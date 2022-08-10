package clivet268.Operations;

import clivet268.FileEncrypterDecrypter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class O extends Operation{
    @Override
    public void run() {
        try {
            FileEncrypterDecrypter.fek();
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        System.out.println("File Encrypted");
    }
}
