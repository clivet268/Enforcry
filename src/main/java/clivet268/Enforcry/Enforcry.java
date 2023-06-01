package clivet268.Enforcry;

import clivet268.Enforcry.Encryption.Asymmetric;
import clivet268.Enforcry.Operations.*;
import clivet268.Enforcry.SecureLine.Commands.*;
import clivet268.Enforcry.Util.DebugOnlyLogger;
import clivet268.Enforcry.Util.Univ;

import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;
//TODO BIG -  implement encryption
// - make a full builder to a file with jar, and batch file
// - encrypt directory of source on drive and have an unencrypter (also in builder)
// - secure cores and memory to the tooth, lock down every permission you can, encrypt everything that makes sense or
// cant be locked down
//TODO port lockdown
//TODO core lockdown
//TODO make enforcry UNIQUE other than username, password needed and you cant mimic someone else with a key from it or something like that
// username and password can be used to generate a unique algorithm, on intial contact this can be sent if they dont know eachother, and if
// they do it can be verified
// An encrypted file could be used as verifier
//TODO make gui where keys can be entered secretly
//TODO NO PLAINTEXT KEYS BEYOND ENTRY
//TODO limit character range ie usernames shouldn't contain ":" or null characters or anything beyond a set of acceptable characters

//The objective of Enforcry is to create a secure, platform-independent, Java environment.
public class Enforcry {
    //Debug only
    public static DebugOnlyLogger logger = new DebugOnlyLogger(Logger.getLogger(Enforcry.class.getName()), false);

    public static HashMap<String, Operation> operations = new HashMap<>();
    public static HashMap<String, ExecutableCommand> SLcommands = new HashMap<>();

    public static String getUsername() {
        return username;
    }

    //TODO get at login
    private static String username = "clivet268test";

    //TODO safter :(
    //TODO refresh periodically and form on a per connection basis
    public static KeyPair sessionKeyStore;

    public static int[] allowedPorts = {26817};
    public static final Scanner s = new Scanner(System.in);


    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {


        //TODO can this cause vulernabilities?
        //TODO build kays and certificates into keystore
        //If error during keygen, kill
        try {
            sessionKeyStore = Asymmetric.generateRSAKkeyPair();
            System.out.println("Session Key generated");
        } catch (Exception e) {
            System.exit(696969);
        }
        initOperations();
        initSLcommands();
        Univ.init();
        System.out.println(operations.size());
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
        new FEKG().init();
        new FDKL().init();
        new FDKIN().init();
        new FEK().init();
        new FEKL().init();
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
