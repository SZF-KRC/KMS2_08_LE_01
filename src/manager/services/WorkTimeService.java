package manager.services;

import manager.database.DatabaseConnection;
import manager.interfaces.IWorkTimeService;
import manager.model.WorkingTime;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class WorkTimeService implements IWorkTimeService {
    private List<WorkingTime> workLogs = new ArrayList<>();

    public WorkTimeService() {
        loadWorkLogsFromDatabase();
    }

    private void loadWorkLogsFromDatabase() {
        workLogs.clear(); // Clear the existing workLogs list to avoid duplicates
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM workLogs";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    WorkingTime workLog = new WorkingTime();
                    workLog.setEmployeeId(resultSet.getInt("employeeId"));
                    workLog.setStartWorkTime(resultSet.getTimestamp("startWorkTime"));
                    workLog.setEndWorkTime(resultSet.getTimestamp("endWorkTime"));
                    workLog.setStartBreakTime(resultSet.getTimestamp("startBreakTime"));
                    workLog.setEndBreakTime(resultSet.getTimestamp("endBreakTime"));
                    workLogs.add(workLog);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void checkIn(int employeeId) {
        Timestamp currentTime = new Timestamp(new Date().getTime());
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO workLogs (employeeId, startWorkTime) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, employeeId);
                statement.setTimestamp(2, currentTime);
                statement.executeUpdate();
            }

            WorkingTime workingTime = new WorkingTime();
            workingTime.setEmployeeId(employeeId);
            workingTime.setStartWorkTime(currentTime);
            workLogs.add(workingTime);
            System.out.println(currentTime);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void checkOut(int employeeId) {
        Timestamp currentTime = new Timestamp(new Date().getTime());
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE workLogs SET endWorkTime = ? WHERE employeeId = ? AND endWorkTime IS NULL";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setTimestamp(1, currentTime);
                statement.setInt(2, employeeId);
                statement.executeUpdate();
            }

            // Update the corresponding entry in the workLogs list
            for (WorkingTime workLog : workLogs) {
                if (workLog.getEmployeeId() == employeeId && workLog.getEndWorkTime() == null) {
                    workLog.setEndWorkTime(currentTime);
                    break;
                }
            }

            System.out.println(currentTime);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void startBreak(int employeeId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            Timestamp currentTime = new Timestamp(new Date().getTime());
            String query = "UPDATE workLogs SET startBreakTime = ? WHERE employeeId = ? AND endWorkTime IS NULL";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setTimestamp(1, currentTime);
                statement.setInt(2, employeeId);
                statement.executeUpdate();
            }

            // Update the corresponding entry in the workLogs list
            for (WorkingTime workLog : workLogs) {
                if (workLog.getEmployeeId() == employeeId && workLog.getEndWorkTime() == null) {
                    workLog.setStartBreakTime(currentTime);
                    break;
                }
            }

            System.out.println(currentTime);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void endBreak(int employeeId) {
        Timestamp currentTime = new Timestamp(new Date().getTime());
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE workLogs SET endBreakTime = ? WHERE employeeId = ? AND endWorkTime IS NULL";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setTimestamp(1, currentTime);
                statement.setInt(2, employeeId);
                statement.executeUpdate();
            }

            // Update the corresponding entry in the workLogs list
            for (WorkingTime workLog : workLogs) {
                if (workLog.getEmployeeId() == employeeId && workLog.getEndWorkTime() == null) {
                    workLog.setEndBreakTime(currentTime);
                    break;
                }
            }

            System.out.println(currentTime);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<WorkingTime> getWorkLogs() {
        return workLogs;
    }
    @Override
    public void listCurrentlyWorkingEmployees() {
        loadWorkLogsFromDatabase(); // Ensure the latest data is loaded from the database

        List<WorkingTime> currentlyWorking = workLogs.stream()
                .filter(log -> log.getStartWorkTime() != null && log.getEndWorkTime() == null)
                .collect(Collectors.toList());
        currentlyWorking.forEach(log -> System.out.println("Employee ID: " + log.getEmployeeId()));
    }
    @Override
    public void listCurrentlyOnBreakEmployees() {
        loadWorkLogsFromDatabase(); // Ensure the latest data is loaded from the database

        List<WorkingTime> currentlyOnBreak = workLogs.stream()
                .filter(log -> log.getStartBreakTime() != null && log.getEndBreakTime() == null && log.getEndWorkTime() == null)
                .collect(Collectors.toList());
        currentlyOnBreak.forEach(log -> System.out.println("Employee ID: " + log.getEmployeeId()));
    }
}
