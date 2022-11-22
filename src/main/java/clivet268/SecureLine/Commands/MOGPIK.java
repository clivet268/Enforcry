package clivet268.SecureLine.Commands;

import clivet268.SecureLine.EncryptedSecureLineReciver;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static clivet268.Util.Univ.PImatch;
import static clivet268.Util.Univ.genPI;

public class MOGPIK extends ExacutableCommand {

    @Override
    public int noinputs(){
        return 3;
    }
    @Override
    public ArrayList<String> commandPrompts(){
        ArrayList<String> ps = new ArrayList<>(1);
        ps.add("Enter your name: ");
        ps.add("Soup of the day?: ");
        ps.add("What do you go by?: ");
        //TODO file only/optional
        ps.add("Do I know you? ");
        ps.add("oKay");
        return ps;
    }
    @Override
    public void run() {
        String namey = input.get(0);
        String sdj = input.get(1);
        String pp = input.get(2);
        String kii = input.get(4);
        boolean check = Boolean.parseBoolean(input.get(3));
            try {
                if(PImatch(namey, sdj,pp,kii)){
                    EncryptedSecureLineReciver.tskp = kii;
                    this.closeflag = true;
                } else if (check) {
                    EncryptedSecureLineReciver.tskp = kii;
                    this.closeflag = genPI(namey, sdj, pp, kii);
                }
                else {
                    this.closeflag = false;
                }
            } catch (IOException | NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }


    }



    @Override
    public int outbytecode(){
        return -1;
    }
}
