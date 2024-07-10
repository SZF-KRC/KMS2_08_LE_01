package manager.management;

import manager.control.InputControl;
import manager.control.SqlControl;
import manager.interfaces.IEmployeeService;
import manager.interfaces.IPayrollService;
import manager.model.Employee;

public class AdministrationPayroll {
    private IPayrollService payrollService;
    private InputControl control;
    private IEmployeeService employeeService;
    private SqlControl sqlControl;

    public AdministrationPayroll(IPayrollService payrollService, InputControl control, IEmployeeService employeeService, SqlControl sqlControl) {
        this.control = control;
        this.payrollService = payrollService;
        this.employeeService = employeeService;
        this.sqlControl = sqlControl;
    }

    public void generatePayroll() {
        Integer employeeId = getValidEmployeeId("payroll generation");
        if (employeeId == null){return;}
        Employee employee = employeeService.getAllEmployees().stream()
                .filter(e -> e.getId() == employeeId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
        payrollService.generatePayroll(employee, payrollService.getWorkLogsForEmployee(employeeId));
        System.out.println("Payroll generated for employee ID: " + employeeId);
    }

    public void listAllPayrolls() {
        if (sqlControl.anyEmployeeExists()){payrollService.getPayrolls().forEach(System.out::println);}
    }

    public void calculateTaxDeductionForEmployee() {
        Integer employeeId = getValidEmployeeId("calculate tax deduction");
        if (employeeId == null){return;}
        Employee employee = employeeService.getAllEmployees().stream()
                .filter(e -> e.getId() == employeeId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        double grossSalary = payrollService.calculateGrossSalary(employee, payrollService.getWorkLogsForEmployee(employeeId));
        double taxDeduction = payrollService.calculateTaxDeduction(grossSalary);
        System.out.println("Gross Salary: " + grossSalary);
        System.out.println("Calculated Tax Deduction: " + taxDeduction);
    }

    private Integer getValidEmployeeId(String prompt){
        if (!sqlControl.anyEmployeeExists()){ System.out.println("No Employees in the Database");return null; }
        int employeeId = control.intEntry("Enter Employee ID to " + prompt + ": ");
        if (!sqlControl.employeeExists(employeeId)){System.out.println("Employee with ID: " + employeeId + " does not exist.");return null;}
        return employeeId;
    }
}
