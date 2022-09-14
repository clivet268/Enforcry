package clivet268.SecureLine;

import clivet268.Enforcry;
import clivet268.FileEncryption.EncrypterDecrypter;
import clivet268.SecureLine.Commands.ExacutableCommand;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static clivet268.Util.Univ.*;

public class SecureLineTP {

    public static String[] o3ut = new String[0];

    public static String keylayeri(String in, String k){
        return EncrypterDecrypter.encrypt(in, k);
    }

    public static String keylayero(String in, String k){
        return EncrypterDecrypter.decrypt(in, k);
    }
    public static void specificrpca(DataInputStream in, String check, DataOutputStream out, String send) throws IOException {
        String temp = in.readUTF();
        while (!temp.equals(check)) {
            temp = in.readUTF();
        }
        out.writeUTF(send);
        out.flush();
    }

    public static void specificcarp(DataInputStream in, String check, DataOutputStream out, String send) throws IOException {
        out.writeUTF(send);
        out.flush();
        String temp = in.readUTF();
        while (!temp.equals(check)) {
            temp = in.readUTF();
        }

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
    public static void rpflushUTF(DataInputStream in) throws IOException {
        in.readUTF();
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

    public static void specificrpcaverbose(DataInputStream in, String check, DataOutputStream out, String send) throws IOException {
        String temp = in.readUTF();
        while (!temp.equals(check)) {
            temp = in.readUTF();
        }
        System.out.println(temp);
        out.writeUTF(send);
        out.flush();
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

    public static void specificrpcasendverboseverbose(DataInputStream in, String check, DataOutputStream out, String send) throws IOException {
        String temp = in.readUTF();
        while (!temp.equals(check)) {
            temp = in.readUTF();
            System.out.println(temp);
        }
        System.out.println(temp);
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

    public static String outputinputcarp(DataInputStream in, DataOutputStream out, String send) throws IOException {
        out.writeUTF(send);
        out.flush();
        String temp = in.readUTF();
        return temp;
    }
    public static String outputinputrpcaverbose(DataInputStream in, DataOutputStream out, String send) throws IOException {
        String temp = in.readUTF();
        System.out.println(temp);
        out.writeUTF(send);
        out.flush();
        return temp;
    }

    public static String outputinputrp(DataInputStream in) throws IOException {
        return in.readUTF();
    }

    public static void rpcafinaloutputs(DataInputStream in, DataOutputStream out, String endoftransmition, @Nullable String k) throws IOException {
        //System.out.println(in.skipBytes(14));
        int readmode = Integer.parseInt(in.readUTF());
        System.out.println("Readmode" + readmode);
        handleReadModes(in, out, readmode, endoftransmition, 0, k);
        System.out.println("End of Transmition");


    }

    public static String rpcafinaloutputs(DataInputStream in, DataOutputStream out, String endoftransmition, int obg, @Nullable String k) throws IOException {
        int readmode = Integer.parseInt(in.readUTF());
        System.out.println("Readmode" + readmode);
        String huhu = handleReadModes(in, out, readmode, endoftransmition, obg, k);
        System.out.println("End of Transmition");
        return huhu;

    }

    public static String handleReadModes(DataInputStream in, DataOutputStream out, int rm, String eot, int obg, @Nullable String k) throws IOException {
        String o0 = "";
        switch (rm) {
            case (1): {
                String temp = "";
                int pkgnum = 1;
                String collection = "";
                while (!temp.equals(eot)) {
                    if (outputinputrpcaverbose(in, out, ("Recived Package Number: " + pkgnum)).equals("Sending Package Number: " + pkgnum)) {
                        pkgnum++;
                        temp = in.readUTF();
                        if(k != null){
                            temp = keylayero(temp, k);
                        }
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
                specificcarp(in, "Begin Loop", out, "Ticky");
                caverbose(out, "Ticky");
                int pkgnum = 1;
                String temp = "Continue";
                String zstcheck = outputinputrp(in);
                if (zstcheck.equals("Zero")) {
                    temp = "Zero";
                }

                Date date = new Date();
                while (temp.equals("Continue")) {
                    byte[] bytes = new byte[BTC];
                    String fpath = enforcrytestpath + getrandname();
                    Path of = Path.of(fpath);
                    Files.createFile(of);
                    int count = BTC;
                    byte[] sum = new byte[0];
                    System.out.println("ready to read");
                    
                    while (count > 0) {
                        count = in.read(bytes);
                        bytes = zchecker(bytes);

                        if (Arrays.equals(bytes, new byte[0])) {
                            System.out.println("Broke");
                            break;
                        }
                        sum = ArrayUtils.addAll(sum, bytes);
                    }
                    if(k != null) {
                        String hum = keylayero(Arrays.toString(sum), k);
                        sum = keylayero(hum, k).getBytes();
                    }
                    System.out.println("read it ALL");
                    Path ppwpe = Files.write(of, sum);
                    temp = "";
                    while (temp.equals("")) {
                        temp = outputinputcarp(in, out, "Ticky");
                    }
                    System.out.println(temp);
                }
                System.out.println("All Transfers Done");

                //credit mkyong https://mkyong.com/java/java-time-elapsed-in-days-hours-minutes-seconds/
                Date enddate = new Date();
                long secondsInMilli = 1000;
                long minutesInMilli = secondsInMilli * 60;
                long hoursInMilli = minutesInMilli * 60;
                long different = enddate.getTime() - date.getTime();
                long elapsedHours = different / hoursInMilli;
                different = different % hoursInMilli;
                long elapsedMinutes = different / minutesInMilli;
                different = different % minutesInMilli;
                long elapsedSeconds = different / secondsInMilli;
                different = different % secondsInMilli;
                long elapsedDeciSeconds = different / 10;
                System.out.printf("Time elapsed: %d:%d:%d:%d%n", elapsedHours, elapsedMinutes, elapsedSeconds, elapsedDeciSeconds);
            }
            case (3): {

            }
        }
        return "";
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

    public static void rpcainputsetwhileverbose(DataInputStream in, DataOutputStream out, String[] inputs, String endoftransmition) throws IOException {
        String temp = in.readUTF();
        boolean dumbfirstcheck = false;
        if (temp.equals(endoftransmition)) {
            System.out.println(temp);
        }
        int i = 0;
        while (!temp.equals(endoftransmition)) {
            if (dumbfirstcheck) {
                temp = in.readUTF();
            } else {
                dumbfirstcheck = true;
            }
            System.out.println(temp);
            if (!temp.equals(endoftransmition)) {
                ca(out, inputs[i]);
                i++;
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

    public static void carpfile(DataInputStream in, DataOutputStream out, String endoftransmition, byte[] fin, @Nullable String k) throws IOException {
        ca(out, "" + 2);
        specificrpcasendverboseverbose(in, "Ticky", out, "Begin Loop");
        specificrpcasendverboseverbose(in, "Ticky", out, "Continue");
        handleWriteModes(in, out, 2, fin, k);
        specificrpcasendverboseverbose(in, "Ticky", out, endoftransmition);
    }

    public static boolean carprun(DataInputStream in, DataOutputStream out, String endoftransmition, @Nullable String commandIn) throws IOException {
        String temp;
        if(commandIn == null) {
            temp = in.readUTF();
            System.out.println(temp);
            while (!Enforcry.SLcommands.containsKey(temp.toLowerCase())) {
                caverbose(out, "Unknown Command");
                temp = in.readUTF();
            }
            caverbose(out, "Command Accepted");
        }
        else {
            temp =commandIn;
        }

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
        AtomicReference<String[]> ewe = new AtomicReference<>(new String[0]);
        specificrpcasendverboseverbose(in, "Ticky", out, "Begin Loop");
        command.getOutput().forEach((e) -> {
            try {
                specificrpcasendverboseverbose(in, "Ticky", out, "Continue");
                ewe.set(handleWriteModes(in, out, command.outbytecode(), e, null));
                pkgnum.getAndIncrement();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        if(command.outbytecode() == 3) {
            o3ut = ewe.get();
        }
        specificrpcasendverboseverbose(in, "Ticky", out, endoftransmition);
        System.out.println("End of Transmition");
        System.out.println("Command " + command.getName() + " executed");
        return command.getCloseflag();

    }
    public static @NotNull String[] handleWriteModes(DataInputStream in, DataOutputStream out, int wm, Object e, String k) throws IOException {
        if (wm == 1) {
            String ee = "";
            if(k != null){
                ee = keylayeri(String.valueOf(e), k);
            }
            else{
                ee = (String) e;
            }
            out.writeUTF(ee);
            out.flush();
        } else if (wm == 2) {
            int count;
            byte[] ee;
            if(k != null){
                ee = keylayeri(String.valueOf(e), k).getBytes();
            }
            else{
                ee = (byte[]) e;
            }
            InputStream is = new ByteArrayInputStream((byte[]) ee);
            DataInputStream inn = new DataInputStream(is);
            byte[] buffer = new byte[BTC];
            int lastcount  = 0;
            while ((count = inn.read(buffer)) > 0) {
                out.write(buffer, 0, BTC);
                out.flush();
            }

            byte [] endpty = new byte[BTC];
            Arrays.fill(endpty, (byte) 0);
            out.write(endpty, 0, BTC);
            out.flush();

            System.out.println("free bird");

        } else if (wm == 3) {
            return (String[]) e;
        }
        return null;
    }
}
