package clivet268.Enforcry.Operations;

import clivet268.Enforcry.Enforcry;

public abstract class Operation implements Runnable{
    String name = this.getClass().getSimpleName();
    boolean exitflag = true;

    public String getName() {
        return name;
    }

    public void init(){
        System.out.println("Initialized " + name.toLowerCase());
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
