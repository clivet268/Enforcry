package clivet268.SecureLine;

import clivet268.Enforcry;
import clivet268.SecureLine.Commands.ExacutableCommand;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import static clivet268.Util.Univ.*;

public class SecureLineTP {
    public static void specificcarp(DataInputStream in, String check, DataOutputStream out, String send) throws IOException {
        String temp = in.readUTF();
        while (!temp.equals(check)) {
            temp = in.readUTF();
        }
        out.writeUTF(send);
        out.flush();
    }

    public static void specificcarpmsg(DataInputStream in, String check, DataOutputStream out, String send, String msg) throws IOException {
        String temp = in.readUTF();
        while (!temp.equals(check)) {
            temp = in.readUTF();
        }
        out.writeUTF(send);
        out.flush();
        System.out.println(msg);
    }

    public static void specificrpmsg(DataInputStream in, String check, String msg) throws IOException {
        String temp = in.readUTF();
        while (!temp.equals(check)) {
            temp = in.readUTF();
        }
        System.out.println(msg);
    }

    public static void specificrpverbose(DataInputStream in, String check) throws IOException {
        String temp = in.readUTF();
        while (!temp.equals(check)) {
            temp = in.readUTF();
        }
        System.out.println(check);
    }

    public static void specificcarpverbosepremsg(DataOutputStream out, DataInputStream in, String send, String check, String msg) throws IOException {
        ca(out, send);
        String temp = in.readUTF();
        while (!temp.equals(check)) {
            temp = in.readUTF();
        }
        System.out.println(msg);
    }

    public static void ca(DataOutputStream out, String send) throws IOException {
        out.writeUTF(send);
        out.flush();
    }

    public static void caint(DataOutputStream out, int send) throws IOException {
        System.out.println("sent int " + send);
        out.write(send);
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
        while (!temp.equals(check)) {
            temp = in.readUTF();
        }
        System.out.println(temp);
        out.writeUTF(send);
        out.flush();
    }
    public static void specificcarpsendverbose(DataInputStream in, String check, DataOutputStream out, String send) throws IOException {
        String temp = in.readUTF();
        while (!temp.equals(check)) {
            temp = in.readUTF();
        }
        out.writeUTF(send);
        out.flush();
        System.out.println(send);
    }

    public static void carpverbose(DataInputStream in, DataOutputStream out, String send) throws IOException {
        String temp = in.readUTF();
        System.out.println(temp);
        out.writeUTF(send);
        out.flush();
    }


    public static String outputinputcarpverbose(DataInputStream in, DataOutputStream out, String send) throws IOException {
        out.writeUTF(send);
        out.flush();
        String temp = in.readUTF();
        System.out.println(temp);
        return temp;
    }

    public static String outputinputrpcaverbose(DataInputStream in, DataOutputStream out, String send) throws IOException {
        String temp = in.readUTF();
        System.out.println(temp);
        out.writeUTF(send);
        out.flush();
        return temp;
    }

    public static void carpoutput(DataInputStream in, DataOutputStream out, String endoftransmition) throws IOException {
        //System.out.println(in.skipBytes(14));
        int readmode = Integer.parseInt(in.readUTF());
        System.out.println(readmode);
        handleReadModes(in, out, readmode, endoftransmition);
        System.out.println("End of Transmition");


    }

