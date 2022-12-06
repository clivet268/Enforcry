package clivet268.SecureLine;

import clivet268.Enforcry;
import clivet268.SecureLine.Commands.ExacutableCommand;

import java.io.DataInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import static clivet268.Enforcry.logger;
import static clivet268.Util.Univ.enforcrytestpath;
import static clivet268.Util.Univ.getrandname;

//ALWAYS FLUSH your non-UTF data
public class EFCTP {

    //Universal variables
    private EFCDataInputStream i;
    private EFCDataOutputStream o;
    private DataInputStream userin;
    ClientTextBox ctb;

    private final Scanner scanner = new Scanner(System.in);
    private static String senderUsername = null;

    //Pass through the data streams
    public EFCTP(EFCDataInputStream ii, EFCDataOutputStream oo) {
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

    //TODO server side calls to /exit when texting lead to the program just ending somehow...
    //TODO command # spoofing prevent with prompt number? must be 0
    //TODO Specifc error on code 13, use try catch and on recive get stacktrace string and print out (maybe)
    //Server depends
    public boolean switcherServer(int ic) throws Exception {
        switch (ic) {
            //TODO what is this?
            case (2) -> {
                o.writeIntE(1);
                o.flush();
            }

            //TODO variable buffer size?
            case (3) -> {
                for (byte[] currentOut : es.getOutput()) {
                    o.writeIntE(18);
                    o.flush();
                    //TODO biiig integer for biiiiiiiiiiiiig files :)
                    o.writeIntE(currentOut.length);
                    o.flush();
                    o.writeE(currentOut);
                    o.flush();
                }
                o.writeIntE(4);
                o.flush();
            }
            case (7) -> {
                String k = "";
                //Checking validity, re-ask if not a valid command
                while (!Enforcry.SLcommands.containsKey(k)) {
                    o.writeIntE(6);
                    o.flush();
                    k = i.readUTFE().toLowerCase();
                }
                logger.log(":)");
                es = Enforcry.SLcommands.get(k);
                if (es.commandPrompts().size() > 0) {
                    o.writeIntE(8);
                    o.flush();
                    break;
                } else {
                    es.run();
                }
                if (es.getOutput().size() > 0) {
                    //Debug only
                    //System.out.println(new String(es.getOutput().get(0)) + " \n:debug only");
                    o.writeIntE(2);
                    o.flush();
                }
                //TODO uhhh wont this break if there are actual files to send? i elseifed it but still maybe?
                //TODO just add a flag for now
                else if (es.getTnt() == 1) {
                    //TODO More formal text mode? GUI? this whole thing needs a GUI thats going to suck :(
                    //TODO currently there is ping-pong-packet, maybe a ping-pong-packet-check is in need?
                    // maybe even a ping-pong-packet-ding-dong? would be better suited if there were extra layers of
                    // security involved such as a changing value used to encrypt again
                    o.writeIntE(16);
                    o.flush();
                } else if (es.closeFlag()) {
                    o.writeIntE(22);
                    o.flush();
                } else {
                    //TODO else?
                }
            }
            case (12) -> {
                int pnum = 0;
                while (pnum < es.commandPrompts().size()) {
                    o.writeUTFE(es.commandPrompts().get(pnum));
                    inputedStrings.add(i.readUTFE());
                    o.writeIntE(8);
                    o.flush();
                    pnum++;
                }
                es.input = inputedStrings;
                es.run();
                if (es.getOutput().size() > 0) {
                    //Debug only
                    System.out.println(new String(es.getOutput().get(0)));
                    o.writeIntE(2);
                    o.flush();
                }
            }
            case (16) -> {
                //TODO how to exit texting mode (safely)
                System.out.println("\n---Texting---\n");
                TextListener t = new TextListener(i, o, scanner, senderUsername);
                Thread tthread = new Thread(t, "Server");
                tthread.start();
                tthread.join();
                System.out.println("\n---Exiting Texting---\n");
                o.writeIntE(25);
            }
            case (20) -> {
                senderUsername = EncryptedSecureLineServer.getcUnam();
                o.writeIntE(20);
                o.flush();
            }
            case (22) -> {
                return false;
            }
        }
        return true;
    }

    //Sending variables
    //TODO Base64 or byte[]??
    // Consider that byte[]s are a little trickier for encryption but if a meathod is made for that then the difference
    // is negligable and might even be better/more efficient with byte[]s

    //Client needs to flush!!!
    //0 - continue, 1 - ask, 2 - close
    public int switcherClient(int ic) throws Exception {
        switch (ic) {
            case (2) -> {
                o.writeIntE(3);
                o.flush();
            }
            case (4) -> {
                //TODO uhhhh what to do here
                o.writeIntE(7);
                o.flush();
            }
            case (6) -> {
                System.out.println("Enter Command:");
                String strin = scanner.nextLine();
                o.writeUTFE(strin);
            }
            case (8) -> {
                o.writeIntE(12);
                o.flush();
                System.out.println(i.readUTFE());
                o.writeUTFE(scanner.nextLine());
            }
            case (16) -> {
                //TODO how to exit texting mode (safely)
                o.writeIntE(16);
                System.out.println("\n---Texting---\n");
                TextListener t = new TextListener(i, o, scanner, senderUsername);
                Thread tthread = new Thread(t, "Client");
                tthread.start();
                tthread.join();
                System.out.println("\n---Exiting Texting---\n");
                return 1;
            }
            case (18) -> {
                //TODO big integer/ handle huge files
                int packetLength = i.readIntE();
                byte[] packet = new byte[packetLength];
                //TODO read fully?
                i.read(packet, 0, packetLength);
                String rn = getrandname();
                String fpath = enforcrytestpath + rn;
                Path of = Path.of(fpath);
                Files.createFile(of);
                Files.write(of, packet);
                System.out.println("File saved to " + rn);
                return 1;
            }

            //TODO needed? better way? right now the out just sends code 20 from the client class
            case (20) -> {
                senderUsername = EncryptedSecureLineClient.getsUnam();
                o.writeIntE(7);
                o.flush();
            }
            case (22) -> {
                return 2;
            }
            case (25) -> {
                return 1;
            }
        }

        return 0;
    }

}
