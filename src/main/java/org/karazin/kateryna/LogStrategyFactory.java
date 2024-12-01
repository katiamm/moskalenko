package org.karazin.kateryna;

import org.karazin.kateryna.strategies_logging.*;

public class LogStrategyFactory {

    public static StrategyForLogging createLogStrategy(LoggerConfigurationProperties config) {
        String format = config.getFormat();
        if (format == null || format.isBlank()) {
            throw new IllegalArgumentException("Log format must not be null or empty");
        }

        return switch (format.trim().toLowerCase()) {
            case "xml" -> createXmlLogStrategy(config);
            case "text" -> createTextLogStrategy(config);
            case "database" -> createDatabaseLogStrategy(config);
            default -> throw new IllegalArgumentException("Unsupported log format: " + format);
        };
    }

    private static StrategyForLogging createXmlLogStrategy(LoggerConfigurationProperties config) {
        return new XmlFileStrategyForLogging(validateFilePath(config.getFilePath()));
    }

    private static StrategyForLogging createTextLogStrategy(LoggerConfigurationProperties config) {
        return new TextFileStrategyForLogging(validateFilePath(config.getFilePath()));
    }

    private static StrategyForLogging createDatabaseLogStrategy(LoggerConfigurationProperties config) {
        return new DatabaseStrategyForLogging(
                validateNotNull(config.getJdbcUrl(), "JDBC URL must not be null"),
                validateNotNull(config.getUsername(), "Database username must not be null"),
                validateNotNull(config.getPassword(), "Database password must not be null"),
                validateNotNull(config.getTableName(), "Database table name must not be null")
        );
    }

    private static String validateFilePath(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("File path must not be null or empty");
        }
        return filePath;
    }

    private static String validateNotNull(String value, String errorMessage) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(errorMessage);
        }
        return value;
    }

}
