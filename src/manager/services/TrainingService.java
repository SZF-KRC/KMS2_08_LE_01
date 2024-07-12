package manager.services;

import manager.database.DatabaseConnection;
import manager.model.FullTimeEmployee;
import manager.model.Training;
import manager.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrainingService {

    public void addTraining(Training training) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Trainings (trainingName, startDate, endDate) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, training.getTrainingName());
                statement.setDate(2, new java.sql.Date(training.getStartDate().getTime()));
                statement.setDate(3, new java.sql.Date(training.getEndDate().getTime()));
                statement.executeUpdate();
            }
            String selectQuery = "SELECT LAST_INSERT_ID() AS last_id";
            try (Statement selectStatement = connection.createStatement();
                 ResultSet resultSet = selectStatement.executeQuery(selectQuery)) {
                if (resultSet.next()) {
                    int newId = resultSet.getInt("last_id");
                    training.setTrainingId(newId);
                } else {
                    throw new SQLException("Failed to retrieve the new employee ID.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Training> getAllTrainings() {
        List<Training> trainings = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Trainings";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    Training training = new Training();
                    training.setTrainingId(resultSet.getInt("id"));
                    training.setTrainingName(resultSet.getString("trainingName"));
                    training.setStartDate(resultSet.getDate("startDate"));
                    training.setEndDate(resultSet.getDate("endDate"));
                    trainings.add(training);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainings;
    }

    public void updateTraining(int trainingId, Training training) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE Trainings SET trainingName = ?, startDate = ?, endDate = ? WHERE Id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, training.getTrainingName());
                statement.setDate(2, new java.sql.Date(training.getStartDate().getTime()));
                statement.setDate(3, new java.sql.Date(training.getEndDate().getTime()));
                statement.setInt(4, trainingId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTraining(int trainingId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM Trainings WHERE Id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, trainingId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addEmployeeToTraining(int employeeId, int trainingId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO EmployeeTraining (employeeId, trainingId) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, employeeId);
                statement.setInt(2, trainingId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeEmployeeFromTraining(int employeeId, int trainingId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM EmployeeTraining WHERE employeeId = ? AND trainingId = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, employeeId);
                statement.setInt(2, trainingId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Employee> getEmployeesForTraining(int trainingId) {
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT e.*, hourlyRate FROM Employees e "
                    + "INNER JOIN EmployeeTraining et ON e.id = et.employeeId "
                    + "WHERE et.trainingId = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, trainingId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String surname = resultSet.getString("surname");
                        int age = resultSet.getInt("age");
                        String phone = resultSet.getString("phone");
                        Date startDate = resultSet.getDate("startDate");
                        double currentHoliday = resultSet.getDouble("currentHoliday");

                        Employee employee;
                        if (resultSet.getObject("hourlyRate") != null) {
                            double hourlyRate = resultSet.getDouble("hourlyRate");
                            FullTimeEmployee fullTimeEmployee = new FullTimeEmployee();
                            fullTimeEmployee.setId(id);
                            fullTimeEmployee.setName(name);
                            fullTimeEmployee.setSurname(surname);
                            fullTimeEmployee.setAge(age);
                            fullTimeEmployee.setPhone(phone);
                            fullTimeEmployee.setStartDate(startDate);
                            fullTimeEmployee.setCurrentHoliday(currentHoliday);
                            fullTimeEmployee.setHourlyRate(hourlyRate);
                            employee = fullTimeEmployee;
                        } else {
                            throw new IllegalArgumentException("Unsupported employee type");
                        }
                        employees.add(employee);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}
