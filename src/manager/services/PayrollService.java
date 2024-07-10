package manager.services;

import manager.database.DatabaseConnection;
import manager.interfaces.IPayrollService;
import manager.model.Employee;
import manager.model.FullTimeEmployee;
import manager.model.Payroll;
import manager.model.WorkingTime;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PayrollService implements IPayrollService {
    private List<Payroll> payrolls;

    @Override
    public void generatePayroll(Employee employee, List<WorkingTime> workLogs) {
        //Payroll payroll = payrolls.stream().filter(p -> p.getEmployee() == employee).findFirst().orElseThrow(() -> new IllegalArgumentException("Employee not found"));
        double grossSalary = calculateGrossSalary(employee, workLogs);
        //double grossSalary = payroll.getGrossSalary();
        double taxDeduction = calculateTaxDeduction(grossSalary);
        double netSalary = grossSalary - taxDeduction;

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO payrolls (employeeId, grossSalary, taxDeduction, netSalary, paymentDate) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, employee.getId());
                statement.setDouble(2, grossSalary);
                statement.setDouble(3, taxDeduction);
                statement.setDouble(4, netSalary);
                statement.setDate(5, new java.sql.Date(new Date().getTime()));
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private long calculateWorkedTime(List<WorkingTime> workLogs){
        long totalWorkedTime = 0;
        for (WorkingTime workLog : workLogs) {
            if (workLog.getEndWorkTime() != null) {
                long checkIn = workLog.getStartWorkTime().getTime();
                long checkOut = workLog.getEndWorkTime().getTime();
                long breakStart = workLog.getStartBreakTime() != null ? workLog.getStartBreakTime().getTime() : 0;
                long breakEnd = workLog.getEndBreakTime() != null ? workLog.getEndBreakTime().getTime() : 0;

                long workedTime = (checkOut - checkIn) - (breakEnd - breakStart);
                totalWorkedTime += workedTime;
            }
        }
        return totalWorkedTime;
    }

    public void calculateHoliday(int employeeId){
        int totalHolidaysPerYear= 60;
        double totalWorkingHoursPerYear = 2080;

        Employee employee = getEmployeeById(employeeId);
        if (employee != null){
            List<WorkingTime> workLogs = getWorkLogsForEmployee(employeeId);

            double hoursWorked = TimeUnit.MILLISECONDS.toHours(calculateWorkedTime(workLogs));
            double oneHourHoliday = totalHolidaysPerYear / totalWorkingHoursPerYear;
            double currentHoliday = hoursWorked * oneHourHoliday;

            employee.setCurrentHoliday(currentHoliday);
            try (Connection connection = DatabaseConnection.getConnection()){
                String query = "UPDATE employees SET currentHoliday = ? WHERE id = ?";
                try (PreparedStatement statement = connection.prepareStatement(query)){
                    statement.setDouble(1,employee.getCurrentHoliday());
                    statement.setInt(2, employeeId);
                    statement.executeUpdate();
                }
            }catch (SQLException e){e.printStackTrace();}
        }
    }
    public void calculateGrossSalaryX(Employee employee, List<WorkingTime> workLogs) {
        double hoursWorked = TimeUnit.MILLISECONDS.toHours(calculateWorkedTime(workLogs));
        Payroll payroll = payrolls.stream().filter(p -> p.getEmployee() == employee).findFirst().orElseThrow(() -> new IllegalArgumentException("Employee not found"));
        payroll.setGrossSalary(employee.calculateSalary() * hoursWorked);
    }

    public double calculateGrossSalary(Employee employee, List<WorkingTime> workLogs) {
        Payroll payroll = payrolls.stream().filter(p -> p.getEmployee() == employee).findFirst().orElseThrow(() -> new IllegalArgumentException("Employee not found"));
        double hoursWorked = TimeUnit.MILLISECONDS.toHours(calculateWorkedTime(workLogs));
        double grossSalary = employee.calculateSalary() * hoursWorked;
        payroll.setGrossSalary(grossSalary);
        return grossSalary;
    }

    public double calculateTaxDeduction(double grossSalary) {return grossSalary * 0.20;} // tax rate of 20%


    @Override
    public List<Payroll> getPayrolls() {
        payrolls = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM payrolls";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    Payroll payroll = new Payroll();
                    payroll.setPayrollId(resultSet.getInt("id"));
                    payroll.setGrossSalary(resultSet.getDouble("grossSalary"));
                    payroll.setTaxDeduction(resultSet.getDouble("taxDeduction"));
                    payroll.setNetSalary(resultSet.getDouble("netSalary"));
                    payroll.setPaymentDate(resultSet.getDate("paymentDate"));

                    int employeeId = resultSet.getInt("employeeId");
                    Employee employee = getEmployeeById(employeeId);
                    payroll.setEmployee(employee);
                    payrolls.add(payroll);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payrolls;
    }

    private Employee getEmployeeById(int employeeId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM employees WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, employeeId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        FullTimeEmployee employee = new FullTimeEmployee();
                        employee.setId(resultSet.getInt("id"));
                        employee.setName(resultSet.getString("name"));
                        employee.setSurname(resultSet.getString("surname"));
                        employee.setAge(resultSet.getInt("age"));
                        employee.setPhone(resultSet.getString("phone"));
                        employee.setStartDate(resultSet.getDate("startDate"));
                        employee.setHourlyRate(resultSet.getDouble("hourlyRate"));
                        employee.setCurrentHoliday(resultSet.getDouble("currentHoliday"));
                        return employee;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<WorkingTime> getWorkLogsForEmployee(int employeeID) {
        List<WorkingTime> workLogs = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM workLogs WHERE employeeId = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, employeeID);
                try (ResultSet resultSet = statement.executeQuery()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return workLogs;
    }
}
