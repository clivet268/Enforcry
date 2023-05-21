package clivet268.Enforcry.Util;

import clivet268.Enforcry.Encryption.EncrypterDecrypter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

public class DriveKeyWrite {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        int[] keyyspots = new int[8];
        ArrayList<String> mun = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 10000; i++) {
            mun.add(EncrypterDecrypter.genX(2));
        }
        for (int ii = 0; ii <= 8; ii++) {
            String s = EncrypterDecrypter.genX(2);
            int rr = r.nextInt(10000);
            keyyspots[ii] = rr;
            mun.set(rr, s);
        }


        System.out.println("Select base device path");
        File keyfile = Univ.filechooser();
        System.out.println("Keyspots written, make sure you get all of them");
        for (int i : keyyspots) {
            System.out.print(i + " ");
        }
        System.out.println();
        Files.createFile(Path.of(keyfile.toPath() + File.separator + "key"));


    }
}
