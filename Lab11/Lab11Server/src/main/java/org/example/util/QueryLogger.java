package org.example.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class QueryLogger {
    private static final Logger LOGGER = Logger.getLogger("JPQLLogger");

    static {
        try {
            FileHandler fileHandler = new FileHandler("jpql_execution.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);
        } catch (IOException e) {
            System.err.println("Failed to initialize logger file handler.");
        }
    }

    public static void logExecutionTime(String queryName, long startTime) {
        long duration = System.currentTimeMillis() - startTime;
        String message = "Execution time for [" + queryName + "]: " + duration + " ms";
        LOGGER.info(message);
    }

    public static void logException(String queryName, Exception e) {
        LOGGER.log(Level.SEVERE, "Exception in query [" + queryName + "]: " + e.getMessage(), e);
    }
}