package clivet268.SecureLine;

import clivet268.Enforcry;
import clivet268.Util.Univ;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.Socket;
import java.util.Scanner;

import static clivet268.SecureLine.SecureLineTP.*;
import static clivet268.Util.Univ.getPromptsNoKey;

public class EncryptedSecureLineSender {
    private Socket socket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private Scanner scanner = new Scanner(System.in);
    private String key = "";

    public EncryptedSecureLineSender(String address, int timeout) throws IOException {
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
            int exitcode = -1;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            specificcarpverbose(in,"Handshake",out, "Handshake");
            specificrpmsg(in,"Bravo","Handshake complete");
            //Connected

            //Encrypted?
            String enc = outputinputrp(in);
            if(enc.equals("En?")){
                ca(out,"En");
            }
            else {
                in.close();
                out.close();
                socket.close();
            }
            //PIK gen
            System.out.println("Select the PIK");
            key = Univ.getPIK();
            String[] outs = getPromptsNoKey(key);
            specificrpmsg(in, "K", "oKay");
            rpcainputsetwhileverbose(in, out, outs,"End of Inputs");



            specificrpverbose(in, "Enter Command");
            exitcode = specificrpcacontinuousinverboseexitcode(in, "Command Accepted", out);
            rpcainputwhileverbose(in, out, "End of Inputs");
            rpcafinaloutputs(in, out, "Zero",2048, key);
            caverbose(out, "Done");
            System.out.println("Closing Connection");

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
        Enforcry.initSLcommands();
        EncryptedSecureLineSender client = new EncryptedSecureLineSender("127.0.0.1", 100);
    }

}
