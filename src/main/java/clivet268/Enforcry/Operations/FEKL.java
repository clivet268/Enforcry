package clivet268.Enforcry.Operations;

import clivet268.Enforcry.Encryption.EncrypterDecrypter;

public class FEKL extends Operation {
    @Override
    public void run() {
        System.out.println("Warning, this does not necessarily change files within within Enforcry's home dir");
        try {
            EncrypterDecrypter.fekl();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("File Encrypted");
    }

    @Override
    public String infoForOp() {
        return "Encrypts a file from the file selection GUI";
    }
}
