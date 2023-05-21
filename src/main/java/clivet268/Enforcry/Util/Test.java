package clivet268.Enforcry.Util;

import org.jetbrains.annotations.TestOnly;

public class Test {
    @TestOnly
    public void sameString(String strin, String s) {
        if (strin.equals(s)) {
            System.out.println("String Equal");
        } else {
            for (int i = 0; i < strin.length() - 1; i++) {
                try {
                    s.charAt(i);
                } catch (IndexOutOfBoundsException ignored) {
                    System.out.println("Test string is longer than testie");
                    System.out.println("Test String: ");
                    System.out.println(strin.substring(i));
                }
                if (strin.charAt(i) != s.charAt(i)) {
                    System.out.println("Discrepency at char place: " + i);
                    System.out.println("Test String: ");
                    System.out.println(strin.substring(i));
                    System.out.println("Testie String:");
                    System.out.println(s.substring(i));
                }
            }
            if (!s.substring(strin.length() - 1, strin.length()).equals("")) {
                System.out.println("Testie String is longer than test string");
                System.out.println("Testie String:");
                System.out.println(s.substring(strin.length()));
            }
        }
    }
}
