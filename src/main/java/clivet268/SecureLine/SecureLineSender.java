package clivet268.SecureLine;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.Socket;
import java.util.Scanner;

import static clivet268.SecureLine.SecureLineTP.*;

public class SecureLineSender {
    private Socket socket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private Scanner scanner = new Scanner(System.in);

    public SecureLineSender(String address, int timeout) throws IOException {
        try {
            socket = new Socket(address, 26817);
            if(timeout > 0) {
                long t = timeout;
                timeout = (int) Math.min(Integer.MAX_VALUE - 1, t * 1000);
                socket.setSoTimeout(timeout);
            }
            System.out.println("Connected to " + socket.getRemoteSocketAddress());

        } catch (IOException u) {
            System.out.println(u + "20");
        }
        if (!(socket == null)) {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            specificcarpverbose(in,"Handshake",out, "Handshake");
            specificrpmsg(in,"Bravo","Handshake complete");
            //Connected

            specificrpverbose(in, "Enter Command");
            specificcarpverbose(in, "Sending Output", out,scanner.next());
            carpoutputverbose(in, out, "Zero");

            try {
                in.close();
                out.close();
                socket.close();

            } catch (IOException i) {
                System.out.println(i);
                if(i instanceof BindException){
                    System.out.println(i);
                }
            }
        }
        else {
            System.out.println("Connection failed");
        }
    }





    public static void main(String args[]) throws IOException {
        SecureLineSender client = new SecureLineSender("127.0.0.1", 100);
    }

}
