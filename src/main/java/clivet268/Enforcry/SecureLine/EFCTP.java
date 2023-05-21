package clivet268.Enforcry.SecureLine;

import clivet268.Enforcry.Enforcry;
import clivet268.Enforcry.SecureLine.Commands.ExecutableCommand;
import org.apache.commons.lang3.tuple.Pair;

import java.io.DataInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import static clivet268.Enforcry.Util.Univ.ENFORCRYTESTPATH;
import static clivet268.Enforcry.Util.Univ.getrandname;

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
    private ArrayList<String> inputedStrings = new ArrayList<>();


    //TODO STATIC IS BAD! ALL THE ARRAYS ARE SAVED AND I DONT WANT TO CLEANEM EVERY TIME, THERE IS A BETTER WAY
    private ExecutableCommand es;

    //TODO server side calls to /exit when texting lead to the program just ending somehow...
    //TODO command # spoofing prevent with prompt number? must be 0
    //TODO Specifc error on code 13, use try catch and on recive get stacktrace string and print out (maybe)
    //Server depends
    public boolean switcherServer(int ic) throws Exception {
        switch (ic) {
            //TODO what is this?
            case (2) -> {
                o.writeIntE(1);
            }

            //TODO variable buffer size?
            case (3) -> {
                for (Pair<Integer, byte[]> currentout : es.getOutput()) {
                    if (currentout.getLeft() == 1) {
                        o.writeIntE(26);
                        o.writeUTFE(new String(currentout.getRight()));
                    }
                    if (currentout.getLeft() == 2) {
                        o.writeIntE(18);
                        //TODO biiig integer for biiiiiiiiiiiiig files :)
                        o.writeIntE(currentout.getRight().length);
                        o.writeE(currentout.getRight());
                    }
                }
                o.writeIntE(4);
            }
            //Starts looking for command, then sends command prompt,
            case (7) -> {
                String k = "";
                //Checking validity, re-ask if not a valid command
                while (!Enforcry.SLcommands.containsKey(k)) {
                    o.writeIntE(6);
                    k = i.readUTFE().toLowerCase();
                    System.out.println(senderUsername + " entered: " + k);
                }

                es = Enforcry.SLcommands.get(k);
                if (es.commandPrompts().size() > 0) {
                    int pnum = 0;
                    while (pnum < es.commandPrompts().size()) {
                        o.writeIntE(8);
                        o.writeUTFE(es.commandPrompts().get(pnum));
                        inputedStrings.add(i.readUTFE());
                        //TODO general ack needed for EFCTP, and this issue is 12ing after getting 8 every time exemplifies that
                        //Ignored
                        pnum++;
                    }
                    es.input = inputedStrings;
                    //Clear inputed trings array after handing it off to the command to be processed
                    inputedStrings = new ArrayList<>();
                    es.run();
                    if (es.getOutput().size() > 0) {
                        //Debug only
                        //System.out.println(new String(es.getOutput().get(0)));
                        o.writeIntE(2);
                    }
                } else {
                    es.run();
                }
                //TODO uhhh wont this break if there are actual files to send? i elseifed it but still maybe?
                //TODO just add a flag for now
                if (es.getTnt() == 1) {
                    //TODO More formal text mode? GUI? this whole thing needs a GUI thats going to suck :(
                    //TODO currently there is ping-pong-packet, maybe a ping-pong-packet-check is in need?
                    // maybe even a ping-pong-packet-ding-dong? would be better suited if there were extra layers of
                    // security involved such as a changing value used to encrypt again
                    o.writeIntE(16);
                } else if (es.closeFlag()) {
                    o.writeIntE(22);
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
                    //TODO general ack needed for EFCTP, and this issue is 12ing after getting 8 every time exemplifies that
                    //Ignored
                    pnum++;
                }
                es.input = inputedStrings;
                //Clear inputed trings array after handing it off to the command to be processed
                inputedStrings = new ArrayList<>();
                es.run();
                if (es.getOutput().size() > 0) {
                    //Debug only
                    //System.out.println(new String(es.getOutput().get(0)));
                    o.writeIntE(2);
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
                scanner.reset();
                o.writeIntE(33);
            }

            case (20) -> {
                senderUsername = EncryptedSecureLineServer.getcUnam();
                o.writeIntE(20);
            }
            //Exit
            case (22) -> {
                return false;
            }
            //After texting completes command prompt returns and promputs user
            case (33) -> {
                o.writeIntE(34);
            }
            default -> {
            }
        }
        return true;
    }

    //Sending variables
    //TODO Base64 or byte[]??
    // Consider that byte[]s are a little trickier for encryption but if a meathod is made for that then the difference
    // is negligable and might even be better/more efficient with byte[]s

    //Client needs to flush!!!
    public int switcherClient(int ic) throws Exception {
        switch (ic) {
            case (2) -> {
                o.writeIntE(3);
            }
            case (4) -> {
                //TODO uhhhh what to do here
                o.writeIntE(7);
            }
            case (6) -> {
                System.out.println("Enter Command:");
                if (scanner.hasNextLine()) {
                    String strin = scanner.nextLine();
                    o.writeUTFE(strin);
                } else {
                    o.writeIntE(7);
                }
            }


            case (8) -> {
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
                scanner.reset();
                o.writeIntE(33);
            }
            case (18) -> {
                //TODO big integer/ handle huge files
                int packetLength = i.readIntE();
                byte[] packet = new byte[packetLength];
                //TODO read fully?
                i.read(packet, 0, packetLength);
                String rn = getrandname();
                String fpath = ENFORCRYTESTPATH + rn;
                Path of = Path.of(fpath);
                Files.createFile(of);
                Files.write(of, packet);
                System.out.println("File saved to " + rn);
                return 1;
            }
            //Get Server username
            //TODO needed? better way? right now the out just sends code 20 from the client class
            case (20) -> {
                senderUsername = EncryptedSecureLineClient.getsUnam();
                o.writeIntE(7);
            }
            case (22) -> {
                return 2;
            }
            case (25) -> {
                return 1;
            }
            case (26) -> {
                System.out.println(i.readUTFE());
            }
            case (33), (34) -> {
                o.writeIntE(7);
            }
            default -> {
            }
        }

        return 0;
    }

}
