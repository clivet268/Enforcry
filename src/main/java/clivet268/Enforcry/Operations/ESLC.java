package clivet268.Enforcry.Operations;

import clivet268.Enforcry.SecureLine.ClientConnection;

public class ESLC extends Operation {
    @Override
    public void run() {
        //TODO test only
        for (String e : params) {
            System.out.println(e);
        }
        if (params.length < 2) {
            tooFewParams();
        } else {
            int e = Integer.MAX_VALUE;
            if (params.length >= 4) {
                String ts = params[3];
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
                    //ts = ts.substring(ts.indexOf(' '));
                }
            }
            String ipad = params[1];
            int pnum = params.length == 2 ? 26817 : (Integer.parseInt(params[2]) == 0 ? 26817 : Integer.parseInt(params[2]));
            try {
                ClientConnection client = new ClientConnection();
                client.connect(ipad, pnum, e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public static void main(String[] args) {
        ESLC slr = new ESLC();
        slr.run();
    }

}
