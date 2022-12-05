package clivet268.SecureLine;

import clivet268.Enforcry;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;

import static clivet268.Enforcry.logger;

public class EncryptedSecureLineServer {
    private static Socket socket = null;
    private static ServerSocket server = null;
    static DataInputStream in = null;
    static DataOutputStream out = null;
    private boolean closeflag = true;
    //TODO key should be secret and exist
    private String key = "";
    private EFCTP efctp;

    private static String cUnam = null;

    @Nullable
    public static String getcUnam() {
        return cUnam;
    }

    public EncryptedSecureLineServer(int timeout) throws IOException {

        try {
            while (closeflag) {
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
                in = new DataInputStream(
                        new BufferedInputStream(socket.getInputStream()));
                out = new DataOutputStream(
                        new BufferedOutputStream(socket.getOutputStream()));
                efctp = new EFCTP(in,out);
                //TODO handshake?
                out.writeInt(1000);
                out.flush();
                int handshake = in.readInt();
                if (!(handshake == 1000)) {
                    //TODO what to do if improper handshake
                    close();
                }
                out.writeInt(1000);
                out.flush();
                out.writeInt(405);
                out.flush();
                handshake = in.readInt();
                if (handshake == 406) {
                    cUnam = in.readUTF();
                } else {
                    close();
                }
                handshake = in.readInt();
                if (handshake == 405) {
                    out.writeInt(406);
                    out.flush();
                    //TODO understand why this needs flushing?
                    out.writeUTF(Enforcry.username);
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
                        eewr = in.readInt();
                        logger.log(eewr + " is");
                    }
                    f = efctp.switcherServer(eewr);
                }

                //Close when done
                close();

            }
        } catch (IOException ignored) {
            /*
            if (i instanceof BindException) {
                //TODO need to be closed?
                //close();
            }

             */
            //Debug only
            //i.printStackTrace();

        } catch (InterruptedException e) {
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
