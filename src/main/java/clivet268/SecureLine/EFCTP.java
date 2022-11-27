package clivet268.SecureLine;

import clivet268.Enforcry;
import clivet268.SecureLine.Commands.ExacutableCommand;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class EFCTP {

    private static DataInputStream i;
    private static DataOutputStream o;
    private static Scanner scanner = new Scanner(System.in);
    private static int promptNumber = 0;
    private static ArrayList<String> inputedStrings;
    private static ExacutableCommand es;
    private


    public EFCTP(DataInputStream in, DataOutputStream out){
        this.i = in;
        this.o = out;
    }


    /**
     * Packs a string into a PAC
     * @param s
     * @return a new PAC
     */
    public static Pac packString(String s){
        return new Pac(s.getBytes());
    }


    public static void receive() throws IOException {
        while(true){
            switcherR(i.readInt());
        }
    }
    public static void send() throws IOException {
        while(true){
            switcherR(i.readInt());
        }
    }

    //TODO command # spoofing prevent with prompt number? must be 0
    //TODO Specifc error on code 13, use try catch and on recive get stacktrace string and print out (maybe)
    public static void switcherReciver(int ic) throws IOException {

        switch (ic){
            case(0):i0();
            case(2): {
                o.writeInt(1);
            }
            //TODO variable buffer size?
            case(3): {

            }
            case(7): {
                //Ready for the input
                o.writeInt(6);
                //Getting the input from io
                String k =  i.readUTF();
                //Checking validity
                if (!Enforcry.SLcommands.containsKey(k)) {
                    o.writeInt(13);
                    return;
                }
                else {
                   es =  Enforcry.SLcommands.get(k);
                }
                if(es.commandPrompts().size() > 0) {
                    o.writeInt(8);
                }
                else {
                    es.input = inputedStrings;
                    es.run();
                }
                if (es.getOutput().size() > 0){
                    o.writeInt(2);
                }
                if(es.getTnt() == 1){
                    //TODO More formal text mode? GUI? this whole thing needs a GUI thats going to suck :(
                    //TODO currently there is ping-pong-packet, maybe a ping-pong-packet-check is in need?
                    // maybe even a ping-pong-packet-ding-dong? would be better suited if there were extra layers of
                    // security involved such as a changing value used to encrypt again
                    o.writeInt(16);
                    System.out.println("Texting");
                    o.writeUTF(scanner.nextLine());
                }
                else{
                    o.writeInt(0);
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
                    }
                }
                else{
                   o.writeInt(8);
                }
            }
            case(14):{
                o.writeInt(15);
                System.out.println(i.readUTF());
            }
            case(15):{
                o.writeUTF(scanner.nextLine());
            }
        }
    }

    public void switcherSender(int ic, Pac p) throws IOException {
        switch (ic){
            case(0):o0();
            case(1): {
                o.writeInt(3);
            }
            case(4):{

            }
            case(5):{

            }
            case(6): {
                o.writeInt(12);
                o.writeUTF(scanner.nextLine());
            }
            case(7):i7();
            case(8):{
                o.writeInt(12);
                o.writeUTF(scanner.nextLine());
            }
            case(9):
            case(10):i10();
            case(11):i10();
            case(14):{
                o.writeInt(15);
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
    }


    //Switcher and command code handlers



}
