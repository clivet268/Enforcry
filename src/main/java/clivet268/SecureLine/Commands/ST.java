package clivet268.SecureLine.Commands;

public class ST extends ExacutableCommand {

    @Override
    public void run() {
        output.add("Test");
    }
    @Override
    public int outbytecode(){
        return 1;
    }
}
