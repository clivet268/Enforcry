package clivet268.Operations;

import clivet268.Util.Univ;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import static clivet268.Encryption.EncrypterDecrypter.gen2048;
import static clivet268.Util.Univ.getRandString;

public class CEE extends Operation{
    @Override
    public void run() {
        try {
            System.out.println("CL or F");
            Scanner s = new Scanner(System.in);
            if(s.nextLine().equalsIgnoreCase("cl")){
                System.out.println(gen2048());
            }
            else{
                //TODO place for file?
                System.out.println("Key saved, make sure you get all of it ;)\n_\n\n");
                Files.write(Path.of(Univ.ENFORCRYBASEPATH + "." + getRandString() + "key"), gen2048().getBytes());
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Generated");
    }
    @Override
    public String infoForOp(){
        return "Returns a key";
    }
}
