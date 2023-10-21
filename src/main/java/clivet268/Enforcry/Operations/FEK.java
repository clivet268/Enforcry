package clivet268.Enforcry.Operations;

import clivet268.Enforcry.Encryption.EncrypterDecrypter;

public class FEK extends Operation{
    @Override
    public void run() {
        EncrypterDecrypter.fek();
        System.out.println("File Encrypted");
    }
    @Override
    public String infoForOp(){
        return "Encrypts a file from the file selection GUI";
    }
}
