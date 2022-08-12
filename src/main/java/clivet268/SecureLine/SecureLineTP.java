package clivet268.SecureLine;

import clivet268.Enforcry;
import clivet268.SecureLine.Commands.ExacutableCommand;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class SecureLineTP {
    public static void specificcarp(DataInputStream in, String check, DataOutputStream out, String send) throws IOException {
        String temp = in.readUTF();
        while(!temp.equals(check)){
            temp = in.readUTF();
        }
        out.writeUTF(send);
        out.flush();
    }
    public static void specificcarpmsg(DataInputStream in, String check, DataOutputStream out, String send, String msg) throws IOException {
        String temp = in.readUTF();
        while(!temp.equals(check)){
            temp = in.readUTF();
        }
        out.writeUTF(send);
        out.flush();
        System.out.println(msg);
    }

    public static void specificrpmsg(DataInputStream in, String check, String msg) throws IOException {
        String temp = in.readUTF();
        while(!temp.equals(check)){
            temp = in.readUTF();
        }
        System.out.println(msg);
    }

    public static void specificrpverbose(DataInputStream in, String check) throws IOException {
        String temp = in.readUTF();
        while(!temp.equals(check)){
            temp = in.readUTF();
        }
        System.out.println(check);
    }
    public static void specificrpverbosepremsg(DataInputStream in, String check, String msg) throws IOException {
        System.out.println(msg);
        String temp = in.readUTF();
        while(!temp.equals(check)){
            temp = in.readUTF();
        }
        System.out.println(check);
    }
    public static void ca(DataOutputStream out, String send) throws IOException {
        out.writeUTF(send);
        out.flush();
    }

    public static void camsg(DataOutputStream out, String send, String msg) throws IOException {
        System.out.println(msg);
        out.writeUTF(send);
        out.flush();
    }

    public static void caverbose(DataOutputStream out, String send) throws IOException {
        out.writeUTF(send);
        out.flush();
        System.out.println(send);
    }

    public static void specificcarpverbose(DataInputStream in, String check, DataOutputStream out, String send) throws IOException {
        String temp = in.readUTF();
        while(!temp.equals(check)){
            temp = in.readUTF();
        }
        System.out.println(temp);
        out.writeUTF(send);
        out.flush();
    }

    public static void specificrpcaverbose(DataInputStream in, String check, DataOutputStream out, String send) throws IOException {
        out.writeUTF(send);
        out.flush();
        String temp = in.readUTF();
        while(!temp.equals(check)){
            temp = in.readUTF();
            System.out.println(temp);
        }
        System.out.println(temp);
    }

    public static void carpoutputverbose(DataInputStream in, DataOutputStream out, String endoftransmition) throws IOException {
            String temp = "";
            String collection = "";
            int pkgnum = 1;
            while(!temp.equals(endoftransmition)){
                caverbose(out, ("Recived Package Number: " + pkgnum));
                pkgnum++;
                temp = in.readUTF();
                System.out.println(temp);
                collection += temp;
            }
            System.out.println("End of Transmition");

        System.out.println("Data: " + collection);
    }

    public static int specificrpcacontinuousinverboseexitcode(DataInputStream in, String check, DataOutputStream out) throws IOException {
        Scanner s = new Scanner(System.in);
        out.writeUTF(s.next());
        out.flush();
        String temp = in.readUTF();
        System.out.println(temp);
        while(!temp.equals(check)){
            out.writeUTF(s.next());
            out.flush();
            temp = in.readUTF();
            System.out.println(temp);
        }
        return 0;
    }

    public static boolean carprun(DataInputStream in, DataOutputStream out, String endoftransmition) throws IOException {
        String temp = in.readUTF();
        System.out.println(temp);
        while(!Enforcry.SLcommands.containsKey(temp.toLowerCase())){
            System.out.println("Unknown Command");
            out.writeUTF("Unknown Command");
            out.flush();
            temp = in.readUTF();
        }
        System.out.println("Command Accepted");
        out.writeUTF("Command Accepted");
        out.flush();

        AtomicInteger pkgnum = new AtomicInteger(1);

        ExacutableCommand command = Enforcry.SLcommands.get(temp);
        command.run();
        command.getOutput().forEach((e) -> {
            try {
                specificrpverbosepremsg(in,("Recived Package Number: " + pkgnum), ("Sent Package Number: " + pkgnum));
                pkgnum.getAndIncrement();
                out.writeUTF(e);
                out.flush();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        System.out.println("await out");
        caverbose(out, endoftransmition);
        System.out.println("End of Transmition");
        System.out.println("Command " + command.getName() + " executed");
        return command.getCloseflag();

    }
}
