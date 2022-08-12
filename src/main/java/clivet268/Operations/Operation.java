package clivet268.Operations;

import clivet268.Enforcry;

import java.util.ArrayList;

public abstract class Operation implements Runnable{
    String name = this.getClass().getSimpleName();
    boolean exitflag = true;

    public String getName() {
        return name;
    }

    public void init(){
        System.out.println(name);
        Enforcry.operations.put(getName().toLowerCase(), this);
    }

    public boolean getExitflag() {
        return exitflag;
    }

    public String infoForOp(){
        return "Base operation class";
    }


    @Override
    public void run() {

    }
}
