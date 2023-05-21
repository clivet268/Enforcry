package clivet268.Enforcry.Operations;

import clivet268.Enforcry.SecureLine.EncryptedSecureLineClient;

import java.util.Scanner;

public class ESLC extends Operation {
    @Override
    public void run() {

        Scanner s = new Scanner(System.in);
        System.out.println("Enter IP address");
        String ipad = s.next();
        int e = -1;
        System.out.println("Enter timeout minutes(m), hours(h), days(d) or -1 for none");
        String se = s.next();
        switch (se) {
            case ("m") -> {
                System.out.println("Enter minutes");
                e = s.nextInt() * 60;
                System.out.println("TO " + (e / 60) + " minutes");
            }
            case ("h") -> {
                System.out.println("Enter hours");
                e = s.nextInt() * 360;
                System.out.println("TO " + (e / 360) + " hours");
            }
            case ("d") -> {
                System.out.println("Enter days");
                e = s.nextInt() * 86400;
                System.out.println("TO " + (e / 360) + " days");
            }
            default -> {
                e = -1;
            }
        }
        try {
            EncryptedSecureLineClient client = new EncryptedSecureLineClient(ipad, e);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    public static void main(String[] args) {
        ESLC slr = new ESLC();
        slr.run();
    }

}
