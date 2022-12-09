package clivet268.SecureLine;

import clivet268.Encryption.Asymmetric;
import clivet268.Enforcry;
import org.jetbrains.annotations.Nullable;

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
            logger.log("1");
            if (!(handshake == 1000)) {
                close();
            }

            //Debug only
            //logger.log(DatatypeConverter.printHexBinary(sessionKey.getPublic().getEncoded()));
            //Public key transfer
            handshake = rawin.readInt();
            logger.log("12");
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
            logger.log("13");
            rawout.flush();
            handshake = rawin.readInt();
            logger.log("14 + " + handshake);
            if (!(handshake == 402)) {
                close();
            }
            logger.log("9879y8tyd2r");
            int lenr = rawin.readInt();
            logger.log("1214");
            byte[] cpkin = new byte[lenr];
            rawin.readFully(cpkin);
            logger.log("2112121221122121");
            sPk = Asymmetric.byteArrayToPrivateKey(cpkin);

            logger.log("114564");

            in = new EFCDataInputStream(rawin, sessionKey.getPrivate(), sPk);
            logger.log("!311");
            out = new EFCDataOutputStream(rawout, sessionKey.getPrivate(), sPk);
            logger.log("weff2323f32f2323");
            efctp = new EFCTP(in, out);
            //Public key encrypted and checked through encrypted in out
            logger.log("wfeiut72");

            //Ports encrypted from here on
            //Communication Handshake continues
            out.writeIntE(405);
            logger.log("155");
            out.flush();
            handshake = in.readIntE();
            logger.log("16");
            if (handshake == 406) {
                sUnam = in.readUTFE();
                //Debug only?
                System.out.println(sUnam + " 0e");
            } else {
                close();
            }
            handshake = in.readIntE();
            logger.log("144");
            if (handshake == 405) {
                out.writeIntE(406);
                logger.log("r2r2r33r2rrf");
                out.flush();
                out.writeUTFE(Enforcry.username);
                logger.log("23r23");
            } else {
                close();
            }

            //TODO server has burden of initialization, should it be this way?
            //Send the initial kick
            out.writeIntE(20);
            logger.log("1");
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
