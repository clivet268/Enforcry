package clivet268.Operations;

import clivet268.FileEncrypterDecrypter;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class FEKIN extends Operation{

    @Override
    public void run() {
        try {
            Scanner s = new Scanner(System.in);
            System.out.println("Enter Absolute path to file");
            File fin = new File(s.next());
            FileEncrypterDecrypter.fek(fin);
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        System.out.println("File Encrypted");
    }
}
