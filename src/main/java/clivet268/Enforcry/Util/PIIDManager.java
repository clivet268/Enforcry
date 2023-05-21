package clivet268.Enforcry.Util;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;

public class PIIDManager {
    ArrayList<String> PIIDs = new ArrayList<>();

    public static void main(String[] args) {
        ManagementFactory.getRuntimeMXBean().getName();
    }
}
