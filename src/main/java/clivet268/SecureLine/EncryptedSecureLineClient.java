package clivet268.SecureLine;

import clivet268.Encryption.Asymmetric;
import clivet268.Enforcry;
import org.jetbrains.annotations.Nullable;

import javax.xml.bind.DatatypeConverter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.PublicKey;
import java.util.Scanner;

import static clivet268.Enforcry.logger;
import static clivet268.Enforcry.sessionKey;

public class EncryptedSecureLineClient {
    private static Socket socket = null;
    private static EFCDataInputStream in = null;
    private static EFCDataOutputStream out = null;
    private static DataInputStream rawin = null;
    private static DataOutputStream rawout = null;
    private static String sUnam = null;
    private static PublicKey sPk = null;

    @Nullable
    public static String getsUnam() {
        System.out.println(sUnam);
        return sUnam;
    }

    private EFCTP efctp;

    //TODO C -> C?
    //TODO S -> S?
    public EncryptedSecureLineClient(String address, int timeout) throws IOException {
        try {
            //initialization
            socket = new Socket(address, 26817);
            if (timeout > 0) {
                long t = timeout;
                timeout = (int) Math.min(Integer.MAX_VALUE - 1, t * 1000);
                socket.setSoTimeout(timeout);
            }
            //Check for socket existence
            if (!(socket == null)) {
                rawin = new DataInputStream(socket.getInputStream());
                rawout = new DataOutputStream(socket.getOutputStream());
            }


            //Fail out
            else {
                try {
                    close();
                } catch (IOException i) {
                    System.out.println(i);
                }
                System.out.println("Connection failed");
                System.exit(0);
            }
            System.out.println("Connected to " + socket.getRemoteSocketAddress());

            //TODO prevent man in the middle attacks, public key is sent unencrypted, can be used to impersonate
            //Handshake
            int handshake = rawin.readInt();
            if (!(handshake == 1000)) {
                //TODO what to do if improper handshake
                close();
            }
            rawout.writeInt(1000);
            rawout.flush();
            handshake = rawin.readInt();
            if (!(handshake == 1000)) {
                close();
            }

            //Debug only
            //logger.log(DatatypeConverter.printHexBinary(sessionKey.getPublic().getEncoded()));
            //Public key transfer
            handshake = rawin.readInt();
            if (!(handshake == 404)) {
                close();
            }
            rawout.writeInt(402);
            rawout.flush();
            byte[] encodedPrivateKey = sessionKey.getPublic().getEncoded();
            rawout.writeInt(encodedPrivateKey.length);
            rawout.flush();
            rawout.write(encodedPrivateKey);
            rawout.flush();
            rawout.writeInt(404);
            rawout.flush();
            handshake = rawin.readInt();
            if (!(handshake == 402)) {
                close();
            }
            int lenr = rawin.readInt();
            byte[] cpkin = new byte[lenr];
            rawin.readFully(cpkin);
            sPk = Asymmetric.byteArrayToPrivateKey(cpkin);
            //Debug only
            System.out.println(DatatypeConverter.printHexBinary(sPk.getEncoded()));


            in = new EFCDataInputStream(rawin, sessionKey.getPrivate(), sPk);
            out = new EFCDataOutputStream(rawout, sessionKey.getPrivate(), sPk);
            efctp = new EFCTP(in, out);
            //Public key encrypted and checked through encrypted in out

            //Ports encrypted from here on
            //Communication Handshake continues
            handshake = in.readIntE();
            if (handshake == 405) {
                out.writeIntE(406);
                out.flush();
                out.writeUTFE(Enforcry.username);
            } else {
                close();
            }
            out.writeIntE(405);
            out.flush();
            handshake = in.readIntE();
            if (handshake == 406) {
                sUnam = in.readUTFE();
                System.out.println(sUnam);
            } else {
                close();
            }
            //TODO moreeEE handshake

            //TODO server has burden of initialization, should it be this way?
            //Send the initial kick
            out.writeIntE(20);
            out.flush();

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
                    logger.log(eewr + " is");
                }
                int outcode = efctp.switcherClient(eewr);
                if(outcode == 1){
                    Scanner userChoice = new Scanner(System.in);
                    System.out.println("Continue connection?");
                    if(!(userChoice.hasNextBoolean() && userChoice.nextBoolean())){
                        out.writeIntE(22);
                        f = false;
                    }
                }
                else if (outcode == 2){
                    out.writeIntE(22);
                    f = false;
                }
            }

            try {
                close();
            } catch (IOException i) {
                i.printStackTrace();
            }

        } catch (IOException u) {
            u.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Closes connection
     */
    private static void close() throws IOException {
        System.out.println("\n---Closing connection---\n");
        socket.close();
        in.close();
        out.close();
    }



    public static void main(String args[]) throws IOException {
        Enforcry.initSLcommands();
        EncryptedSecureLineClient client = new EncryptedSecureLineClient("127.0.0.1", 100);
    }

}
