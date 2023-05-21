package clivet268.Enforcry.Operations;

import clivet268.Enforcry.Encryption.EncrypterDecrypter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class FEK extends Operation{
    @Override
    public void run() {
        try {
            EncrypterDecrypter.fek();
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        System.out.println("File Encrypted");
    }
    @Override
    public String infoForOp(){
        return "Encrypts a file from the file selection GUI";
    }
}
