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

    public EncryptedSecureLineServer(int timeout) throws IOException {
        while(closeflag) {
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
                in = new DataInputStream(
                        new BufferedInputStream(socket.getInputStream()));
                out = new DataOutputStream(
                        new BufferedOutputStream(socket.getOutputStream()));
                efctp = new EFCTP(in,out);
                //TODO handshake?
                out.writeInt(1000);
                out.flush();
                System.out.println(in.readInt());
                out.writeInt(1000);
                out.flush();

                //continue flag
                boolean f = true;
                logger.log(Level.INFO, "000000");
                //use EFCTP
                while (f){
                    logger.log(Level.INFO, "0000");
                    //TODO conflicts with inner io interactions?
                    f=efctp.switcherServer(in.readInt());
                }

                //Close
                socket.close();
                in.close();
                out.close();
            } catch (IOException i) {
                if(i instanceof BindException){
                    socket.close();
                    server.close();
                }
                i.printStackTrace();

            }
        }
    }

    public static void main(String args[]) throws IOException {
        Enforcry.initSLcommands();
        EncryptedSecureLineServer server = new EncryptedSecureLineServer(555);
    }

}
