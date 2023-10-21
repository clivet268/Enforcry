package clivet268.Enforcry.SecureLine;

import clivet268.Enforcry.Encryption.ChaCha20Poly1305;
import clivet268.Enforcry.Encryption.EncrypterDecrypter;
import org.jetbrains.annotations.Nullable;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Scanner;

import static clivet268.Enforcry.Encryption.EncrypterDecrypter.*;
import static clivet268.Enforcry.Enforcry.getUsername;

//TODO implement in secure line
//TODO THERE NEEDS TO BE A WAY TO VERIFY REPEATED CONNECTIONS WITH CHALLENGE QUESTIONS OR ANOTHER METHOD
public class ClientConnection {

    private static String sUnam = null;

    @Nullable
    public static String getsUnam() {
        System.out.println(sUnam);
        return sUnam;
    }

    private static Socket socket = null;
    protected static EFCDataInputStream in = null;
    protected static EFCDataOutputStream out = null;
    protected RawDataInputStream rawin = null;
    protected RawDataOutputStream rawout = null;
    private static SecretKey cSk;

    private static PublicKey cPk;

    private static PrivateKey cPPk;
    private static SecretKey sSk;


    private static PublicKey sPk;

    public void connect(String ip, int portnum, int timeout) throws Exception {
        try {
            socket = new Socket(ip, portnum);
            if (timeout < 0) {
                socket.setSoTimeout(Integer.MAX_VALUE);
            } else {
                socket.setSoTimeout(timeout);
            }
            //Check for socket existence
            rawin = new RawDataInputStream(socket.getInputStream());
            rawout = new RawDataOutputStream(socket.getOutputStream());
            System.out.println("Connected to " + socket.getRemoteSocketAddress());
            handshake();
            keyTransfer();
            postConnect();
        } catch (ConnectException e) {
            System.out.println("Connection Failed: \n" + e.getMessage());
        }
    }


    //TODO what to do if improper handshake
    public void handshake() throws IOException {
        //Handshake
        int handshake = rawin.readInt();
        if (!(handshake == 1000)) {
            close();
        }
        rawout.writeInt(1000);
        rawout.flush();
        handshake = rawin.readInt();
        if (!(handshake == 1000)) {
            close();
        }
    }

    public void postConnect() {

        //Ports encrypted from here on
        //Communication Handshake continues
        EFCTP efctp = new EFCTP(in, out);
        try {
            out.writeIntE(405);

            int code = in.readIntE();
            if (code == 406) {
                sUnam = in.readUTFE();
                //Debug only?
                System.out.println(sUnam + " 0e");
            } else {
                close();
            }
            code = in.readIntE();
            if (code == 405) {
                out.writeIntE(406);
                out.writeUTFE(getUsername());
            } else {
                close();
            }

            //TODO server has burden of initialization, should it be this way?
            //Send the initial kick
            out.writeIntE(20);

            //TODO handle continues like this?
            //use EFCTP until user or program exits
            //continue flag
            boolean f = true;
            while (f) {
                //TODO conflicts with inner io interactions?
                //Check for errent command code reads
                int eewr = 0;
                while (eewr == 0 || eewr > 1000) {
                    //TODO error out? error code send?
                    eewr = in.readIntE();
                }
                int outcode = efctp.switcherClient(eewr);
                if (outcode == 1) {
                    Scanner userChoice = new Scanner(System.in);
                    System.out.println("Continue connection?");
                    if (!(userChoice.hasNextBoolean() && userChoice.nextBoolean())) {
                        out.writeIntE(22);
                        f = false;
                    }
                } else if (outcode == 2) {
                    out.writeIntE(22);
                    f = false;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //TODO make a nonce of the keys for verification use SHA-256 DO NOT JUST SEND THE PREVIOUS KEY!!!!!!!!
    //TODO prevent man in the middle attacks, public key is sent unencrypted, can be used to impersonate
    //TODO these code numbers and they way theyre handled SUCK. LIKE A LOT!!!!!!!!!!!
    public void keyTransfer() {
        try {
            KeyPair asymmetricAESexchanger = EncrypterDecrypter.generateRSAKkeyPair();
            cPk = asymmetricAESexchanger.getPublic();
            cPPk = asymmetricAESexchanger.getPrivate();

            //Public key transfer - initialized by client
            //Sending public Key
            rawout.writeInt(402);
            rawout.flush();
            int code = rawin.readInt();
            rawout.writeMSG(cPk.getEncoded());

            //Request send public key
            rawout.writeInt(404);
            rawout.flush();
            code = rawin.readInt();
            sPk = EncrypterDecrypter.byteArrayToKey(rawin.readMSG());

            //Hash verify public Key
            rawout.writeInt(403);
            rawout.flush();
            code = rawin.readInt();
            rawout.writeUTF(new String(do_RSAEncryption(genNonce(new String(cPk.getEncoded())).getBytes(), sPk)));

            //Exit connection if the key nonces don't match
            //TODO log a failure/attempted attack, have ability to escalate
            //TODO should the nonce be using Key.toString() and SHA
            if (!(new String(do_RSADecryption(rawin.readUTF().getBytes(), cPPk)).equals(genNonce(cPk.toString())))) {
                System.out.println("INVALID NONCE!!!");
                close();
            }

            //Hash verify yourself
            code = rawin.readInt(); //403
            rawout.writeInt(407);
            rawout.flush();
            //Exit connection if the key nonces don't match
            //TODO log a failure/attempted attack, have ability to escalate
            if (!rawin.readUTF().equals(genNonce(cPk.toString()))) {
                System.out.println("INVALID NONCE!!!");
                close();
            }

            //Transfer Symmetric
            cSk = ChaCha20Poly1305.genChaCha20();
            rawout.writeInt(408);
            rawout.writeUTF(do_RSAEncryption(cSk.getEncoded(), sPk).toString());

            //Receive Symmetric
            sSk = stringToSecretKey(new String(do_RSADecryption(rawin.readUTF().getBytes(), cPPk)));

            in = new EFCDataInputStream(rawin, sSk);
            out = new EFCDataOutputStream(rawout, cSk);
            //TODO handle properly
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes connection....
     */
    void close() throws IOException {
        System.out.println("\n---Closing connection---\n");
        socket.close();
        in.close();
        out.close();
    }

    public static void main(String[] args) {

    }
}
