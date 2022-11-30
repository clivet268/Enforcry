package clivet268.SecureLine.Commands;

public class CLOSE extends ExacutableCommand {

    @Override
    public void run() {
        this.closeflag = false;
        this.output.clear();
        this.output.add("CLOSEd".getBytes());
    }

    @Override
    public int outbytecode(){
        return 1;
    }

}
