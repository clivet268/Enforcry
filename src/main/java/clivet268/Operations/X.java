package clivet268.Operations;

import clivet268.FileEncrypterDecrypter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class X extends Operation{
    @Override
    public void run() {
        this.exitflag = false;
        System.out.println("Exiting");
        System.exit(0);
    }
}
