package clivet268.SecureLine;

import clivet268.Encryption.Asymmetric;
import clivet268.Enforcry;
import org.jetbrains.annotations.Nullable;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PublicKey;

import static clivet268.Enforcry.logger;
import static clivet268.Enforcry.sessionKey;

public class EncryptedSecureLineServer {
    private static Socket socket = null;
    private static EFCDataInputStream in = null;
    private static EFCDataOutputStream out = null;
    private static DataInputStream rawin = null;
    private static DataOutputStream rawout = null;
    private static ServerSocket server = null;
    private static PublicKey cPk = null;

    private EFCTP efctp;

    private static String cUnam = null;

    @Nullable
    public static String getcUnam() {
        return cUnam;
    }

    public EncryptedSecureLineServer(int timeout) throws IOException {

        try {
            //Initialization
            server = new ServerSocket(26817);
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
            rawin = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            rawout = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            efctp = new EFCTP(in, out);

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


            //Public key transfer
            rawout.writeInt(404);
            rawout.flush();
            handshake = rawin.readInt();
            if (!(handshake == 402)) {
                close();
            }
            int lenr = rawin.readInt();
            byte[] cpkin = new byte[lenr];
            rawin.readFully(cpkin);
            cPk = Asymmetric.byteArrayToPrivateKey(cpkin);
            //Debug only
            System.out.println(DatatypeConverter.printHexBinary(cPk.getEncoded()));
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

            in = new EFCDataInputStream(rawin, sessionKey.getPrivate(), cPk);
            out = new EFCDataOutputStream(rawout, sessionKey.getPrivate(), cPk);
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
                cUnam = in.readUTFE();
                System.out.println(cUnam);
            } else {
                close();
            }

            //TODO YUCK WTF IS THIS
            //Handshake
            out.writeIntE(405);
            out.flush();
            handshake = in.readIntE();
            if (handshake == 406) {
                cUnam = in.readUTFE();
            } else {
                close();
            }
            handshake = in.readIntE();
            if (handshake == 405) {
                out.writeIntE(406);
                out.flush();
                //TODO understand why this needs flushing?
                out.writeUTFE(Enforcry.username);
                out.flush();
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
                    logger.log(eewr + " is");
                }
                f = efctp.switcherServer(eewr);
            }

            //Close when done
            close();


        } catch (IOException ignored) {
            /*
            if (i instanceof BindException) {
                //TODO need to be closed?
                //close();
            }

             */
            //Debug only
            //i.printStackTrace();
            // TODO handle different exceptions differently
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void close() throws IOException {
        System.out.println("\n---Closing connection---\n");
        socket.close();
        in.close();
        out.close();
    }


    public static void main(String args[]) throws IOException {
        Enforcry.initSLcommands();
        EncryptedSecureLineServer server = new EncryptedSecureLineServer(555);
    }

}
