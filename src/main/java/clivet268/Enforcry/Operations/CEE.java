package clivet268.Enforcry.Operations;

import clivet268.Enforcry.Encryption.ChaCha20Poly1305;
import clivet268.Enforcry.Util.Univ;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static clivet268.Enforcry.Util.Univ.getRandString;

public class CEE extends Operation{
    @Override
    public void run() {
        try {
            System.out.println("CL or F");
            Scanner s = new Scanner(System.in);
            if (s.nextLine().equalsIgnoreCase("cl")) {
                System.out.println(ChaCha20Poly1305.genChaCha20());
            } else {
                //TODO place for file?
                System.out.println("Key saved, make sure you get all of it ;)\n_\n\n");
                Files.write(Path.of(Univ.ENFORCRYBASEPATH + "." + getRandString() + "key"), ChaCha20Poly1305.genChaCha20().toString().getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Generated");
    }
    @Override
    public String infoForOp(){
        return "Returns a key";
    }
}
