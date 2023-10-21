package clivet268.Enforcry.SecureLine;

import clivet268.Enforcry.Encryption.ChaCha20Poly1305;
import clivet268.Enforcry.Encryption.EncrypterDecrypter;
import org.jetbrains.annotations.Nullable;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import static clivet268.Enforcry.Encryption.EncrypterDecrypter.*;
import static clivet268.Enforcry.Enforcry.getUsername;

public class ServerConnection {


    private static String cUnam = null;

    @Nullable
    public static String getcUnam() {
        return cUnam;
    }

    protected Socket socket = null;
    protected EFCDataInputStream in = null;
    protected EFCDataOutputStream out = null;
    protected RawDataInputStream rawin = null;
    protected RawDataOutputStream rawout = null;

    private static SecretKey cSk;

    private static PublicKey cPk;

    private static PrivateKey sPPk;
    private static SecretKey sSk;


    private static PublicKey sPk;
    static ServerSocket server = null;

    public void predef(Socket s) {
        socket = s;
    }


    public void handshake() throws IOException {
        //Handshake
        rawout.writeInt(1000);
        rawout.flush();
        int handshake = rawin.readInt();
        if (!(handshake == 1000)) {
            //TODO what to do if improper handshake
            close();
        }
        rawout.writeInt(1000);
        rawout.flush();
    }

    public void connect(int portnum, int timeout) {
        try {
            server = new ServerSocket(portnum);

            if (timeout > 0) {
                long t = timeout;
                timeout = (int) Math.min(Integer.MAX_VALUE - 1, t * 1000);
                server.setSoTimeout(timeout);
            }
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
            //Accept the client

            socket = server.accept();
            System.out.println("Client accepted");
            //Check for socket existence

            rawin = new RawDataInputStream(socket.getInputStream());
            rawout = new RawDataOutputStream(socket.getOutputStream());
            System.out.println("Connected to " + socket.getRemoteSocketAddress());
            handshake();
            keyTransfer();
            postConnect();
        } catch (IOException e) {
            System.out.println("Connection failed: \n" + e.getMessage());
        }
    }

    public void predefConnect() throws Exception {
        rawin = new RawDataInputStream(socket.getInputStream());
        rawout = new RawDataOutputStream(socket.getOutputStream());
        System.out.println("Connected to " + socket.getRemoteSocketAddress());
        handshake();
        keyTransfer();
        postConnect();
    }

    //TODO these codes? kinda dumb, we should just not do that except within EFCTP, no?
    public void keyTransfer() throws IOException {
        try {
            KeyPair asymmetricAESexchanger = EncrypterDecrypter.generateRSAKkeyPair();
            sPk = asymmetricAESexchanger.getPublic();
            sPPk = asymmetricAESexchanger.getPrivate();

            //Request send public key
            int code = rawin.readInt();
            rawout.writeInt(404);
            rawout.flush();
            //TODO how to handle bad codes? especially missteps
            //TODO thread this? :) / :(
            cPk = EncrypterDecrypter.byteArrayToKey(rawin.readMSG());

            //Sending public Key
            code = rawin.readInt();
            rawout.writeInt(402);
            rawout.flush();
            rawout.writeMSG(sPk.getEncoded());

            //Hash verify public Key
            code = rawin.readInt();
            rawout.writeInt(403);
            rawout.flush();
            rawout.writeUTF(new String(do_RSAEncryption(genNonce(new String(cPk.getEncoded())).getBytes(), cPk)));
            //Exit connection if the key nonces don't match
            //TODO log a failure/attempted attack, have ability to escalate
            //TODO should the nonce be using Key.toString() and SHA
            if (!(new String(do_RSADecryption(rawin.readUTF().getBytes(), sPPk)).equals(genNonce(cPk.toString())))) {
                System.out.println("INVALID NONCE!!!");
                close();
            }

            //Hash verify yourself
            code = rawin.readInt(); //403
            rawout.writeInt(407);
            rawout.flush();
            //Exit connection if the key nonces don't match
            //TODO log a failure/attempted attack, have ability to escalate
            if (!rawin.readUTF().equals(genNonce(sPk.toString()))) {
                System.out.println("INVALID NONCE!!!");
                close();
            }

            //Receive Symmetric
            sSk = stringToSecretKey(new String(do_RSADecryption(rawin.readUTF().getBytes(), sPPk)));

            //Transfer Symmetric
            cSk = ChaCha20Poly1305.genChaCha20();
            rawout.writeInt(408);
            rawout.writeUTF(do_RSAEncryption(cSk.getEncoded(), cPk).toString());


            in = new EFCDataInputStream(rawin, sSk);
            out = new EFCDataOutputStream(rawout, cSk);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        System.out.println("\n---Closing connection---\n");
        try {
            socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            System.out.println("Error while closing: \n" + e.getMessage());
        }
    }

    public void postConnect() {
        try {
            //Ports encrypted from here on
            //Communication Handshake continues
            EFCTP efctp = new EFCTP(in, out);
            int code = in.readIntE();
            if (code == 405) {
                out.writeIntE(406);
                out.writeUTFE(getUsername());
            } else {
                close();
            }
            out.writeIntE(405);
            code = in.readIntE();
            if (code == 406) {
                cUnam = in.readUTFE();
                System.out.println(cUnam);
            } else {
                close();
            }


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
                f = efctp.switcherServer(eewr);
            }

            //Close when done
            close();
            //TODO handle properly
        } catch (Exception e) {
            close();
            e.printStackTrace();
        }
    }
}
