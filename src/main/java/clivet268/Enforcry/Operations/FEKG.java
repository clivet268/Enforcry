package clivet268.Enforcry.Operations;

import java.util.Scanner;

public class FEKG extends Operation {
    @Override
    public void run() {
            Scanner s = new Scanner(System.in);
            System.out.println("Enter key to use");
        System.out.println("File Encrypted");
    }

    @Override
    public String infoForOp() {
        return "Encrypts a file from the file selection GUI";
    }
}
