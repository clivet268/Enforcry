package clivet268.Operations;

import clivet268.FileEncryption.EncrypterDecrypter;
import clivet268.Util.Univ;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static clivet268.FileEncryption.EncrypterDecrypter.gen2048;
import static clivet268.Util.Univ.*;

public class MPIK extends Operation{
    @Override
    public void run() {
        Scanner s = new Scanner(System.in);
        System.out.println(getRandString(100));
        System.out.println("Enter your name: ");
        String namey = s.nextLine();
        System.out.println("Soup of the day?: ");
        String sdj = s.nextLine();
        System.out.println("What do you go by?: ");
        String pp = s.nextLine();
        //TODO file only/optional
        System.out.println("oKay");
        String kii = s.nextLine();
            List<Path> txtFiles = null;
            try {
                txtFiles = Files.walk(Path.of(enforcrysecretpath + File.separator + "party_list"))
                        //use to string here, otherwise checking for path segments
                        .filter(p -> p.toString().contains("." + sdj)).toList();

            boolean bag = true;
            for (Path e : txtFiles) {
                if(!bag){
                    break;
                }
                try {
                    String helt = EncrypterDecrypter.decrypt(Files.readString(e), kii);
                    int[] ee = new int[namey.length()];
                    //Name for rotating seed, with n as main generating factor and s as a constant offset
                    for (int i = 0; i < namey.length(); i++) {
                        ee[i] = (int)namey.charAt(i) * 19 + sdj.length() + Math.floorMod(namey.charAt(i) *namey.charAt(i) + ((int)namey.charAt(i) * 10000 / namey.length()), 43);
                    }
                    int totales = 0;
                    String yummers = "";
                    System.out.println("uhh" + helt.length());
                    for(int r = 0; r < pp.length(); r++){
                        int index = ee[r % (namey.length() - 1)];
                        System.out.println(ee[r % (namey.length() -1)]);
                        System.out.println(index);
                        System.out.println(totales);
                        yummers += helt.charAt(totales + index);
                        totales += index +1;
                    }
                    if (yummers.equals(pp)) {
                        bag = false;
                        System.out.println("Bingo");
                    }
                }
                catch (StringIndexOutOfBoundsException ignored){
                    System.out.println("uhoh");
                }
            }
                if(bag){
                    System.out.println("Do I Know You?");
                    boolean check;
                    try {
                        check = s.nextBoolean();
                    }
                    catch (InputMismatchException ex){
                        check = false;
                    }
                    if(check){
                        if(!genPI(namey,sdj,pp ,kii).equals("")){
                            System.out.println("Now I Do");
                        }
                        else {
                            System.out.println("Sorry Bub, You're Not On The List");
                        }
                    }
                    else{
                        System.out.println("Sorry Bub, You're Not On The List");
                    }

                }
        }catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String infoForOp(){
        return "Establishes IP";
    }
}
