package me.johni0702.ButtonControl;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ButtonControlLogger {

    public static final Logger log = Logger.getLogger("Minecraft");

    public static void severe(String string, Exception ex) {
        log.log(Level.SEVERE, "[ButtonControl] " + string, ex);
    }

    public static void severe(String string) {
        log.log(Level.SEVERE, "[ButtonControl] " + string);
    }

    static void info(String string) {
        log.log(Level.INFO, "[ButtonControl] " + string);
    }

    static void warning(String string) {
        log.log(Level.WARNING, "[ButtonControl] " + string);
    }
}
