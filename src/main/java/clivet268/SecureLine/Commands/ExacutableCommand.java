package clivet268.SecureLine.Commands;

import clivet268.Enforcry;

import java.util.ArrayList;

public abstract class ExacutableCommand implements Runnable{
    String name = this.getClass().getSimpleName();
    public ArrayList<String> output = new ArrayList<>();
    public ArrayList<String> input = new ArrayList<>();

    public int noinputs(){
        return 0;
    }

    public ArrayList<String> commandPrompts(){
            ArrayList<String> ps = new ArrayList<>();
        return  ps;
    }
    boolean closeflag =true;

    public void init(){
        Enforcry.SLcommands.put(getName().toLowerCase(), this);
    }

    public boolean getCloseflag() {
        return closeflag;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {

    }

    public byte outbytecode(){
        return -1;
    }

    public ArrayList<String> getOutput(){
        return output;
    }
}
