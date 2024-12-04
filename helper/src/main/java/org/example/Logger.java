package org.example;

public class Logger {
    private final System.Logger logger;
    private Logger(Class<?> clazz) {
        this.logger = System.getLogger(clazz.getSimpleName());
    }
    public static Logger getLogger(Class<?> clazz) {
        return new Logger(clazz);
    }
    public void info(Object message) {
        logger.log(System.Logger.Level.INFO, message);
    }
    public void trace(Object message) {
        logger.log(System.Logger.Level.TRACE, message);
    }
}
