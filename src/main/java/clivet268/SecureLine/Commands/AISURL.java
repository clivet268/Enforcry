package clivet268.SecureLine.Commands;

import clivet268.SecureLine.Command_Sources.AISURL_SRC;

import java.io.IOException;
import java.util.ArrayList;

public class AISURL extends ExacutableCommand {

    @Override
    public int noinputs(){
        return 1;
    }
    @Override
    public ArrayList<String> commandPrompts(){
        ArrayList<String> ps = new ArrayList<>(1);
        ps.add("Enter url");
        return ps;
    }

    @Override
    public void run() {
        try {
            this.output = (ArrayList<Object>)AISURL_SRC.get(this.input.get(0)).clone();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int outbytecode(){
        return 2;
    }
}
