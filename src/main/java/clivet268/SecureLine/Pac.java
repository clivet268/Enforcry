package clivet268.SecureLine;

import java.util.Base64;

public class Pac {
    int pacLengthReal;
    byte[] pacBody;
    String pacBody64;

    public Pac(byte[] b){
        pacBody = b;
        pacBody64 = Base64.getEncoder().encodeToString(b);
        pacLengthReal=pacBody64.length();
    }
}
