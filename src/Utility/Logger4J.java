package Utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class represents a Utility Class For Logs.
 */

public class Logger4J {

    private static final Logger logger = LogManager.getLogger(Logger4J.class);
    private static Logger4J instance;

    private Logger4J() {
        // Private constructor to prevent instantiation.
    }

    public static Logger4J getInstance() {
        if (instance == null) {
            synchronized (Logger4J.class) {
                if (instance == null) {
                    instance = new Logger4J();
                }
            }
        }
        return instance;
    }

    public void debug(String message) {
        logger.debug(message);
    }

    public void info(String message) {
        logger.info(message);
    }

    public void warn(String message) {
        logger.warn(message);
    }

    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
}
