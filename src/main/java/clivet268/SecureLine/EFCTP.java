package clivet268.SecureLine;

import clivet268.Enforcry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class EFCTP {

    static DataInputStream i;
    static DataOutputStream o;

    static Scanner scanner = new Scanner(System.in);


    public EFCTP(DataInputStream in, DataOutputStream out){
        this.i = in;
        this.o = out;
    }

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

    public static int switcherR(int ic) throws IOException {

        switch (ic){
            case(0):i0();
            case(1):i1();
            case(2): {
                return 1;
            }
            case(3):i3();
            case(4):i4();
            case(5):i5();
            case(6):i6();
            case(7): {
                return 6;
            }
            case(8):i8();
            case(9):i9();
            case(10):i10();
            case(11):i10();
            case(12):{
                if (!Enforcry.SLcommands.containsKey(i.readUTF())) {
                    o.writeInt(9);
                }
            }
        }
    }

    public void switcherS(int ic, Pac p) throws IOException {
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
