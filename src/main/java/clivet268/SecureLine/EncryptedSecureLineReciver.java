package clivet268.SecureLine;

import clivet268.Enforcry;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class EncryptedSecureLineReciver {
    private Socket socket = null;
    private ServerSocket    server   = null;
    private DataInputStream in       =  null;
    private DataOutputStream out       =  null;
    private boolean closeflag = true;
    private String key = "";
    private EFCTP efctp;

    public EncryptedSecureLineReciver(int timeout) throws IOException {
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

                //continue flag
                boolean f = true;
                while (f){
                    //TODO conflicts with inner io interactions?
                    f=efctp.switcherReciver(in.readInt());
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
        EncryptedSecureLineReciver server = new EncryptedSecureLineReciver(555);
    }

}
