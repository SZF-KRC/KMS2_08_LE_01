package manager.control;

import manager.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataControl {

    public boolean isEmployeeCheckedOut(int employeeId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM workLogs WHERE employeeId = ? AND endWorkTime IS NOT NULL";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, employeeId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next(); // Return true if there is at least one entry with endWorkTime not null
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isEmployeeOnBreak(int employeeId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM workLogs WHERE employeeId = ? AND startBreakTime IS NOT NULL AND endBreakTime IS NULL";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, employeeId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next(); // Return true if there is an entry with startBreakTime not null and endBreakTime null
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
