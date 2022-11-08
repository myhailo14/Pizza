package lab.pizza.logger;

import lab.pizza.model.Pizza;

import java.io.FileWriter;
import java.io.IOException;

public class PizzasLogger {
    private static final String LOG_FILE_PATH = "C:/University/Projects/Labs/KP/Pizza/pizza/pizza/src/main/resources/log/log.txt";

    public static synchronized void logPizzaStart(final Pizza pizza) {
        writeToLogFile(String.format("Pizza %s with id %d started cooking...\n", pizza.getName(), pizza.getId()));
    }

    public static synchronized void logPizzaEnd(final Pizza pizza) {
        writeToLogFile(String.format("Pizza %s with id %d is cooked!\n", pizza.getName(), pizza.getId()));
    }

    private static synchronized void writeToLogFile(final String message) {
        try {
            FileWriter fileWriter = new FileWriter(LOG_FILE_PATH, true);
            fileWriter.write(message);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

