package clivet268.SecureLine.Commands;

import java.net.Socket;
import java.util.ArrayList;

public class CLOSE extends ExacutableCommand {

    @Override
    public void run() {
        this.closeflag = false;
        this.output.add("0");
    }

    @Override
    public byte outbytecode(){
        return 0;
    }

}
