package org.karazin.kateryna;

import lombok.RequiredArgsConstructor;
import org.karazin.kateryna.strategies_logging.StrategyForLogging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class LoggerModule {
    private static LoggerModule instance;
    private final StrategyForLogging strategyForLogging;

    public static synchronized LoggerModule getInstance(StrategyForLogging strategyForLogging) {
        if (instance == null) {
            instance = new LoggerModule(strategyForLogging);
        }
        return instance;
    }

    public void log(LoggingLevels level, String message) {
        String logMessage = String.format(
                "%s %s - [%s]: %s",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                level,
                Thread.currentThread().getName(),
                message
        );
        strategyForLogging.logSomeMessage(logMessage);
    }


    public void debug(String message) {
        log(LoggingLevels.DEBUG, message);
    }

    public void info(String message) {
        log(LoggingLevels.INFO, message);
    }

    public void warning(String message) {
        log(LoggingLevels.WARNING, message);
    }

    public void error(String message) {
        log(LoggingLevels.ERROR, message);
    }

    public void critical(String message) {
        log(LoggingLevels.CRITICAL, message);
    }

}