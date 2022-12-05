package clivet268.SecureLine;

import java.io.*;
import java.util.Scanner;

import static clivet268.Enforcry.username;

//Flush everything since client also uses this
public class TextListener implements Runnable {

    DataInputStream i;
    DataOutputStream o;
    Scanner scanner;
    private static boolean kflag = false;
    String senderUsername;

    public TextListener(DataInputStream iin, DataOutputStream ion, Scanner sin, String su) {
        i = iin;
        o = ion;
        scanner = sin;
        senderUsername = su;
    }

    public void texter() throws IOException {
        while (!kflag) {
            int codein = i.readInt();
            if (codein == 14) {
                String sins = i.readUTF();
                for (char ignored : (username + ":  ").toCharArray()) {
                    System.out.print("\b");
                }
                System.out.println(senderUsername + ": " + sins);
                System.out.print(username + ": ");
            }
            if (codein == 23) {
                kflag = true;
            }
        }
    }

    @Override
    public void run(){
        try{
            Thread rn = new Thread(()-> {
                try {
                    texter();
                } catch (IOException ignored) {

                }
            });
            rn.start();
            System.out.print(username + ": ");
            while (!kflag) {
                String ts = call();
                kflag = interpretOut(ts);
            }
        } catch (IOException ignored) {
        }
    }

    //Escape character for commands such as "/exit"
    public boolean interpretOut(String tso) throws IOException {
        if(tso.length() > 0) {
            if (!(tso.toCharArray()[0] == '/')) {
                o.writeInt(14);
                o.flush();
                //TODO why does this string need to be flushed and not others?
                o.writeUTF(tso);
                o.flush();
                System.out.print(username + ": ");
            } else if (tso.equals("/exit")) {
                o.writeInt(23);
                o.flush();
                return true;
            }
        }
        return false;
    }

    //TODO possible without busy waiting?

    /**
     * credit https://www.javaspecialists.eu/archive/Issue153-Timeout-on-Console-Input.html and
     * https://stackoverflow.com/questions/4983065/how-to-interrupt-java-util-scanner-nextline-call - author djna
     * https://stackoverflow.com/users/82511/djna
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
                    if(kflag){
                        br.close();
                    }
                    Thread.sleep(100);
                }
                input = br.readLine();
            } catch (InterruptedException e) {
                return null;
            }
        } while ("".equals(input));
        return input;
    }

}
