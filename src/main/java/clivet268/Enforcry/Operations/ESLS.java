package clivet268.Enforcry.Operations;

import clivet268.Enforcry.SecureLine.EncryptedSecureLineServer;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

public class ESLS extends Operation {
    //TODO use new simpler time setting method found in AMOE
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
            case ("h"): {
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
            EncryptedSecureLineServer server = new EncryptedSecureLineServer();
            server.connect(26817, 100);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        }

    }

    public static void main(String[] args) {
        ESLS slr = new ESLS();
        slr.run();
    }

}
