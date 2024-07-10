package manager.interfaces;

import manager.model.Employee;
import manager.model.Payroll;
import manager.model.WorkingTime;

import java.util.List;

public interface IPayrollService {
    void generatePayroll(Employee employee, List<WorkingTime> workLogs);
    void calculateHoliday(int employeeId);
    double calculateGrossSalary(Employee employee, List<WorkingTime> workLogs);
    double calculateTaxDeduction(double grossSalary);
    List<Payroll> getPayrolls();
    List<WorkingTime> getWorkLogsForEmployee(int employeeID);
}