    public static void handleReadModes(DataInputStream in, DataOutputStream out, int rm, String eot) throws IOException {
        switch (rm) {
            case (1): {
                String temp = "";
                int pkgnum = 1;
                String collection = "";
                while (!temp.equals(eot)) {
                    if (outputinputrpcaverbose(in, out, ("Recived Package Number: " + pkgnum)).equals("Sending Package Number: " + pkgnum)) {
                        pkgnum++;
                        temp = in.readUTF();
                        collection += temp;
                    } else {
                        break;
                    }
                }
                String fpath = enforcrytestpath + getrandname();
                Path of = Path.of(fpath);
                Files.createFile(of);
                Files.write(of,collection.getBytes());
                System.out.println("Data: " + collection);
            }
            case (2): {
                int pkgnum = 1;
                String temp = "";
                while (!temp.equals(eot)) {
                    if (outputinputrpcaverbose(in, out, ("Recived Package Number: " + pkgnum)).equals("Sending Package Number: " + pkgnum)) {
                        pkgnum++;
                        byte[] bytes = new byte[8192];
                        String fpath = enforcrytestpath + getrandname();
                        Path of = Path.of(fpath);
                        Files.createFile(of);
                        int count = 8192;
                        Byte[] sum = new Byte[0];
                        System.out.println("ready to read");
                        while (count == 8192) {
                            System.out.println(count = in.read(bytes));
                            bytes = trimTrailingZeros(bytes);
                            if (Arrays.equals(bytes, new byte[0])){
                                break;
                            }
                            sum = concatWithCollection(sum, ArrayUtils.toObject(bytes));
                        }
                        System.out.println("read it ALL");
                        Files.write(of, ArrayUtils.toPrimitive(sum));
                    } else {
                        System.out.println("Done reading this one");
                        break;
                    }
                    System.out.println("a");
                    caverbose(out, "Ticky");
                    temp = in.readUTF();
                    System.out.println("b");
                }

                System.out.println("All Inputs Done");
            }
            case (3): {

            }
        }
    }

    public static void rpcainputwhileverbose(DataInputStream in, DataOutputStream out, String endoftransmition) throws IOException {
        String temp = in.readUTF();
        boolean dumbfirstcheck = false;
        Scanner s = new Scanner(System.in);
        if (temp.equals(endoftransmition)) {
            System.out.println(temp);
        }
        while (!temp.equals(endoftransmition)) {
            if (dumbfirstcheck) {
                temp = in.readUTF();
            } else {
                dumbfirstcheck = true;
            }
            System.out.println(temp);
            if (!temp.equals(endoftransmition)) {
                ca(out, s.nextLine());
            }
        }

    }

    public static int specificrpcacontinuousinverboseexitcode(DataInputStream in, String check, DataOutputStream out) throws IOException {
        Scanner s = new Scanner(System.in);
        out.writeUTF(s.next());
        out.flush();
        String temp = in.readUTF();
        System.out.println(temp);
        while (!temp.equals(check)) {
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
        while (!Enforcry.SLcommands.containsKey(temp.toLowerCase())) {
            caverbose(out, "Unknown Command");
            temp = in.readUTF();
        }
        caverbose(out, "Command Accepted");

        AtomicInteger pkgnum = new AtomicInteger(1);

        ExacutableCommand command = Enforcry.SLcommands.get(temp.toLowerCase());
        command.commandPrompts().forEach((string) -> {
            try {
                command.input.add(outputinputcarpverbose(in, out, string));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        caverbose(out, "End of Inputs");
        command.run();
        ca(out, "" + command.outbytecode());
        command.getOutput().forEach((e) -> {
            try {
                caverbose(out, ("Sending Package Number: " + pkgnum));
                handleWriteModes(in, out, command.outbytecode(), e);
                System.out.println("Sent Package Number: " + pkgnum);
                pkgnum.getAndIncrement();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        specificcarpsendverbose(in, "Ticky", out, endoftransmition);
        System.out.println("End of Transmition");
        System.out.println("Command " + command.getName() + " executed");
        return command.getCloseflag();

    }

    static int hwmclock = 0;
    public static void handleWriteModes(DataInputStream in, DataOutputStream out, int wm, Object e) throws IOException {
        if (wm == 1) {
            out.writeUTF((String) e);
            out.flush();
        } else if (wm == 2) {
            int count;
            if(hwmclock == 1){
                ca(out, "Oneotherun");
            }
            else {
                hwmclock = 1;
            }
            InputStream is = new ByteArrayInputStream((byte[]) e);
            DataInputStream inn = new DataInputStream(is);
            byte[] buffer = new byte[8192];
            while ((count = inn.read(buffer)) > 0) {
                System.out.println(buffer.length);
                System.out.println("wreeted " + count);
                out.write(buffer, 0, count);
                out.flush();
            }
            byte [] endpty = new byte[8191];
            out.write(endpty);
            out.flush();

            System.out.println("free bird");

        }
    }
}
