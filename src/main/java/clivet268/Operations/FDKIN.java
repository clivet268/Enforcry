package clivet268.Operations;

import clivet268.Encryption.EncrypterDecrypter;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class FDKIN extends Operation{
    @Override
    public void run() {
        try {
            Scanner s = new Scanner(System.in);
            System.out.println("Enter Absolute path to file");
            File fin = new File(s.next());
            System.out.println("Enter key");
            String k = s.next();
            EncrypterDecrypter.fdk(fin,k);
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        System.out.println("File Decrypted");
    }
    @Override
    public String infoForOp(){
        return "Decrypts a file given the absolute path to the file";
    }
}
