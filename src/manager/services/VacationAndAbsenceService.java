package manager.services;

import manager.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VacationAndAbsenceService {

    public void requestHoliday(int employeeId, int leaveDays){
        double currentHoliday = getCurrentHolidays(employeeId);
        if (currentHoliday > 0){
            requestHolidaySQL(employeeId,leaveDays);
        }else {
            System.out.println("No holidays available");
        }
    }


    private void requestHolidaySQL(int employeeId, int leaveDays) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE employees SET currentHoliday = currentHoliday - ? WHERE id = ? AND currentHoliday >= ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, leaveDays);
                statement.setInt(2, employeeId);
                statement.setInt(3, leaveDays);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated == 0) {
                    throw new IllegalArgumentException("Not enough holidays available");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getCurrentHolidays(int employeeId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT currentHoliday FROM employees WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, employeeId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getDouble("currentHoliday");
                    } else {
                        throw new IllegalArgumentException("Employee not found");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
