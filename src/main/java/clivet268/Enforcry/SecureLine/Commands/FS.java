package clivet268.Enforcry.SecureLine.Commands;

import clivet268.Enforcry.SecureLine.Command_Sources.FS_SRC;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;

public class FS extends ExecutableCommand {

    //TODO no good

    /**
     * @return asks for the
     */
    @Override
    public ArrayList<String> commandPrompts() {
        ArrayList<String> ps = new ArrayList<>(1);
        ps.add("Select which root number to use: ");
        ps.add("Path To File on Server From Enforcry File Root:");
        return ps;
    }

    @Override
    public void run() {
        this.output.clear();
        try {
            this.output = FS_SRC.get(Integer.parseInt(this.input.get(0)), this.input.get(1));
        } catch (IOException e) {
            this.output.add(Pair.of(1, "Invalid input".getBytes()));
        }
    }
}
