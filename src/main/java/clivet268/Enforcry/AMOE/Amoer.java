package clivet268.Enforcry.AMOE;

import clivet268.Enforcry.SecureLine.ServerConnection;
import org.json.JSONObject;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static clivet268.Enforcry.Util.Univ.USERAMOEPATH;

public class Amoer extends ServerConnection {

    //TODO handle exceptions higher up?
    @Override
    public void postConnect() {
        try {
            System.out.println(in.readUTFE());
            while (true) {
                System.out.println(in.readIntE());
                out.writeIntE(998);
                String jsin = in.readUTFE();
                System.out.println(jsin);
                JSONObject j = new JSONObject(jsin);
                Path of = Path.of(USERAMOEPATH + j.get("username") + "-" + j.get("date"));
                Files.createFile(of);
                OutputStream os = Files.newOutputStream(of, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
                os.write(j.toString().getBytes());
                os.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
