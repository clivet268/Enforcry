package clivet268.SecureLine.Commands;

import java.net.Socket;

public class CLOSE extends ExacutableCommand {

    @Override
    public void run() {
        this.closeflag = false;
    }

}
