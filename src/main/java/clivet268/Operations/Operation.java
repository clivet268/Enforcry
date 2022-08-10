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
        Enforcry.operations.put(getName(), this);
    }

    public boolean getExitflag() {
        return exitflag;
    }

    @Override
    public void run() {

    }
}
