package clivet268.Operations;

import clivet268.Enforcry;
import org.apache.commons.text.WordUtils;

import java.awt.*;

public class O extends Operation{
    @Override
    public void run() {
        Dimension size
                = Toolkit.getDefaultToolkit().getScreenSize();

        // width will store the width of the screen
        int width = (int)size.getWidth();
        Enforcry.operations.forEach((key, value)->{
        System.out.println(key + " -\n" + WordUtils.wrap(value.infoForOp(), width/15));
        });
    }
    @Override
    public String infoForOp(){
        return "Lists these operations and their uses";
    }
}
