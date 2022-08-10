package clivet268.SecureLine.Commands;

import clivet268.Enforcry;

import java.util.ArrayList;

public abstract class ExacutableCommand implements Runnable{
    String name = this.getClass().getSimpleName();
    ArrayList<String> output = new ArrayList<>();

    boolean closeflag =true;

    public void init(){
        Enforcry.SLcommands.put(getName(), this);
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

    public ArrayList<String> getOutput(){
        return output;
    }
}
