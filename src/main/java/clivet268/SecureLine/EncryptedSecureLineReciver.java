package clivet268.SecureLine;

import clivet268.Enforcry;
import org.jsoup.select.Evaluator;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import static clivet268.SecureLine.SecureLineTP.*;

public class EncryptedSecureLineReciver {
    private Socket socket = null;
    private ServerSocket    server   = null;
    private DataInputStream in       =  null;
    private DataOutputStream out       =  null;
    private boolean closeflag = true;

    public EncryptedSecureLineReciver(int timeout) throws IOException {
        while(closeflag) {
            try {

                server = new ServerSocket(26817);
                if (timeout > 0) {
                    long t = timeout;
                    timeout = (int) Math.min(Integer.MAX_VALUE - 1, t * 1000);
                    server.setSoTimeout(timeout);
                }
                System.out.println("Server started");
                System.out.println("Waiting for a client ...");
                socket = server.accept();
                System.out.println("Client accepted");
                in = new DataInputStream(
                        new BufferedInputStream(socket.getInputStream()));
                out = new DataOutputStream(
                        new BufferedOutputStream(socket.getOutputStream()));
                caverbose(out, "Handshake");
                specificcarpmsg(in,"Handshake",out, "Bravo","Handshake complete");
                //Connection established

                //Encrypted?
                specificcarp(in, "En",out,"En?");
                System.out.println("wefijwf");
                specificrpmsg(in, "Sending PIK", "Reciving PIK");
                System.out.println("fwwef");
                ca(out, "K");
                System.out.println("ewf");
                carpoutput(in, out, "Zero");


                camsg(out,"Enter Command", "Awaiting Command");
                this.closeflag = carprun(in, out, "Zero");
                specificrpverbose(in, "Done");
                System.out.println("Closing connection");
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
