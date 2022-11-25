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
    public static void switcherReciver(int ic) throws IOException {

        switch (ic){
            case(0):i0();
            case(1):i1();
            case(2): {
                o.writeInt(1);
            }
            case(3):i3();
            case(4):i4();
            case(5):i5();
            case(6):i6();
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
                    es.run();
                }
                if (es.getOutput().size() > 0){
                    o.writeInt(2);
                }
                else{
                    o.writeInt();
                }
            }
            case(8):i8();
            case(9):i9();
            case(10):i10();
            case(11):i10();
            case(12):{

            }
        }
    }

    public void switcherSender(int ic, Pac p) throws IOException {
        switch (ic){
            case(0):o0();
            case(1): {
                o.writeUTF(p.plrs);
            }
            case(2):o2();
            case(3):o3();
            case(4):4();
            case(5):i5();
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
        }
    }


    //Switcher and command code handlers



}
