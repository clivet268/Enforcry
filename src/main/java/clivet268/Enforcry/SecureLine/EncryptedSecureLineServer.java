package clivet268.Enforcry.SecureLine;

import org.jetbrains.annotations.Nullable;

import static clivet268.Enforcry.Enforcry.getUsername;

public class EncryptedSecureLineServer extends ServerConnection {

    private static String cUnam = null;

    @Nullable
    public static String getcUnam() {
        return cUnam;
    }

    @Override
    public void postConnect() {
        try {
            //Ports encrypted from here on
            //Communication Handshake continues
            EFCTP efctp = new EFCTP(in, out);
            int code = in.readIntE();
            if (code == 405) {
                out.writeIntE(406);
                out.writeUTFE(getUsername());
            } else {
                close();
            }
            out.writeIntE(405);
            code = in.readIntE();
            if (code == 406) {
                cUnam = in.readUTFE();
                System.out.println(cUnam);
            } else {
                close();
            }


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
                f = efctp.switcherServer(eewr);
            }

            //Close when done
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
