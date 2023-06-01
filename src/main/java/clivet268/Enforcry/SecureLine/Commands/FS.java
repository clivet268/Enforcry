package clivet268.Enforcry.SecureLine.Commands;

import clivet268.Enforcry.SecureLine.Command_Sources.FS_SRC;

import java.util.ArrayList;

public class FS extends ExecutableCommand {

    //TODO no good

    /**
     * @return asks for the
     */
    @Override
    public ArrayList<String> commandPrompts() {
        ArrayList<String> ps = new ArrayList<>(1);
        ps.add("Path To File From Local User File Root:");
        return ps;
    }

    @Override
    public void run() {
        this.output.clear();
        this.output = FS_SRC.get(this.input.get(1));
    }
}
