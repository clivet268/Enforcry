package clivet268.Operations;

import clivet268.FileEncryption.FileEncrypterDecrypter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class FDK extends Operation {
    @Override
    public void run() {
        try {
            Scanner s = new Scanner(System.in);
            System.out.println("Enter key");
            String k = s.next();
            FileEncrypterDecrypter.fdk(k);
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        System.out.println("File Decrypted");
    }
    @Override
    public String infoForOp(){
        return "Decrypts a file from the file selection GUI";
    }
}
