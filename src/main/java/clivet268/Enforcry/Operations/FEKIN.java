package clivet268.Enforcry.Operations;

import clivet268.Enforcry.Encryption.EncrypterDecrypter;

import java.io.File;
import java.util.Scanner;

//TODO redundant?
public class FEKIN extends Operation{

    @Override
    public void run() {
        try {
            Scanner s = new Scanner(System.in);
            System.out.println("Enter Absolute path to file");
            File fin = new File(s.next());
            EncrypterDecrypter.fek(fin);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("File Encrypted");
    }

    @Override
    public String infoForOp(){
        return "Encrypts a file given the absolute path to the file";
    }
}
