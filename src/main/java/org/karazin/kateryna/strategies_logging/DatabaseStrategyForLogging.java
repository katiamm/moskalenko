package org.karazin.kateryna.strategies_logging;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseStrategyForLogging implements StrategyForLogging {

    private final String jdbcUrl;
    private final String username;
    private final String password;
    private final String tableName;

    public DatabaseStrategyForLogging(String jdbcUrl, String username, String password, String tableName) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
        this.tableName = tableName;
    }

    @Override
    public void logSomeMessage(String message) {
        String sql = buildInsertSql();

        try (Connection connection = createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, message);
            preparedStatement.executeUpdate();
            System.out.println("Log saved to table " + tableName + ": " + message);

        } catch (SQLException e) {
            handleDatabaseError(e);
        }
    }

    private String buildInsertSql() {
        return "INSERT INTO " + tableName + " (message, timestamp) VALUES (?, CURRENT_TIMESTAMP)";
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    private void handleDatabaseError(SQLException e) {
        System.err.println("Error saving log to database: " + e.getMessage());
    }
}
