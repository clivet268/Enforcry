package clivet268.Util;

import org.apache.commons.lang3.ArrayUtils;

import javax.swing.*;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    public static String getrandname() {
        Random ran = new Random();
        String nam = "";
        for (int i = 0; i < ran.nextInt(18, 45); i++) {
            nam += ran.nextInt(9);
        }
        return nam;
    }
    public static byte[] trimTrailingZeros(byte[] bytes){
        ArrayUtils.reverse(bytes);
        int i  = 0;
        while (bytes[i] == 0){
            i++;
        }
        ArrayUtils.reverse(bytes);
        byte[] nbytes =new byte[bytes.length - 1 - i];
        for (int j = nbytes.length; j >= 0; j--) {
            nbytes[j] = bytes[i];
        }
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
