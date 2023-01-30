package clivet268;

import clivet268.Encryption.Asymmetric;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class EnforcrySetup {
    public static void setup() throws Exception {
        Scanner sin = new Scanner(System.in);
        String password = sin.nextLine();
        String username = sin.nextLine();
        Path of = Path.of(File.separator + "Enforcry");
        if (Files.notExists(of)) {
            Files.createDirectory(of);
        }
        byte[] privateKey = Asymmetric.generateRSAKkeyPair().getPrivate().getEncoded();

    }

    public static void main(String[] args) throws Exception {
        setup();
    }
}
