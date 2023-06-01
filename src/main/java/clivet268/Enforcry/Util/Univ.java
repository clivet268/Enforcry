package clivet268.Enforcry.Util;

import clivet268.Enforcry.Enforcry;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Univ {

    public static ArrayList<String> ndaryDirs = new ArrayList<>();
    public static String ENFORCRYBASEPATH = File.separator + "Enforcry" + File.separator;
    public static String USERBASEPATH = ENFORCRYBASEPATH + Enforcry.getUsername() + File.separator;
    public static String USERFILESPATH = USERBASEPATH + "Files" + File.separator;
    public static String USERAMOEPATH = USERBASEPATH + "AMOE" + File.separator;
    public static String USERAMOEPUTIL = USERAMOEPATH + "util" + File.separator;
    public static String USERTEMPPATH = USERBASEPATH + "temp" + File.separator;
    public static String ENFORCRYTESTPATH = ENFORCRYBASEPATH + "test" + File.separator;
    public static String USERSECRETPATH = USERBASEPATH + "-" + File.separator;
    public static String USERPARTYPATH = USERSECRETPATH + "sl" + File.separator + "party_list" + File.separator;
    public static String USERFOUTPATH = USERBASEPATH + "fout" + File.separator;
    public static String LENGTHVERIFIER = "len:|=//\\\\";

    public static void init() {
        System.out.println("\n---Init System Paths---\n");
        createPaths();
        System.out.println("\n---System Paths Init Complete---\n");
    }

    public static String normalStringSet = "abcedfghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789-_";
    public static char[] charset = normalStringSet.toCharArray();
    public static String upperStringSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static void createPaths() {
        new File(ENFORCRYBASEPATH).mkdirs();
        new File(USERPARTYPATH).mkdirs();
        new File(USERFOUTPATH).mkdirs();
        new File(ENFORCRYTESTPATH).mkdirs();
        new File(USERFILESPATH).mkdirs();
        new File(USERSECRETPATH).mkdirs();
        new File(USERBASEPATH).mkdirs();
        new File(USERAMOEPATH).mkdirs();
        new File(USERTEMPPATH).mkdirs();
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
}

