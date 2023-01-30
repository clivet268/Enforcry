package clivet268.SecureLine.Commands;

import clivet268.SecureLine.Command_Sources.FS_SRC;

import java.io.IOException;
import java.util.ArrayList;

//TODO password based encrypt file before sending
public class EPFS extends ExecutableCommand {

    @Override
    public ArrayList<String> commandPrompts() {
        ArrayList<String> ps = new ArrayList<>(1);
        ps.add("Who are you and who am I");
        ps.add("Path To File on Server In Enforcry Package");
        return ps;
    }

    @Override
    public void run() {
        this.output.clear();
        try {
            this.output = FS_SRC.get(Integer.parseInt(this.input.get(0)), this.input.get(1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
