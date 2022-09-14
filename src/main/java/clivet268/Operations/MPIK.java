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
                        if(genPI(namey,sdj,pp ,kii)){
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

    /*public static boolean genPI(String n, String s, String p, String k) throws IOException, NoSuchAlgorithmException {
        int[] e = new int[n.length()];
        //Name for rotating seed, with n as main generating factor and s as a constant offset
        for (int i = 0; i < n.length(); i++) {
            e[i] = (int)n.charAt(i) * 19 + s.length() + Math.floorMod(n.charAt(i) *n.charAt(i) + ((int)n.charAt(i) * 10000 / n.length()), 43);
        }

        String soom = "";
        Random ewer = new Random();
        //Start part
        //For each letter in the p add the p with random length random charters corresponding to the e's seed
        int tot = 0;
        for(int r = 0; r < p.length(); r++){
            System.out.println(e[r % (n.length() -1)]);
            tot += e[r % (n.length() -1)] +1;
            System.out.println(tot);
            soom = soom + getRandString(e[r % (n.length() -1)]);
            soom += p.charAt(r);
        }

        soom = soom + getRandString(e[ewer.nextInt(e.length -1)]);
        soom = soom + gen2048();

        //TODO in mem only
        //TODO delete unencrypted file
        //MAke the file
        System.out.println(soom);
        String hal = EncrypterDecrypter.encrypt(soom, k);
        System.out.println(hal);
        Path unencrypted = Path.of(enforcrysecretpath + File.separator + "party_list" + File.separator + Univ.getrandname() + "." + p);
        Files.createFile(unencrypted);
        Files.write(unencrypted, hal.getBytes());


        //Verify the Validity
        String helt = EncrypterDecrypter.decrypt(Files.readString(unencrypted), k);
        int[] ee = new int[n.length()];
        //Name for rotating seed, with n as main generating factor and s as a constant offset
        for (int i = 0; i < n.length(); i++) {
            ee[i] = (int)n.charAt(i) * 19 + s.length() + Math.floorMod(n.charAt(i) *n.charAt(i) + ((int)n.charAt(i) * 10000 / n.length()), 43);
        }
        int totales = 0;
        String yummers = "";
        System.out.println("uhh" + helt.length());
        for(int r = 0; r < p.length(); r++){
            int index = ee[r % (n.length() - 1)];
            System.out.println(e[r % (n.length() -1)]);
            System.out.println(index);
            System.out.println(totales);
            yummers += helt.charAt(totales + index);
            totales += index +1;
        }
        if(yummers.equals(p)){
            System.out.println("created");
            assert hal != null;
            return true;
        }
        else
        {
            return false;
        }

    }

     */


                /*
                String str = new String(FileEncrypterDecrypter.fdktostream(new File(e.toUri()), kii), StandardCharsets.UTF_8);
                String tstr = str;
                int count;
                count = str.split(namey, -1).length - 1;
                String ststs = "";
                if(s.hashCode() < 100){
                    ststs = String.valueOf(s.hashCode() * 132912 + 1);
                }
                else if (s.hashCode() < 1000){
                    ststs = String.valueOf(s.hashCode() * 23411);
                }
                else if (s.hashCode() < 10000){
                    ststs = String.valueOf(s.hashCode() * 5909);
                }
                else if (s.hashCode() < 100000){
                    ststs = String.valueOf(s.hashCode() * 163);
                }
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
                boolean check;
                try {
                    check = s.nextBoolean();
                }
                catch (InputMismatchException ex){
                    check = false;
                }
                if(check){
                    genpersonalidentifiers(namey,sdj,pp ,kii);
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

    public static void genpersonalidentifiers(String n, String s, String p, String k) throws NoSuchAlgorithmException, IOException {
        boolean bag = true;
        while (bag) {
            int yrey = 0;
            System.out.println(yrey);
            yrey++;
            String ststs = "";
            System.out.println(s.hashCode());
            if(s.hashCode() < 100){
                ststs = String.valueOf(s.hashCode() * 132912 + 1);
            }
            else if (s.hashCode() < 1000){
                ststs = String.valueOf(s.hashCode() * 23411);
            }
            else if (s.hashCode() < 10000){
                ststs = String.valueOf(s.hashCode() * 5909);
            }
            else if (s.hashCode() < 100000){
                ststs = String.valueOf(s.hashCode() * 163);
            }

            System.out.println(ststs);
            System.out.println(ststs.length());
            int mdj = Integer.parseInt(ststs.substring(ststs.length() - 4, ststs.length() - 1));
            int stedj = Integer.parseInt(ststs.substring(0, 6));
            Random er = new Random();
            System.out.println(yrey);
            yrey++;
            System.out.println("sussywussy");
            String e = getRandString(3* stedj + er.nextInt(100000));
            System.out.println(yrey);
            yrey++;
            for (int ieee = 0; ieee < mdj; ieee++) {
                int ewrw = er.nextInt(e.length() - 1 - n.length());
                e = e.substring(0, ewrw) + n + e.substring(ewrw + n.length());
            }
            System.out.println(yrey);
            yrey++;
            e = e.substring(0, stedj)
                    + k
                    + e.substring(stedj,
                    stedj + k.length());
            Random r = new Random();
            System.out.println(yrey);
            yrey++;
            //TODO make in mem solely
            //TODO smarter nickname
            File filee = new File(enforcrypartypath+ File.separator + getRandString(19 + r.nextInt(9)));
            System.out.println(yrey);
            yrey++;
            Files.write(filee.toPath(), e.getBytes());
            System.out.println(yrey + "sus");
            FileEncrypterDecrypter.fek(filee,enforcrypartypath, k,p );
            System.out.println("Let Me Check Again");

            //Test Valid PI
            List<Path> txtFiles = Files.walk(Path.of(enforcrysecretpath + File.separator + "party_list"))
                    //use to string here, otherwise checking for path segments
                    .filter(pe -> pe.toString().endsWith("." + s)).toList();
            for (Path flies : txtFiles) {
                String str = new String(FileEncrypterDecrypter.fdktostream(new File(flies.toUri()), k), StandardCharsets.UTF_8);
                String tstr = str;
                int count;
                count = str.split(n, -1).length - 1;
                System.out.println(count);
                String re = "";
                if(s.hashCode() < 100){
                    re = String.valueOf(s.hashCode() * 132912 + 1);
                }
                else if (s.hashCode() < 1000){
                    re = String.valueOf(s.hashCode() * 23411);
                }
                else if (s.hashCode() < 10000){
                    re = String.valueOf(s.hashCode() * 5909);
                }
                else if (s.hashCode() < 100000){
                    re = String.valueOf(s.hashCode() * 163);
                }
                String j = re.substring(re.length() - 4, re.length() - 1);
                String sttead = re.substring(0, 6);
                if (count == Integer.parseInt(j) || count == Integer.parseInt(j) - 1) {
                    System.out.println("Now I Do");
                    Enforcry.stk = tstr.substring(Integer.parseInt(sttead), Integer.parseInt(sttead) + 2048);
                    System.out.println("Accepted");
                    bag = false;
                    break;
                }
            }
            if(bag){
                System.out.println("Wait you're... uhhh...");
            }

        }
    }
    }
                 */

    @Override
    public String infoForOp(){
        return "Establishes IP";
    }
}
