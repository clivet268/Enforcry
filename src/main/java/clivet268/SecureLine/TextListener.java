package clivet268.SecureLine;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;

import static clivet268.Enforcry.logger;

public class TextListener implements Runnable{

    DataInputStream i;
    DataOutputStream o;
    Scanner scanner;
    boolean kflag = false;

    public TextListener(DataInputStream iin, DataOutputStream ion, Scanner sin) {
        i=iin;
        o=ion;
        scanner = sin;
    }

    public void texter() throws IOException {
        logger.log(Level.INFO, "tr4yht4h54h4h454h");
        while (!kflag){
            int codein = i.readInt();
            logger.log(Level.INFO, "9iu0iouihji0oujhb");
            if (codein == 14) {
                String sins = i.readUTF();
                for(char ignored : (username + ":  ").toCharArray()){
                    System.out.print("\b");
                }
                System.out.println(senderUsername + ": " + sins);
                System.out.print(username + ": ");
            }
            if (codein == 23) {
                logger.log(Level.INFO, "56ruhruoh");
                kflag = true;
            }
        }

    }

    @Override
    public void run(){
        try{
            Thread rn = new Thread(()-> {
                try {
                    logger.log(Level.INFO, "inited87656457689");
                    texter();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            logger.log(Level.INFO, "4r4r24444");
            rn.start();
            while (!kflag){
                logger.log(Level.INFO, "u227828723873287g7372");
                o.writeInt(14);
                o.flush();
                String ts = scanner.nextLine();
                logger.log(Level.INFO, "u9843h43843th943th98");
                o.writeUTF(ts);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.log(Level.INFO, "fef3jhkj232323232");
        synchronized (EFCTP.lock){
            EFCTP.lock.notify();
        }
    }
}
