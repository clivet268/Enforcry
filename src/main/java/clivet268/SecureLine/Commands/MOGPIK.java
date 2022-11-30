package clivet268.SecureLine.Commands;

import java.util.ArrayList;

public class MOGPIK extends ExacutableCommand {
    //TODO key needs to be gotten and stored safely, this doe sneither, use usernames and secrets in future
    // benign for now, getting from file should be handled automatically, not explicitly

    @Override
    public ArrayList<String> commandPrompts(){
        ArrayList<String> ps = new ArrayList<>(1);
        ps.add("this is a test enter your butt: ");
        return ps;
    }
    @Override
    public void run() {
        this.output.clear();

    }
}
