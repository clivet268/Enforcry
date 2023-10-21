package clivet268.Enforcry.Operations;


import clivet268.Enforcry.SecureLine.ServerConnection;

public class ESLS extends Operation {
    //TODO use new simpler time setting method found in AMOE
    @Override
    public void run() {
        int e = -1;
        String ts = params[0];
        if (ts.contains("m")) {
            e = Integer.parseInt(ts.substring(ts.indexOf('m'), ts.indexOf(' '))) * 60;
            ts = ts.substring(ts.indexOf(' '));
        }
        if (ts.contains("h")) {
            e += Integer.parseInt(ts.substring(ts.indexOf('h'), ts.indexOf(' '))) * 3600;
            ts = ts.substring(ts.indexOf(' '));
        }
        if (ts.contains("d")) {
            e += Integer.parseInt(ts.substring(ts.indexOf('d'), ts.indexOf(' '))) * 86400;
            ts = ts.substring(ts.indexOf(' '));
        }
        if (e == -1) {
            System.out.println("");
            e = Integer.MAX_VALUE;
        }
        try {
            ServerConnection server = new ServerConnection();
            server.connect(26817, e);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    public static void main(String[] args) {
        ESLS slr = new ESLS();
        slr.run();
    }

}
