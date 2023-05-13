package clivet268.Operations;

import clivet268.Encryption.EncrypterDecrypter;

import java.io.IOException;
import java.util.Scanner;

public class FDKL extends Operation {
    @Override
    public void run() {
        try {
            Scanner s = new Scanner(System.in);
            System.out.println("Enter key");
            String k = s.next();
            EncrypterDecrypter.fdkl(k);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("File Decrypted");
    }

    @Override
    public String infoForOp() {
        return "Decrypts a file from the file selection GUI";
    }
}
