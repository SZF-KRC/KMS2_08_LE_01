package manager.services;

import manager.database.DatabaseConnection;
import manager.interfaces.IEmployeeService;
import manager.model.FullTimeEmployee;
import manager.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService implements IEmployeeService {
    private List<Employee> employees;


    @Override
    public void addEmployee(Employee employee) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO employees (name, surname, age, phone, startDate, hourlyRate, currentHoliday) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, employee.getName());
                statement.setString(2, employee.getSurname());
                statement.setInt(3, employee.getAge());
                statement.setString(4, employee.getPhone());
                statement.setDate(5, new java.sql.Date(employee.getStartDate().getTime()));
                statement.setDouble(6, ((FullTimeEmployee) employee).getHourlyRate());
                statement.setDouble(7, employee.getCurrentHoliday());
                statement.executeUpdate();

                String selectQuery = "SELECT LAST_INSERT_ID() AS last_id";
                try (Statement selectStatement = connection.createStatement();
                     ResultSet resultSet = selectStatement.executeQuery(selectQuery)) {
                    if (resultSet.next()) {
                        int newId = resultSet.getInt("last_id");
                        employee.setId(newId);
                    } else {
                        throw new SQLException("Failed to retrieve the new employee ID.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEmployee(int employeeId, Employee updatedEmployee) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE employees SET name = ?, surname = ?, age = ?, phone = ?, startDate = ?, hourlyRate = ?, currentHoliday = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, updatedEmployee.getName());
                statement.setString(2, updatedEmployee.getSurname());
                statement.setInt(3, updatedEmployee.getAge());
                statement.setString(4, updatedEmployee.getPhone());
                statement.setDate(5, new java.sql.Date(updatedEmployee.getStartDate().getTime()));
                statement.setDouble(6, ((FullTimeEmployee) updatedEmployee).getHourlyRate());
                statement.setDouble(7, updatedEmployee.getCurrentHoliday());
                statement.setInt(8, employeeId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteEmployee(int employeeId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Najskôr vymažeme všetky súvisiace záznamy v tabuľke worklogs
            String deleteWorklogsQuery = "DELETE FROM worklogs WHERE employeeId = ?";
            try (PreparedStatement worklogStatement = connection.prepareStatement(deleteWorklogsQuery)) {
                worklogStatement.setInt(1, employeeId);
                worklogStatement.executeUpdate();
            }

            // Potom vymažeme všetky súvisiace záznamy v tabuľke payrolls
            String deletePayrollsQuery = "DELETE FROM payrolls WHERE employeeId = ?";
            try (PreparedStatement payrollStatement = connection.prepareStatement(deletePayrollsQuery)) {
                payrollStatement.setInt(1, employeeId);
                payrollStatement.executeUpdate();
            }

            // Nakoniec vymažeme samotného zamestnanca
            String deleteEmployeeQuery = "DELETE FROM employees WHERE id = ?";
            try (PreparedStatement employeeStatement = connection.prepareStatement(deleteEmployeeQuery)) {
                employeeStatement.setInt(1, employeeId);
                employeeStatement.executeUpdate();
            }
            System.out.println("Employee deleted with ID: " + employeeId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        employees = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM employees";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    FullTimeEmployee employee = new FullTimeEmployee();
                    employee.setId(resultSet.getInt("id"));
                    employee.setName(resultSet.getString("name"));
                    employee.setSurname(resultSet.getString("surname"));
                    employee.setAge(resultSet.getInt("age"));
                    employee.setPhone(resultSet.getString("phone"));
                    employee.setStartDate(resultSet.getDate("startDate"));
                    employee.setHourlyRate(resultSet.getDouble("hourlyRate"));
                    employee.setCurrentHoliday(resultSet.getDouble("currentHoliday"));
                    employees.add(employee);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}
