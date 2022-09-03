package clivet268.Util;

import clivet268.Enforcry;
import clivet268.FileEncryption.FileEncrypterDecrypter;
import org.apache.commons.lang3.ArrayUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static clivet268.FileEncryption.FileEncrypterDecrypter.gen2048;

public class Univ {

    public static final int BTC =  65536;
    public static String enforcrybasepath = System.getProperty("user.home") + File.separator + "Enforcry/";
    public static String enforcrytestpath = System.getProperty("user.home") + File.separator + "Enforcry/test/";
    public static String enforcrysecretpath = System.getProperty("user.home") + File.separator + "Enforcry/-/";
    public static String enforcrypartypath = enforcrysecretpath + File.separator + "party_list";
    public static String enforcryfoutpath = System.getProperty("user.home") + File.separator + "Enforcry/fout/";

    public Univ(){
        createPaths();
    }

    public static String normalStringSet = "abcedfghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789-_";
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
    public static String getRandString() {
        Random ran = new Random();
        String nam = "";
        for (int i = 0; i < i+ran.nextInt(18, 45); i++) {
            nam += ran.nextInt(9);
        }
        return nam;
    }

    public static String getRandString(int ii) {
        Random ran = new Random();
        String nam = "";
        for (int i = 0; i < ii; i++) {
            nam += normalStringSet.charAt(ran.nextInt(normalStringSet.length() - 1));
        }
        System.out.println("randed");
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

    public static void genpersonalidentifiers(String n, String s, String p) throws NoSuchAlgorithmException, IOException {
        boolean bag = true;
        while (bag) {
            int yrey = 0;
            System.out.println(yrey);
            yrey++;
            String ststs = String.valueOf(s.hashCode());
            int mdj = Integer.parseInt(ststs.substring(ststs.length() - 4, ststs.length() - 1));
            int stedj = Integer.parseInt(ststs.substring(0, 6));
            Random er = new Random();
            System.out.println(yrey);
            yrey++;
            String e = getRandString(3* stedj + er.nextInt(100000));
            System.out.println(yrey);
            yrey++;
            for (int ieee = 0; ieee < mdj; ieee++) {
                int ewrw = er.nextInt(e.length() - 1 - n.length());
                e = e.substring(0, ewrw) + n + e.substring(ewrw + n.length());
            }
            System.out.println(yrey);
            yrey++;
            String k = gen2048();
            e = e.substring(0, stedj)
                    + k
                    + e.substring(stedj,
                    stedj + k.length());
            FileEncrypterDecrypter.encrypt(e, p);
            System.out.println("Let Me Check Again");

            //Test Valid PI
            List<Path> txtFiles = Files.walk(Path.of(enforcrysecretpath + File.separator + "party_list"))
                    //use to string here, otherwise checking for path segments
                    .filter(pe -> pe.toString().endsWith("." + s)).toList();
            for (Path flies : txtFiles) {
                String str = new String(FileEncrypterDecrypter.fdktostream(new File(flies.toUri()), p), StandardCharsets.UTF_8);
                String tstr = str;
                int count;
                count = str.split(n, -1).length - 1;
                String re = String.valueOf(s.hashCode());
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

