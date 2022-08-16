package clivet268.Util;

import javax.swing.*;
import java.io.File;
import java.util.Random;

public class Univ {
    public static String enforcrybasepath = System.getProperty("user.home") + File.separator + "Enforcry/";
    public static String enforcrytestpath = System.getProperty("user.home") + File.separator + "Enforcry/test/";
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

    public static String getrandname(){
        Random ran = new Random();
        String nam = "";
        for(int i = 0; i < ran.nextInt(18, 45); i++) {
            nam += ran.nextInt(9);
        }
        return nam;
    }
}
