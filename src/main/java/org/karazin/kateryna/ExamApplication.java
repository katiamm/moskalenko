package org.karazin.kateryna;

import org.karazin.kateryna.strategies_logging.StrategyForLogging;

import java.util.Scanner;

public class ExamApplication {

    private static final String RESOURCES_DIRECTORY = "src/main/resources/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        LoggerConfigurationProperties config = getLoggerConfiguration(scanner);

        if (config == null) {
            System.out.println("Invalid choice, exiting...");
            return;
        }

        StrategyForLogging strategyForLogging = LogStrategyFactory.createLogStrategy(config);
        LoggerModule loggerModule = LoggerModule.getInstance(strategyForLogging);

        loggerModule.info("Application started");

        logCustomMessage(scanner, loggerModule);

        System.out.println("Log message has been written to the selected log format.");
    }

    private static LoggerConfigurationProperties getLoggerConfiguration(Scanner scanner) {
        System.out.println("Select log format:");
        System.out.println("1. XML");
        System.out.println("2. Text");
        System.out.println("3. Database");

        int choice = scanner.nextInt();
        scanner.nextLine();

        LoggerConfigurationProperties config = new LoggerConfigurationProperties();

        return switch (choice) {
            case 1 -> configureXmlLog(scanner, config);
            case 2 -> configureTextLog(scanner, config);
            case 3 -> configureDatabaseLog(scanner, config);
            default -> null;
        };
    }

    private static LoggerConfigurationProperties configureXmlLog(Scanner scanner, LoggerConfigurationProperties config) {
        System.out.print("Enter file name for XML log (e.g., logs.xml): ");
        String xmlFileName = scanner.nextLine();
        config.setFormat(LogOutputFormat.XML);
        config.setFilePath(RESOURCES_DIRECTORY + xmlFileName);
        return config;
    }

    private static LoggerConfigurationProperties configureTextLog(Scanner scanner, LoggerConfigurationProperties config) {
        System.out.print("Enter file name for Text log (e.g., logs.txt): ");
        String textFileName = scanner.nextLine();
        config.setFormat(LogOutputFormat.TEXT);
        config.setFilePath(RESOURCES_DIRECTORY + textFileName);
        return config;
    }

    private static LoggerConfigurationProperties configureDatabaseLog(Scanner scanner, LoggerConfigurationProperties config) {
        System.out.print("Enter JDBC URL for PostgreSQL log (e.g., jdbc:postgresql://localhost:5432/pattern_exam) ");
        String jdbcUrl = scanner.nextLine();
        System.out.print("Enter username for Database log: ");
        String username = scanner.nextLine();
        System.out.print("Enter password for Database log: ");
        String password = scanner.nextLine();
        System.out.print("Enter table name for Database log: ");
        String tableName = scanner.nextLine();
        config.setFormat(LogOutputFormat.DATABASE);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setTableName(tableName);
        return config;
    }

    private static void logCustomMessage(Scanner scanner, LoggerModule loggerModule) {
        System.out.print("Enter a message to log: ");
        String message = scanner.nextLine();
        loggerModule.info(message);
    }
}
