package clivet268.Operations;

public class DEFAULT extends Operation{
    @Override
    public void run() {
        System.out.println("Invalid option (x to exit)");
        System.out.println("What would you like to do?");
    }

    @Override
    public String infoForOp(){
        return "\b\b\b\b\b\b\b\b\b\b";
    }
}
