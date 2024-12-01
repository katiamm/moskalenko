package org.karazin.kateryna.strategies_logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class XmlFileStrategyForLogging implements StrategyForLogging {

    private final String filePath;
    private static final String LOG_MESSAGE_TEMPLATE = """
        <log>
            <message>
                %s
            </message>
        </log>
        """;

    public XmlFileStrategyForLogging(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void logSomeMessage(String message) {
        ensureFileExists();

        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(formatLogMessage(message));
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    private void ensureFileExists() {
        File file = new File(filePath);

        if (file.exists()) {
            return;
        }

        try {
            if (file.createNewFile()) {
                System.out.println("New log file created: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create log file: " + filePath, e);
        }
    }

    private String formatLogMessage(String message) {
        return String.format(LOG_MESSAGE_TEMPLATE, message);
    }
}