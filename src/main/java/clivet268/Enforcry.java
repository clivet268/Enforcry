package clivet268;

import clivet268.Encryption.Asymmetric;
import clivet268.Operations.*;
import clivet268.SecureLine.Commands.*;
import clivet268.Util.DebugOnlyLogger;
import clivet268.Util.Univ;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;

//TODO BIG -  implement encryption
// - make a full builder to a file with jar, and batch file
// - encrypt directory of source on drive and hav an unencriptor (also in builder)
// - secure cores and memory to the tooth, lock down every permission you can, encrypt everything that makes sense or
// cant be locked down
//TODO port lockdown
//TODO core lockdown
//TODO make enforcry UNIQUE other than username, password needed and you cant mimic someone else with a key from it or something like that
// username and password can be used to generate a unique algorithm, on intial contact this can be sent if they dont know eachother, and if
// they do it can be verified
// An encrypted file could be used as verifier
public class Enforcry {
    //Debug only
    //public static DebugOnlyLogger logger = new DebugOnlyLogger(Logger.getLogger(Enforcry.class.getName()), false);
    public static DebugOnlyLogger logger = new DebugOnlyLogger(Logger.getLogger(Enforcry.class.getName()), true);
    public static HashMap<String, Operation> operations = new HashMap<>();
    public static HashMap<String, ExacutableCommand> SLcommands = new HashMap<>();
    public static String stk = "";
    public static String username = "Clivet268";
    public static KeyPair sessionKey;

    public static void main(String[] args) {
        //TODO can this cause vulernabilities?
        //If error during keygen, kill
        try {
            sessionKey = Asymmetric.generateRSAKkeyPair();
            System.out.println("Session Key generated");
        } catch (Exception e) {
            System.exit(696969);
        }
        initOperations();
        initSLcommands();
        Univ univ = new Univ();
        System.out.println(operations.size());
        Scanner s = new Scanner(System.in);
        boolean b = true;
        while (b) {
            System.out.println("What would you like to do? (o for options)");
            Operation temp = operations.get(s.next().toLowerCase());
            if (temp != null) {
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
        System.out.println("\n---Init Operations---\n");

        new CEE().init();
        new DEFAULT().init();
        new ESLS().init();
        new ESLC().init();
        new FDK().init();
        new FDKIN().init();
        new FEK().init();
        new FEKIN().init();
        new MPIK().init();
        new O().init();
        new X().init();

        System.out.println("\n---Operations Init Complete---\n");
    }

    /**
     * Initializes all commands in the commands folder that extend the abstract class ExacutableCommand
     */
    public static void initSLcommands() {
        System.out.println("\n---Init Operations---\n");

        new AISURL().init();
        new CLOSE().init();
        new EPFS().init();
        new FS().init();
        new ST().init();
        new TXT().init();

        System.out.println("\n---Commands Init Complete---\n");
    }

}
