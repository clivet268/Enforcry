package clivet268;

import clivet268.Operations.Operation;
import clivet268.SecureLine.Commands.ExacutableCommand;
import clivet268.Util.Univ;
import org.reflections.Reflections;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Enforcry {

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

    public static void initOperations(){

        Reflections reflections = new Reflections("clivet268.Operations");
        Set<Class<? extends Operation>> opstoinit = new HashSet<>(reflections.getSubTypesOf(Operation.class));
        opstoinit.forEach((e) -> {
            try {
                e.getDeclaredConstructor().newInstance().init();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException ex) {
                throw new RuntimeException(ex);
            }
        });
        System.out.println("---\nOperations init complete\n---");
    }

    public static void initSLcommands(){
        Reflections reflections = new Reflections("clivet268.SecureLine.Commands");
        Set<Class<? extends ExacutableCommand>> opstoinit = new HashSet<>(reflections.getSubTypesOf(ExacutableCommand.class));
        opstoinit.forEach((e) -> {
            try {
                e.getDeclaredConstructor().newInstance().init();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

}
