package clivet268.Operations;

public class X extends Operation{
    @Override
    public void run() {
        this.exitflag = false;
        System.out.println("Exiting");
        System.exit(0);
    }

    @Override
    public String infoForOp(){
        return "Exits Enforcry";
    }
}
