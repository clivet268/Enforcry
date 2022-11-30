package clivet268.SecureLine;

import clivet268.Enforcry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;

import static clivet268.Enforcry.logger;

public class EncryptedSecureLineClient {
    private Socket socket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private Scanner scanner = new Scanner(System.in);
    //TODO key should be secret and exist
    private String key = "";

    private EFCTP efctp;

    //TODO C -> C?
    //TODO S -> S?
    public EncryptedSecureLineClient(String address, int timeout) throws IOException {
        try {
            //initialization
            socket = new Socket(address, 26817);
            if(timeout > 0) {
                long t = timeout;
                timeout = (int) Math.min(Integer.MAX_VALUE - 1, t * 1000);
                socket.setSoTimeout(timeout);
            }
            //Check for socket existence
            if (!(socket == null)) {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
            }
            //Fail out
            else {
                try {
                    in.close();
                    out.close();
                    socket.close();
                } catch (IOException i) {
                    System.out.println(i);
                }
                System.out.println("Connection failed");
                System.exit(0);
            }
            efctp = new EFCTP(in,out);
            System.out.println("Connected to " + socket.getRemoteSocketAddress());
            //TODO handshake?
            System.out.println(in.readInt());
            out.writeInt(1000);
            out.flush();
            System.out.println(in.readInt());

            //continue flag
            //TODO this means the convo is intialized by the client, is that good?
            // better ping-pong needed for future levels of communitcation
            // probalby should be in a handshake!!!!
            logger.log(Level.INFO, "00");
            boolean f= true;
            //Send the initial kick
            //TODO  code 20 might need to be used in future
            out.writeInt(7);
            out.flush();

            //Finished?
            boolean ndone = true;
            //use EFCTP
            while(ndone) {
                while (f) {
                    logger.log(Level.INFO, "000");
                    //TODO conflicts with inner io interactions?
                    //TODO debug only
                    int eewr = in.readInt();
                    while(eewr == 0 || eewr > 1000) {
                        eewr = in.readInt();
                    }
                    //debug only
                    System.out.println(eewr);
                    f = efctp.switcherClient(eewr);
                }
                System.out.println("not done?");
                Scanner scanner1 = new Scanner(System.in);
                try {
                    ndone = scanner1.nextBoolean();
                }
                catch (InputMismatchException ignored){
                    ndone = false;
                }
            }

            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException i) {
                System.out.println(i);
            }

        } catch (IOException u) {
            System.out.println(u + "20");
        }

    }





    public static void main(String args[]) throws IOException {
        Enforcry.initSLcommands();
        EncryptedSecureLineClient client = new EncryptedSecureLineClient("127.0.0.1", 100);
    }

}
