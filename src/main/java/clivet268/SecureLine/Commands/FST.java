package clivet268.SecureLine.Commands;

public class FST extends ExacutableCommand {

    @Override
    public void run() {
        output.add("Test".getBytes());
    }
    @Override
    public int outbytecode(){
        return 2;
    }
}
