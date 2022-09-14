package clivet268.Operations;

import clivet268.SecureLine.EncryptedSecureLineReciver;
import clivet268.SecureLine.SecureLineReciver;

import java.io.IOException;
import java.util.Scanner;

public class ESLR extends Operation {
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
            EncryptedSecureLineReciver server = new EncryptedSecureLineReciver(e);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    public static void main(String[] args) {
        ESLR slr = new ESLR();
        slr.run();
    }

}