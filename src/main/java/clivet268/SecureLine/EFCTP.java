package clivet268.SecureLine;

import clivet268.Enforcry;
import clivet268.SecureLine.Commands.ExacutableCommand;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;

import static clivet268.Enforcry.logger;
import static clivet268.Util.Univ.enforcrytestpath;
import static clivet268.Util.Univ.getrandname;


public class EFCTP {

    //Universal variables
    private DataInputStream i;
    private DataOutputStream o;
    private final Scanner scanner = new Scanner(System.in);
    //Pass through the data streams
    public EFCTP(DataInputStream ii, DataOutputStream oo){
        i = ii;
        o = oo;
    }

    //Receiving variables
    private int promptNumber = 0;
    //Getting outputs
    private int outNum = 0;
    @SuppressWarnings("unused")
    private ArrayList<String> inputedStrings;

    private ExacutableCommand es;
    public int BTC = 16384;
    private InputStream is;
    private DataInputStream inn = new DataInputStream(is);

    //TODO command # spoofing prevent with prompt number? must be 0
    //TODO Specifc error on code 13, use try catch and on recive get stacktrace string and print out (maybe)
    public boolean switcherServer(int ic) throws IOException {
        switch (ic){
            case(0):{
                return false;
            }
            case(2): {
                o.writeInt(1);
                o.flush();
            }
            //TODO variable buffer size?
            case(3): {
                //TODO optimal? (lol)
                if(outNum >= es.getOutput().size()){
                    o.writeInt(4);
                o.flush();
                }
                is = new ByteArrayInputStream(es.getOutput().get(outNum));
                DataInputStream inn = new DataInputStream(is);
                byte[] buffer = new byte[BTC];
                if ((inn.read(buffer)) > 0) {
                    o.write(buffer, 0, BTC);
                    o.flush();
                    o.writeInt(5);
                o.flush();
                }
                else {
                    o.writeInt(18);
                o.flush();
                    outNum++;
                }
            }
            case(7): {
                logger.log(Level.INFO, "1");
                //Ready for the input
                o.writeInt(6);
                o.flush();
                //Getting the input from io
                String k = i.readUTF();
                //Checking validity, re-ask if not a valid command
                while (!Enforcry.SLcommands.containsKey(k)){
                    o.writeInt(6);
                    o.flush();
                    k = i.readUTF();
                }
                es =  Enforcry.SLcommands.get(k);
                if(es.commandPrompts().size() > 0) {
                    o.writeInt(8);
                o.flush();
                    o.writeUTF(es.input.get(promptNumber));
                }
                else {
                    es.input = inputedStrings;
                    es.run();

                }
                if (es.getOutput().size() > 0){
                    o.writeInt(2);
                o.flush();
                }
                //TODO uhhh wont this break if there are actual files to send? i elseifed it but still maybe?
                //TODO just add a flag for now
                else if(es.getTnt() == 1){
                    //TODO More formal text mode? GUI? this whole thing needs a GUI thats going to suck :(
                    //TODO currently there is ping-pong-packet, maybe a ping-pong-packet-check is in need?
                    // maybe even a ping-pong-packet-ding-dong? would be better suited if there were extra layers of
                    // security involved such as a changing value used to encrypt again
                    o.writeInt(16);
                o.flush();
                    System.out.println("Texting");
                    o.writeUTF(scanner.nextLine());
                }
                else{
                    o.writeInt(0);
                o.flush();
                }
            }
            case(12):{
                inputedStrings.add(i.readUTF());
                promptNumber++;
                if(promptNumber > es.commandPrompts().size()){
                    es.input = inputedStrings;
                    es.run();
                    if (es.getOutput().size() > 0){
                        o.writeInt(2);
                o.flush();
                    }
                }
                else{
                   o.writeInt(8);
                o.flush();
                   o.writeUTF(es.input.get(promptNumber));
                }
            }
            case(14):{
                o.writeInt(15);
                o.flush();
                System.out.println(i.readUTF());
            }
            case(15):{
                o.writeUTF(scanner.nextLine());
            }
            case(16):{
                System.out.println("Texting");
                o.writeUTF(scanner.nextLine());
            }
        }
        return true;
    }
    //TODO SENDER IS CLIENT< RECIVER IS SERVER, FIX VOCAB THIS IS ANNOYING

    //Sending variables
    //TODO Base64 or byte[]??
    // Consider that byte[]s are a little trickier for encryption but if a meathod is made for that then the difference
    // is negligable and might even be better/more efficient with byte[]s
    private ArrayList<byte[]> logicalBodies;
    private int currentLogicalBody = 0;
    byte[] sum = new byte[0];

    public boolean switcherClient(int ic) throws IOException {
        switch (ic){
            case(0): {

            }
            case(1): {
                o.writeInt(3);
                o.flush();
            }
            case(4):{
                //TODO needed or is this the same as 0, the only difference is what if they want to text?
            }
            case(5):{
                byte[] bytes = new byte[BTC];
                if (i.read(bytes) > 0) {
                    sum = ArrayUtils.addAll(sum, bytes);
                    o.writeInt(3);
                o.flush();
                }
                else{
                    //TODO else???
                }
            }
            //TODO 6 and 8 difference? needed? theyre both just a read from line but both command related so ???
            case(6): {
                logger.log(Level.INFO, "2312");
                System.out.println("Enter Command:");
                o.writeInt(12);
                o.flush();
                o.writeUTF(scanner.nextLine());
            }
            case(8):{
                System.out.println(i.readUTF());
                o.writeInt(12);
                o.flush();
                o.writeUTF(scanner.nextLine());
            }
            case(14):{
                o.writeInt(15);
                o.flush();
                System.out.println(i.readUTF());
            }
            case(15):{
                o.writeUTF(scanner.nextLine());
            }
            case(16):{
                System.out.println("Texting");
                o.writeUTF(scanner.nextLine());
            }
            case(18):{
                String fpath = enforcrytestpath + getrandname();
                Path of = Path.of(fpath);
                Files.createFile(of);
                Files.write(of, sum);
                o.writeInt(3);
                o.flush();
            }
            //TODO needed? better way? right now the out just sends code 20 from the client class
            case (20):{
                logger.log(Level.INFO, "0");
                o.writeInt(7);
                o.flush();
            }
        }
        return true;
    }

}
