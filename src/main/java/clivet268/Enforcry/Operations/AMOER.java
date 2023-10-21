package clivet268.Enforcry.Operations;

import clivet268.Enforcry.AMOE.Amoer;
import clivet268.Enforcry.SecureLine.BucketServerConnection;

public class AMOER extends Operation {
    @Override
    public void run() {
        BucketServerConnection b = new BucketServerConnection();
        b.bucketConnect(28770, new Amoer());
    }

    @Override
    public String infoForOp() {
        return "Sets up a process in the background logging information to show this device's \n" +
                "location through ip and relays it to the designated ip on a select interval.";
    }
}
