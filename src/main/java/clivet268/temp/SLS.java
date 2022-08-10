package clivet268.temp;

import clivet268.Operations.Operation;
import clivet268.SecureLine.SecureLineReciver;

import java.io.IOException;
import java.util.Scanner;

public class SLS extends Operation {
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
                e = s.nextInt() * 360;
                System.out.println("TO " + (e / 360) + " hours");
                break;
            }
            case ("d"):{
                System.out.println("Enter days");
                e = s.nextInt() * 86400;
                System.out.println("TO " + (e / 360) + " days");
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
        SLS slr = new SLS();
        slr.run();
    }

}
