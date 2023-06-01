package clivet268.Enforcry.SecureLine;

import org.jetbrains.annotations.Nullable;

import java.util.Scanner;

import static clivet268.Enforcry.Enforcry.getUsername;

public class EncryptedSecureLineClient extends ClientConnection {
    private static String sUnam = null;

    @Nullable
    public static String getsUnam() {
        System.out.println(sUnam);
        return sUnam;
    }

    //TODO C -> C?
    //TODO S -> S?
    @Override
    public void postConnect() {

        //Ports encrypted from here on
        //Communication Handshake continues
        EFCTP efctp = new EFCTP(in, out);
        try {
            out.writeIntE(405);

            int code = in.readIntE();
            if (code == 406) {
                sUnam = in.readUTFE();
                //Debug only?
                System.out.println(sUnam + " 0e");
            } else {
                close();
            }
            code = in.readIntE();
            if (code == 405) {
                out.writeIntE(406);
                out.writeUTFE(getUsername());
            } else {
                close();
            }

            //TODO server has burden of initialization, should it be this way?
            //Send the initial kick
            out.writeIntE(20);

            //TODO handle continues like this?
            //use EFCTP until user or program exits
            //continue flag
            boolean f = true;
            while (f) {
                //TODO conflicts with inner io interactions?
                //Check for errent command code reads
                int eewr = 0;
                while (eewr == 0 || eewr > 1000) {
                    //TODO error out? error code send?
                    eewr = in.readIntE();
                }
                int outcode = efctp.switcherClient(eewr);
                if (outcode == 1) {
                    Scanner userChoice = new Scanner(System.in);
                    System.out.println("Continue connection?");
                    if (!(userChoice.hasNextBoolean() && userChoice.nextBoolean())) {
                        out.writeIntE(22);
                        f = false;
                    }
                } else if (outcode == 2) {
                    out.writeIntE(22);
                    f = false;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
