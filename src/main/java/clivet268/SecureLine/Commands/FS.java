package clivet268.SecureLine.Commands;

import clivet268.SecureLine.Command_Sources.FS_SRC;

import java.io.IOException;
import java.util.ArrayList;

public class FS extends ExacutableCommand {

    //TODO no good
    /**
     * @return asks for the
     */
    @Override
    public ArrayList<String> commandPrompts(){
        ArrayList<String> ps = new ArrayList<>(1);
        ps.add("Path To File on Server");
        return ps;
    }

    @Override
    public void run() {
        this.output.clear();
        try {
            this.output =  FS_SRC.get(this.input.get(0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
