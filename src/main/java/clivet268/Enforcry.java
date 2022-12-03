package clivet268;

import clivet268.Operations.*;
import clivet268.SecureLine.Commands.*;
import clivet268.Util.Univ;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;

public class Enforcry {
    public static Logger logger = Logger.getLogger(Enforcry.class.getName());
    //TODO make encryption more like SSL or something, a little more polised pls
    //TODO use a username instead of all this PID buulsheit
    public static HashMap<String,Operation> operations = new HashMap<>();
    public static HashMap<String,ExacutableCommand> SLcommands = new HashMap<>();
    public static String stk = "";
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        initOperations();
        initSLcommands();
        Univ univ = new Univ();
        System.out.println(operations.size());
        Scanner s = new Scanner(System.in);
        boolean b = true;
        while(b) {
            System.out.println("What would you like to do? (o for options)");
            Operation temp = operations.get(s.next().toLowerCase());
            if(temp != null) {
                temp.run();
                b = temp.getExitflag();
            }
        }
        System.out.println("Exiting");
        System.exit(0);
    }

    //TODO better way without reflections?
    /**
     * Initializes all operations in the operations folder that extend the abstract class Operation
     */
    public static void initOperations(){
        new CEE().init();
        new DEFAULT().init();
        new ESLR().init();
        new ESLS().init();
        new FDK().init();
        new FDKIN().init();
        new FEK().init();
        new FEKIN().init();
        new MPIK().init();
        new O().init();
        new X().init();

        System.out.println("---\nOperations init complete\n---");
    }
    /**
     * Initializes all commands in the commands folder that extend the abstract class ExacutableCommand
     */
    public static void initSLcommands(){
        new AISURL().init();
        new CLOSE().init();
        new EPFS().init();
        new FS().init();
        new MOGPIK().init();
        new ST().init();
        new TXT().init();
        System.out.println("---\nOperations init complete\n---");
    }

}
