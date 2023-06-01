package clivet268.Enforcry.SecureLine.Commands;

import clivet268.Enforcry.SecureLine.Command_Sources.AISURL_SRC;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;

public class AISURL extends ExecutableCommand {

    @Override
    public ArrayList<String> commandPrompts() {
        ArrayList<String> ps = new ArrayList<>(1);
        ps.add("Enter url");
        return ps;
    }

    @Override
    public void run() {
        this.output.clear();
        try {
            this.output = AISURL_SRC.get(this.input.get(0));
        } catch (IOException e) {
            String ee = e.getLocalizedMessage();
            System.out.println(ee);
            this.output.add(Pair.of(1, ee));
        }
    }

}
