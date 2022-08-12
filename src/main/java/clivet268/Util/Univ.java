package clivet268.Util;

import javax.swing.*;
import java.io.File;

public class Univ {
    public static String enforceybasepath = System.getProperty("user.home") + File.separator + "Enforcry/";
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
}
