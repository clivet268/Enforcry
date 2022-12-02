package clivet268.SecureLine;

import clivet268.Enforcry;
import clivet268.SecureLine.Commands.ExacutableCommand;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;

import static clivet268.Enforcry.logger;
import static clivet268.Util.Univ.enforcrytestpath;
import static clivet268.Util.Univ.getrandname;

//ALWAYS FLUSH your non-UTF data
public class EFCTP {

    //Universal variables
    private DataInputStream i;
    private DataOutputStream o;
    private DataInputStream userin;
    ClientTextBox ctb;
    public static final Object lock = new Object();

    private final Scanner scanner = new Scanner(System.in);
    //Pass through the data streams
    public EFCTP(DataInputStream ii, DataOutputStream oo ){
        //ClientTextBox clientTextBox){
        i = ii;
        o = oo;
        //ctb = clientTextBox;

    }

    //Receiving variables

    //Getting outputs
    private int outNum = 0;
    @SuppressWarnings("unused")
    private ArrayList<String> inputedStrings;

    private ExacutableCommand es;

    //TODO command # spoofing prevent with prompt number? must be 0
    //TODO Specifc error on code 13, use try catch and on recive get stacktrace string and print out (maybe)
    public boolean switcherServer(int ic) throws IOException, InterruptedException {
        switch (ic) {
            //TODO what is this?
            case (2) -> {
                o.writeInt(1);
                o.flush();
            }

            //TODO variable buffer size?
            case (3) -> {
                for (byte[] currentOut : es.getOutput()) {
                    logger.log(Level.INFO, "193regrebrebebrerbabre2");
                    o.writeInt(18);
                    o.flush();
                    //TODO biiig integer for biiiiiiiiiiiiig files :)
                    o.writeInt(currentOut.length);
                    o.flush();
                    o.write(currentOut);
                    o.flush();
                }
                o.writeInt(4);
                o.flush();
            }
            case (7) -> {
                logger.log(Level.INFO, "1");
                String k = "";
                //Checking validity, re-ask if not a valid command
                while (!Enforcry.SLcommands.containsKey(k)) {
                    logger.log(Level.INFO, "102");
                    o.writeInt(6);
                    o.flush();
                    logger.log(Level.INFO, "1932");
                    k = i.readUTF().toLowerCase();
                }
                es = Enforcry.SLcommands.get(k);
                logger.log(Level.INFO, "332");
                if (es.commandPrompts().size() > 0) {
                    logger.log(Level.INFO, "fewo");
                    o.writeInt(8);
                    o.flush();
                } else {
                    logger.log(Level.INFO, "121");
                    es.run();
                }
                if (es.getOutput().size() > 0) {
                    logger.log(Level.INFO, "2z2onihubhouou");
                    //Debug only
                    System.out.println(new String(es.getOutput().get(0)));
                    o.writeInt(2);
                    o.flush();
                    logger.log(Level.INFO, "uhhwfae9h298923bubyufg");
                }
                //TODO uhhh wont this break if there are actual files to send? i elseifed it but still maybe?
                //TODO just add a flag for now
                else if (es.getTnt() == 1) {
                    logger.log(Level.INFO, "mai223douou");
                    //TODO More formal text mode? GUI? this whole thing needs a GUI thats going to suck :(
                    //TODO currently there is ping-pong-packet, maybe a ping-pong-packet-check is in need?
                    // maybe even a ping-pong-packet-ding-dong? would be better suited if there were extra layers of
                    // security involved such as a changing value used to encrypt again
                    o.writeInt(16);
                    o.flush();
                } else {
                    logger.log(Level.INFO, "w9fj9jwe");
                    //TODO else?
                }
                logger.log(Level.INFO, "maidouou");
            }
            case (12) -> {
                int pnum = 0;
                logger.log(Level.INFO, "qoiqw");
                while (pnum < es.commandPrompts().size()) {
                    logger.log(Level.INFO, "tj990");
                    o.writeInt(8);
                    o.flush();
                    o.writeUTF(es.input.get(pnum));
                    inputedStrings.add(i.readUTF());
                    pnum++;
                    logger.log(Level.INFO, "30496uhu34");
                }
                es.input = inputedStrings;
                es.run();
                if (es.getOutput().size() > 0) {
                    //Debug only
                    System.out.println(new String(es.getOutput().get(0)));
                    logger.log(Level.INFO, "uenique14918");
                    o.writeInt(2);
                    o.flush();
                }
                logger.log(Level.INFO, "232323");
            }
            case (16) -> {
                System.out.println("Texting");
                TextListener t = new TextListener(i, o, scanner);
                Thread tthread = new Thread(t, "Server");
                tthread.start();
                //TODO is this broken to need this?
                //Kick the client into action
                o.writeInt(14);
                o.flush();
                o.writeUTF("");
                synchronized (lock) {
                    lock.wait();
                }
            }
        }
        logger.log(Level.INFO, "ewnjowfuhofio");
        return true;
    }

    //Sending variables
    //TODO Base64 or byte[]??
    // Consider that byte[]s are a little trickier for encryption but if a meathod is made for that then the difference
    // is negligable and might even be better/more efficient with byte[]s

    //Client needs to flush!!!
    public boolean switcherClient(int ic) throws IOException, InterruptedException {
        switch (ic) {
            case (2) -> {
                o.writeInt(3);
                o.flush();
            }
            case (4) -> {
                //TODO uhhhh what to do here
                logger.log(Level.INFO, "nio3ogg43iooi34oig34");
                o.writeInt(7);
                o.flush();
            }
            case (6) -> {
                logger.log(Level.INFO, "2312");
                System.out.println("Enter Command:");
                String strin = scanner.nextLine();
                o.writeUTF(strin);
                logger.log(Level.INFO, "2443312");
            }
            case (8) -> {
                logger.log(Level.INFO, "q23r223qw");
                System.out.println(i.readUTF());
                o.writeInt(12);
                o.flush();
                logger.log(Level.INFO, "q332fffqw");
                o.writeUTF(scanner.nextLine());
            }
            case (16) -> {
                o.writeInt(16);
                //TODO brokey? why need to do this
                //Clear utf buffer
                i.readUTF();
                System.out.println("Texting");
                TextListener t = new TextListener(i, o, scanner);
                logger.log(Level.INFO, "q332fffqw");
                Thread tthread = new Thread(t, "Client");
                tthread.start();
                logger.log(Level.INFO, "q332fffqw");
                synchronized (lock) {
                    lock.wait();
                }
                logger.log(Level.INFO, "ewfew76543212345678");
            }
            case (18) -> {
                //TODO big integer
                logger.log(Level.INFO, "viberuoahuigyutg87");
                int packetLength = i.readInt();
                byte[] packet = new byte[packetLength];
                logger.log(Level.INFO, "korefibo9932993932");
                i.readFully(packet, 0, packetLength);
                logger.log(Level.INFO, "ji3459hu4wiuunoigwuinouibhubu");
                String rn = getrandname();
                String fpath = enforcrytestpath + rn;
                Path of = Path.of(fpath);
                Files.createFile(of);
                Files.write(of, packet);
                System.out.println("File saved to " + rn);
            }

            //TODO needed? better way? right now the out just sends code 20 from the client class
            case (20) -> {
                logger.log(Level.INFO, "0");
                o.writeInt(7);
                o.flush();
            }
        }

        logger.log(Level.INFO, "070");
        return true;
    }

}
