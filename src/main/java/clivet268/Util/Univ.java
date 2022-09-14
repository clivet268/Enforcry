package clivet268.Util;

import clivet268.FileEncryption.EncrypterDecrypter;
import org.apache.commons.lang3.ArrayUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static clivet268.FileEncryption.EncrypterDecrypter.gen2048;

public class Univ {

    public static final int BTC =  65536;
    public static String enforcrybasepath = System.getProperty("user.home") + File.separator + "Enforcry/";
    public static String enforcrytestpath = System.getProperty("user.home") + File.separator + "Enforcry/test/";
    public static String enforcrysecretpath = System.getProperty("user.home") + File.separator + "Enforcry/-/";
    public static String enforcrypartypath = enforcrysecretpath + File.separator + "sl"+ File.separator + "party_list";
    public static String enforcryfoutpath = System.getProperty("user.home") + File.separator + "Enforcry/fout/";

    public Univ(){
        createPaths();
    }

    public static String normalStringSet = "abcedfghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789-_";
    public static char[] charset = normalStringSet.toCharArray();
    public static String upperStringSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static void createPaths(){
        new File(enforcryfoutpath).mkdirs();
        new File(enforcrytestpath).mkdirs();
        new File(enforcrysecretpath).mkdirs();
        new File(enforcryfoutpath).mkdirs();
        new File(enforcrypartypath).mkdirs();
    }
    public static File filechooser(){
        File file = null;
        JFrame myFrame = new JFrame("Frame Title");
        myFrame.setVisible(true);
        myFrame.requestFocus();

        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(System.getProperty("user.home")));
        fc.updateUI();
        int returnVal = fc.showOpenDialog(myFrame);
        System.out.println(fc.isVisible());
        if (!fc.isVisible()) {
            fc.setVisible(true);
            fc.requestFocus();
        }
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            // Retrieve the selected file
            file = fc.getSelectedFile();
        }
        else{
            System.out.println(returnVal);
        }
        myFrame.setVisible(false);
        return file;

    }

    public static String getrandname() {
        Random ran = new Random();
        String nam = "";
        for (int i = 0; i < ran.nextInt(18, 45); i++) {
            nam += ran.nextInt(9);
        }
        return nam;
    }
    //TODO nor workey?
    public static String getRandString() {
        Random ran = new Random();
        String nam = "";
        for (int i = 0; i < i+ran.nextInt(18, 45); i++) {
            nam += normalStringSet.charAt(ran.nextInt(9));
        }
        return nam;
    }

    public static String getRandString(int ii) {
        Random ran = new Random();
        String nam = "";
        for (int i = 0; i < ii; i++) {
            nam += charset[(ran.nextInt(normalStringSet.length() - 1))];
        }
        return nam;
    }

    public static byte[] trimTrailingZeros(byte[] bytes){
        String e = "";
        for (byte b: bytes) {
            e += Byte.toString(b);
        }
        System.out.println(e);
        ArrayUtils.reverse(bytes);
        e ="";
        int i  = 0;
        while (bytes[i] == 0){
            i++;
            if (i >= bytes.length){
                return new byte[0];
            }
        }
        ArrayUtils.reverse(bytes);
        for (byte b: bytes) {
            e += Byte.toString(b);
        }

        byte[] nbytes =new byte[bytes.length - i];
        System.arraycopy(bytes, 0, nbytes, 0, bytes.length - i);
        return nbytes;
    }

    public static byte[] zchecker(byte[] bytes){
        int i  = 0;
        while (bytes[i] == 0){
            i++;
            if (i >= bytes.length){
                return new byte[0];
            }
        }
        return bytes;
    }

    public static byte[] trimTrailingBytes(byte[] bytes, byte trimmings){
        String e = "";
        for (byte b: bytes) {
            e += Byte.toString(b);
        }
        System.out.println(e);
        ArrayUtils.reverse(bytes);
        e ="";
        int i  = 0;
        while (bytes[i] == trimmings){
            i++;
            if (i >= bytes.length){
                return new byte[0];
            }
        }
        ArrayUtils.reverse(bytes);
        for (byte b: bytes) {
            e += Byte.toString(b);
        }

        byte[] nbytes =new byte[bytes.length - i];
        System.arraycopy(bytes, 0, nbytes, 0, bytes.length - i);
        return nbytes;
    }

    public static byte[] trimLeadingZeros(byte[] bytes){
        int i  = 0;
        while (bytes[i] == 0){
            i++;
            if (i >= bytes.length){
                return new byte[0];
            }
        }

        byte[] nbytes =new byte[bytes.length - i];
        System.arraycopy(bytes, 0, nbytes, 0, bytes.length - i);
        return nbytes;
    }
    public static <T> T[] concatWithCollection(T[] array1, T[] array2) {
        List<T> resultList = new ArrayList<>(array1.length + array2.length);
        Collections.addAll(resultList, array1);
        Collections.addAll(resultList, array2);

        @SuppressWarnings("unchecked")
        //the type cast is safe as the array1 has the type T[]
        T[] resultArray = (T[]) Array.newInstance(array1.getClass().getComponentType(), 0);
        return resultList.toArray(resultArray);
    }

    public static String[] getPrompts(){
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
        System.out.println("Do I Know You?");
        String yn = s.nextLine();
        return new String[]{namey, sdj, pp, kii, yn};
    }

    public static String[] getPromptsNoKey(String kii){
        Scanner s = new Scanner(System.in);
        System.out.println(getRandString(100));
        System.out.println("Enter your name: ");
        String namey = s.nextLine();
        System.out.println("Soup of the day?: ");
        String sdj = s.nextLine();
        System.out.println("What do you go by?: ");
        String pp = s.nextLine();
        //TODO file only/optional
        System.out.println("Do I Know You?");
        String yn = s.nextLine();
        return new String[]{namey, sdj, pp, kii, yn};
    }

    public static String getPIK() throws IOException {
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
                        yummers += helt.charAt(totales + index);
                        totales += index +1;
                    }
                    if (yummers.equals(pp)) {
                        bag = false;
                        return helt.substring(helt.length() - 2048 -1, helt.length() -1);
                    }
                }
            if(bag){
                    System.out.println("Sorry Bub, You're Not On The List");
                }

            } catch (IOException | RuntimeException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    public static String getprePIKandgen(String namey, String sdj, String pp, String kii, boolean yn) throws IOException {
        List<Path> txtFiles = null;
        try {
            txtFiles = Files.walk(Path.of(enforcrysecretpath + File.separator + "party_list"))
                    //use to string here, otherwise checking for path segments
                    .filter(p -> p.toString().contains("." + sdj)).toList();

            boolean bag = true;
            for (Path e : txtFiles) {
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
                    yummers += helt.charAt(totales + index);
                    totales += index +1;
                }
                if (yummers.equals(pp)) {
                    bag = false;
                    return helt.substring(helt.length() - 2048 -1, helt.length() -1);
                }
            }

            if(yn){
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

        } catch (IOException | RuntimeException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    public static boolean genPI(String n, String s, String p, String k) throws IOException, NoSuchAlgorithmException {
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

}

