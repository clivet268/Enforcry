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


}

