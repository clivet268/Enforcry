package clivet268.SecureLine.Commands;

import clivet268.SecureLine.Command_Sources.FS_SRC;

import java.io.IOException;
import java.util.ArrayList;

public class EPFS extends ExacutableCommand {

    @Override
    public int noinputs(){
        return 1;
    }
    @Override
    public ArrayList<String> commandPrompts(){
        ArrayList<String> ps = new ArrayList<>(1);
        ps.add("Who are you and who am I");
        ps.add("Path To File on Server In Enforcry Package");
        return ps;
    }

    @Override
    public void run() {
        try {
            this.output = (ArrayList<Object>) FS_SRC.get(this.input.get(0)).clone();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int outbytecode(){
        return 2;
    }
}
