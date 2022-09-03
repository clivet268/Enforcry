package clivet268.Operations;

import clivet268.Enforcry;
import clivet268.FileEncryption.FileEncrypterDecrypter;
import clivet268.Util.Univ;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static clivet268.Util.Univ.*;

public class MNG extends Operation{
    @Override
    public void run() {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String namey = s.nextLine();
        System.out.println("Soup of the day?: ");
        String sdj = s.nextLine();
        System.out.println("What do you go by?: ");
        String pp = s.nextLine();
        try {
            List<Path> txtFiles = Files.walk(Path.of(enforcrysecretpath + File.separator + "party_list"))
                    //use to string here, otherwise checking for path segments
                    .filter(p -> p.toString().endsWith("." + sdj)).toList();
            boolean bag = true;
            for(Path e : txtFiles){
                String str = new String(FileEncrypterDecrypter.fdktostream(new File(e.toUri()), pp), StandardCharsets.UTF_8);
                String tstr = str;
                int count;
                count = str.split(namey, -1).length - 1;
                String ststs = String.valueOf(sdj.hashCode());
                String mdj = ststs.substring(ststs.length() - 4, ststs.length() -1);
                String stedj = ststs.substring(0 , 6);
                if(count == Integer.parseInt(mdj) || count == Integer.parseInt(mdj) - 1){
                    System.out.println("I Know You");
                    Enforcry.stk = tstr.substring(Integer.parseInt(stedj), Integer.parseInt(stedj) + 2048);
                    System.out.println("Accepted");
                    bag = false;
                    break;
                }
            }
            if(bag){
                System.out.println("Do I Know You?");
                boolean check = s.nextBoolean();
                if(check){
                    genpersonalidentifiers(namey,sdj,pp);
                }
                else{
                    System.out.println("Sorry Bub, You're Not On The List");
                }

            }

        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        System.out.println("File Encrypted");
    }
    @Override
    public String infoForOp(){
        return "Encrypts a file given the absolute path to the file";
    }
}
