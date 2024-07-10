package manager.management;

import manager.control.InputControl;
import manager.control.SqlControl;
import manager.interfaces.IEmployeeService;
import manager.interfaces.IPersonnelRecords;
import manager.model.Employee;
import manager.model.FullTimeEmployee;
import java.util.Date;

public class PersonnelRecords implements IPersonnelRecords {
    private final InputControl control;
    private final IEmployeeService employeeService;
    private final SqlControl sqlControl;

    public PersonnelRecords(InputControl control, IEmployeeService employeeService, SqlControl sqlControl){
        this.control = control;
        this.employeeService = employeeService;
        this.sqlControl = sqlControl;
    }

    public void addNewEmployee(){
        FullTimeEmployee employee = new FullTimeEmployee();
        employee.setName(control.stringEntry("Enter name: "));
        employee.setSurname(control.stringEntry("Enter surname: "));
        employee.setAge(control.intEntry("Enter age: "));
        employee.setHourlyRate(control.doubleEntry("Enter hourly rate: "));
        employee.setPhone(control.phoneNumberEntry("Enter phone: "));
        employee.setStartDate(new Date());
        employee.setCurrentHoliday(0);
        employeeService.addEmployee(employee);
        System.out.println("Employee added with ID: " + employee.getId());
    }
    public void updateEmployee() {
        Integer employeeId = getValidEmployeeId("update");
        if (employeeId == null){return;}

        FullTimeEmployee employee = new FullTimeEmployee();
        employee.setName(control.stringEntry("Enter new name: "));
        employee.setSurname(control.stringEntry("Enter new surname: "));
        employee.setAge(control.intEntry("Enter new age: "));
        employee.setHourlyRate(control.doubleEntry("Enter new hourly rate: "));
        employee.setPhone(control.phoneNumberEntry("Enter new phone: "));
        employee.setStartDate(new Date());
        employee.setCurrentHoliday(employee.getCurrentHoliday());
        employee.setId(employeeId);
        employeeService.updateEmployee(employeeId, employee);
        System.out.println("Employee updated with ID: " + employeeId);
    }
    public void deleteEmployee() {
        Integer employeeId = getValidEmployeeId("delete");
        if (employeeId != null) {employeeService.deleteEmployee(employeeId);}
    }
    public void listAllEmployees() {
        if (!sqlControl.anyEmployeeExists()){ System.out.println("No Employees in the Database");return;}
        for (Employee employee : employeeService.getAllEmployees()) {
            System.out.println(employee);
        }
    }

    private Integer getValidEmployeeId(String prompt){
        if (!sqlControl.anyEmployeeExists()){ System.out.println("No Employees in the Database");return null; }
        int employeeId = control.intEntry("Enter Employee ID to " + prompt + ": ");
        if (!sqlControl.employeeExists(employeeId)){System.out.println("Employee with ID: " + employeeId + " does not exist.");return null;}
        return employeeId;
    }
}
