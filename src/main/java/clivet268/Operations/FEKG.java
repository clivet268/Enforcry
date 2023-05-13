package clivet268.Operations;

import clivet268.Encryption.EncrypterDecrypter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class FEKG extends Operation {
    @Override
    public void run() {
        try {
            Scanner s = new Scanner(System.in);
            System.out.println("Enter key to use");
            EncrypterDecrypter.fekg(s.next());
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        System.out.println("File Encrypted");
    }

    @Override
    public String infoForOp() {
        return "Encrypts a file from the file selection GUI";
    }
}
