package clivet268.Enforcry.Operations;

import clivet268.Enforcry.Util.Univ;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

//TODO needed? yes accessing outside directories in a controlled method is necessary, needs to be polished and used better
public class NDARY extends Operation {
    @Override
    public void run() {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter directory you would like to add as ndary source");
        String ndary = s.nextLine();
        Path of = Path.of(ndary);
        if (!Files.exists(of)) {
            System.out.println("Dir not found");
        } else if (Files.isReadable(of)) {
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
