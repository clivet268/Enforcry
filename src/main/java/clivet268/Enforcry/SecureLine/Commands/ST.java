package clivet268.Enforcry.SecureLine.Commands;


import org.apache.commons.lang3.tuple.Pair;

public class ST extends ExecutableCommand {

    @Override
    public void run() {
        this.output.clear();
        output.add(Pair.of(1, "Test".getBytes()));
        output.add(Pair.of(2, "Test".getBytes()));
    }
}
