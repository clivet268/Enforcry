package clivet268.Operations;

import java.io.IOException;
import java.util.Scanner;

public class SLR extends Operation {
    @Override
    public void run() {
        Scanner s = new Scanner(System.in);
        int e = -1;
        System.out.println("Enter timeout minutes(m), hours(h), days(d) or -1 for none");
        String se = s.next();
        switch (se) {
            case ("m"):{
                System.out.println("Enter minutes");
                e = s.nextInt() * 60;
                System.out.println("TO " + (e / 60) + " minutes");
                break;
            }
            case ("h"):{
                System.out.println("Enter hours");
                e = s.nextInt() * 3600;
                System.out.println("TO " + (e / 3600) + " hours");
                break;
            }
            case ("d"):{
                System.out.println("Enter days");
                e = s.nextInt() * 86400;
                System.out.println("TO " + (e / 86400) + " days");
                break;
            }
            default:{
                e = -1;
            }
        }
        try {
            SecureLineReciver server = new SecureLineReciver(e);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    public static void main(String[] args) {
        SLR slr = new SLR();
        slr.run();
    }

}
