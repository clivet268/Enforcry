package clivet268.Enforcry.Operations;

import clivet268.Enforcry.AMOE.Amoe;

import static clivet268.Enforcry.Enforcry.s;
import static clivet268.Enforcry.Util.Univ.PORT_AMOE;

public class AMOE extends Operation {

    //TODO WHAT is causing stdin to be full with something at this point?????
    @Override
    public void run() {
        Amoe c = new Amoe();
        System.out.println("Source's IP");
        s.nextLine();
        String ip = s.nextLine();
        try {
            c.connect(ip, PORT_AMOE, -1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String infoForOp() {
        return "Sets up a process in the background logging information to show this device's \n" +
                "location through ip and relays it to the designated ip on a select interval.";
    }
}
