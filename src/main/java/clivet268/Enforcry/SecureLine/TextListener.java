package clivet268.Enforcry.SecureLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import static clivet268.Enforcry.Enforcry.getUsername;

//Flush everything since client also uses this
public class TextListener implements Runnable {

    EFCDataInputStream i;
    EFCDataOutputStream o;
    Scanner scanner;
    private static boolean kflag = false;
    String senderUsername;

    public TextListener(EFCDataInputStream iin, EFCDataOutputStream ion, Scanner sin, String su) {
        i = iin;
        o = ion;
        scanner = sin;
        senderUsername = su;
    }

    public void texter(Thread tokill) throws Exception {
        while (!kflag) {
            int codein = i.readIntE();
            if (codein == 14) {
                String sins = i.readUTFE();
                for (char ignored : (getUsername() + ":  ").toCharArray()) {
                    System.out.print("\b");
                }
                System.out.println(senderUsername + ": " + sins);
                System.out.print(getUsername() + ": ");
            }
            if (codein == 35) {
                tokill.interrupt();
                kflag = true;
            }
        }
        tokill.interrupt();
    }

    @Override
    public void run(){
        try{
            Thread rn = new Thread(()-> {
                try {
                    while (!kflag) {
                        String ts = call();
                        kflag = interpretOut(ts);
                    }
                } catch (Exception ignored) {

                }
            });
            rn.start();
            System.out.print(getUsername() + ": ");
            texter(rn);
            rn.interrupt();
        } catch (Exception ignored) {
        }
    }

    //Escape character for commands such as "/exit"
    public boolean interpretOut(String tso) throws Exception {
        if (tso.length() > 0) {
            if (!(tso.toCharArray()[0] == '/')) {
                o.writeIntE(14);
                o.writeUTFE(tso);
                System.out.print(getUsername() + ": ");
            } else if (tso.equals("/exit")) {
                o.writeIntE(35);
                return true;
            }
        }
        return false;
    }

    //TODO possible without busy waiting?
    /**
     * credit <a href="https://www.javaspecialists.eu/archive/Issue153-Timeout-on-Console-Input.html">...</a> and
     * https://stackoverflow.com/questions/4983065/how-to-interrupt-java-util-scanner-nextline-call - author djna
     * <a href="https://stackoverflow.com/users/82511/djna">...</a>
     *
     * @return
     * @throws IOException
     */
    public String call() throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));
        String input;
        do {
            try {
                // wait until we have data to complete a readLine()
                while (!br.ready()) {
                    if (kflag) {
                        br.close();
                    }
                    Thread.sleep(100);
                }
                input = br.readLine();
            } catch (InterruptedException e) {
                return null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } while ("".equals(input));
        return input;
    }

}
