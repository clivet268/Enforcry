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
    private static Socket socket = null;
    private static DataInputStream in = null;
    private static DataOutputStream out = null;
    private static String sUnam = null;

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

            //Handshake
            int handshake = in.readInt();
            if (!(handshake == 1000)) {
                //TODO what to do if improper handshake
                close();
            }
            out.writeInt(1000);
            out.flush();
            handshake = in.readInt();
            if (!(handshake == 1000)) {
                close();
            }
            handshake = in.readInt();
            if (handshake == 405) {
                out.writeInt(406);
                out.flush();
                out.writeUTF(Enforcry.username);
            } else {
                close();
            }
            out.writeInt(405);
            out.flush();
            handshake = in.readInt();
            if (handshake == 406) {
                sUnam = in.readUTF();
                System.out.println(sUnam);
            } else {
                close();
            }

            //TODO server has burden of initialization, should it be this way?
            //Send the initial kick
            out.writeInt(20);
            out.flush();

            //TODO handle continues like this?
            //use EFCTP until user or program exits
            //continue flag
            boolean f= true;
            while (f) {
                //TODO conflicts with inner io interactions?
                //Check for errent command code reads
                int eewr = 0;
                while (eewr == 0 || eewr > 1000) {
                    //TODO error out? error code send?
                    eewr = in.readInt();
                    logger.log(eewr + " is");
                }
                int outcode = efctp.switcherClient(eewr);
                if(outcode == 1){
                    Scanner userChoice = new Scanner(System.in);
                    System.out.println("Continue connection?");
                    if(!(userChoice.hasNextBoolean() && userChoice.nextBoolean())){
                        out.writeInt(22);
                        f = false;
                    }
                }
                else if (outcode == 2){
                    out.writeInt(22);
                    out.flush();
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
        } catch (InterruptedException e) {
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
