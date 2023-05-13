package clivet268.Util;

import java.util.MissingResourceException;
import java.util.logging.Logger;

public class DebugOnlyLogger {

    private static boolean of;
    private static Logger l;

    /**
     * @param lin   - The pass-through for the original system logger
     * @param onoff - True means the logger is on, false means the logger is off
     * @throws MissingResourceException if the resourceBundleName is non-null and
     *                                  no corresponding resource can be found.
     */
    public DebugOnlyLogger(Logger lin, boolean onoff) {
        of = onoff;
        l = lin;
    }

    /**
     * Log a message, with no arguments.
     * <p>
     * If the logger is currently enabled for the given message
     * level then the given message is forwarded to all the
     * registered output Handler objects.
     *
     * @param msg The string message (or a key in the message catalog)
     */

    public void log(String msg) {
        if (!of) {
            return;
        }
        System.out.println(msg);
        ;
    }
}
