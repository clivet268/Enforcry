package clivet268.SecureLine.Commands;

import clivet268.Enforcry;

import java.util.ArrayList;

public abstract class ExacutableCommand implements Runnable{
    String name = this.getClass().getSimpleName();
    /**
     * stores after being populated by {@link #run()}
     */
    ArrayList<byte[]> output = new ArrayList<>();
    //TODO setter getter?
    public ArrayList<String> input = new ArrayList<>();

    /**
     * 0 - none
     * 1 - text
     * @return returns the output mode of this command (texting or not)
     */
    public int getTnt(){
        return 0;
    }

    /**
     * @return returns the command prompts to be sent out to the sender,by default an empty arraylist
     */
    public ArrayList<String> commandPrompts() {
        ArrayList<String> ps = new ArrayList<>();
        return ps;
    }

    /**
     * Used to control the closing of the connection after a command completes or when requested
     *
     * @return whether the connection should continue or close
     */
    public boolean closeFlag() {
        return false;
    }

    //TODO no longer SL, EFCTP

    /**
     * Populates the SLcommands array to be gotten by sender
     */
    public void init() {
        System.out.println("Initialized " + name.toLowerCase());
        Enforcry.SLcommands.put(name.toLowerCase(), this);
    }

    /**
     * Runs the function stored inside and populates the output array if there are outputs
     */
    @Override
    public void run() {
    }

    /**
     * @return returns the outputs generated by {@link #run()}
     */
    public ArrayList<byte[]> getOutput(){
        return output;
    }
}
