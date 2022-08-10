package clivet268.SecureLine;

import clivet268.Enforcry;
import clivet268.SecureLine.Commands.ExacutableCommand;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
    public static void ca(DataOutputStream out, String send) throws IOException {
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

    public static void carpoutputverbose(DataInputStream in, DataOutputStream out, String endoftransmition) throws IOException {
            String temp = "";
            String collection = "";
            int pkgnum = 1;
            while(!temp.equals(endoftransmition)){
                caverbose(out, ("Package Number: " + pkgnum));
                pkgnum++;
                temp = in.readUTF();
                collection += in.readUTF();
            }
            System.out.println("End of Transmition");

        System.out.println(collection);
    }

    public static boolean carprun(DataInputStream in, DataOutputStream out, String endoftransmition) throws IOException {
        String temp = in.readUTF();
        while(!Enforcry.SLcommands.containsKey(temp)){
            out.writeUTF("Unknown command");
            out.flush();
            temp = in.readUTF();
        }
        out.writeUTF("Command Accepted");
        out.flush();

        AtomicInteger pkgnum = new AtomicInteger(1);

        ExacutableCommand command = Enforcry.SLcommands.get(temp);
        command.run();
        command.getOutput().forEach((e) -> {
            try {
                specificrpverbose(in,("Package Number: " + pkgnum));
                pkgnum.getAndIncrement();
                out.writeUTF(e);
                out.flush();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        caverbose(out, endoftransmition);
        System.out.println("End of Transmition");
        System.out.println("Command " + command.getName() + " executed");
        return command.getCloseflag();

    }
}
