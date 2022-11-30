package clivet268.SecureLine.Commands;

public class ST extends ExacutableCommand {

    @Override
    public void run() {
        this.output.clear();
        output.add("Test".getBytes());
    }
    @Override
    public int outbytecode(){
        return 1;
    }
}
