package clivet268.Enforcry.Operations;

import clivet268.Enforcry.SecureLine.BucketServerConnection;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static clivet268.Enforcry.Util.Univ.USERAMOEPATH;

public class AMOER extends Operation {
    @Override
    public void run() {
        CommandSource c = new CommandSource();
        c.init();
    }

    private static class CommandSource extends BucketServerConnection {
        public void init() {

            try {
                connect(28770, 100);
            } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
        }

        //TODO handle exceptions higher up?
        @Override
        public void postConnect() {
            try {
                System.out.println(in.readIntE());
                out.writeIntE(998);
                JSONObject j = new JSONObject(in.readUTFE());
                Path of = Path.of(USERAMOEPATH + j.get("username") + "-" + j.get("date"));
                Files.createFile(of);
                OutputStream os = Files.newOutputStream(of, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
                os.write(j.toString().getBytes());
                os.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }


    }

    @Override
    public String infoForOp() {
        return "Sets up a process in the background logging information to show this device's \n" + "location through ip and relays it to the designated ip on a select interval.";
    }
}
