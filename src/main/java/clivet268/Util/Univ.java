package clivet268.Util;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Univ {

    public static ArrayList<String> ndaryDirs = new ArrayList<>();
    public static String ENFORCRYBASEPATH = File.separator + "Enforcry" + File.separator;
    public static String ENFORCRYFILESPATH = ENFORCRYBASEPATH + "Files" + File.separator;
    public static String ENFORCRYTESTPATH = ENFORCRYBASEPATH + "test" + File.separator;
    public static String ENFORCRYSECRETPATH = ENFORCRYBASEPATH + "-" + File.separator;
    public static String ENFORCRYPARTYPATH = ENFORCRYSECRETPATH + "sl" + File.separator + "party_list" + File.separator;
    public static String ENFORCRYFOUTPATH = ENFORCRYBASEPATH + "fout" + File.separator;

    public Univ() {
        createPaths();
    }

    public static String normalStringSet = "abcedfghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789-_";
    public static char[] charset = normalStringSet.toCharArray();
    public static String upperStringSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static void createPaths() {
        new File(ENFORCRYFILESPATH).mkdirs();
        new File(ENFORCRYFOUTPATH).mkdirs();
        new File(ENFORCRYTESTPATH).mkdirs();
        new File(ENFORCRYSECRETPATH).mkdirs();
        new File(ENFORCRYPARTYPATH).mkdirs();
    }

    public static File filechooser() {
        File file = null;
        JFrame myFrame = new JFrame("Frame Title");
        myFrame.setVisible(true);
        myFrame.requestFocus();

        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fc.setCurrentDirectory(new File(System.getProperty("user.home")));
        fc.updateUI();
        fc.showOpenDialog(myFrame);
        myFrame.requestFocus();
        if (!fc.isVisible()) {
            fc.setVisible(true);
            fc.requestFocus();
        }
        file = fc.getSelectedFile();
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

    public static boolean genPI(String n, String s, String p, String k) throws IOException, NoSuchAlgorithmException {
        String hal = PIoutFormula(n,s,k);

        Path tf2 = Path.of(ENFORCRYPARTYPATH + File.separator + Univ.getrandname() + "." + p);
        Files.createFile(tf2);
        Files.write(tf2, hal.getBytes());
        return true;
    }

    public static boolean PImatch(String n, String s, String p, String k) throws IOException {

        List<Path> txtFiles = null;
        txtFiles = Files.walk(Path.of(ENFORCRYPARTYPATH))
                //use to string here, otherwise checking for path segments
                .filter(pp -> pp.toString().contains("." + p)).toList();
        boolean bag = false;
        for(Path sse : txtFiles){
            String fc = Files.readAllLines(sse).get(0);
            if (PIinTF(n,s,k,fc)){
                return true;
            }
        }
        return false;
    }

    public static boolean PIKget(String n, String s, String p, String k) throws IOException {

        List<Path> txtFiles = null;
        txtFiles = Files.walk(Path.of(ENFORCRYPARTYPATH))
                //use to string here, otherwise checking for path segments
                .filter(pp -> pp.toString().contains("." + p)).toList();
        boolean bag = false;
        for(Path sse : txtFiles){
            String fc = Files.readAllLines(sse).get(0);
            if (PIinTF(n,s,k,fc)){
                return true;
            }
        }
        return false;
    }

    public static boolean PIinTF(String n, String s, String k, String fc){
        String check = "";
        char[] chrses = fc.toCharArray();
        char[] cherr = n.toCharArray();
        char[] cher = s.toCharArray();
        for (int i = 0; i < chrses.length; i++) {
            check += ((char)(chrses[i] - cher[i%cher.length] - cherr[i%cherr.length]));
        }
        System.out.println(check);
        return check.equals(k);
    }

    public static String PIoutFormula(String n, String s, String k){
        String out = "";
        char[] cherr = n.toCharArray();
        char[] cher = s.toCharArray();
        //char[] chr = p.toCharArray();
        char[] cr = k.toCharArray();
        for (int i = 0; i < cr.length; i++) {
            out += ((char)(cr[i] +1)); //cher[i%cher.length]/2 + cherr[i%cherr.length]/2));
        }
        System.out.println("out");
        return out;
    }

    public static void incrext(String toblink) throws InterruptedException {
        int i = 0;
        for (; i < toblink.length(); i++) {
            System.out.print(toblink.charAt(i));
            //TODO :(((
            Thread.sleep(100);
        }
        for (; i > 0; i--) {
            System.out.print("\b");
            //TODO :(((
            Thread.sleep(100);
        }

    }

}

