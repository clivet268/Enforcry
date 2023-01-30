package clivet268.Operations;

import clivet268.Util.Univ;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class NDARY extends Operation {
    @Override
    public void run() {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter directory you would like to add as ndary source");
        String ndary = s.nextLine();
        if (!Files.exists(Path.of(ndary))) {
            System.out.println("Dir not found");
        } else if (Files.isReadable(Path.of(ndary))) {
            System.out.println("Dir not readable");
        } else {

            Univ.ndaryDirs.add(s.next());
        }
    }

    @Override
    public String infoForOp() {
        return "Allows a __ndary file to be accessed as a n alternate enforcry path";
    }
}
