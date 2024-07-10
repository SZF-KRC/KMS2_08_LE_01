package manager.management;

import manager.control.InputControl;
import manager.control.SqlControl;
import manager.interfaces.IPayrollService;
import manager.interfaces.IWorkTimeService;

public class WorkTime {
    private InputControl control;
    private IWorkTimeService workTimeService;
    private IPayrollService payrollService;
    private SqlControl sqlControl;

    public WorkTime(IWorkTimeService workTimeService, InputControl control, IPayrollService payrollService, SqlControl sqlControl){
        this.control = control;
        this.workTimeService = workTimeService;
        this.payrollService = payrollService;
        this.sqlControl = sqlControl;
    }

    public void checkIn() {
        Integer employeeId = getValidEmployeeId("check in");
        if (employeeId != null) { workTimeService.checkIn(employeeId);}
    }

    public void checkOut() {
        Integer employeeId = getValidEmployeeId("check out");
        if (employeeId != null) { workTimeService.checkOut(employeeId);payrollService.calculateHoliday(employeeId);}
    }

    public void startBreak() {
        Integer employeeId = getValidEmployeeId("start break");
        if (employeeId != null) {workTimeService.startBreak(employeeId);}
    }

    public void endBreak() {
        Integer employeeId = getValidEmployeeId("end break");
        if (employeeId != null) {workTimeService.endBreak(employeeId);}
    }

    // Added method to list employees currently working
    public void listCurrentlyWorkingEmployees() {
        if (!sqlControl.anyEmployeeExists()){System.out.println("No Employees in the Database");return;}
        workTimeService.listCurrentlyWorkingEmployees();
    }

    // Added method to list employees currently on break
    public void listCurrentlyOnBreakEmployees() {
        if (!sqlControl.anyEmployeeExists()){System.out.println("No Employees in the Database");return;}
        workTimeService.listCurrentlyOnBreakEmployees();
    }

    private Integer getValidEmployeeId(String prompt){
        if (!sqlControl.anyEmployeeExists()){ System.out.println("No Employees in the Database");return null; }
        int employeeId = control.intEntry("Enter Employee ID to " + prompt + ": ");
        if (!sqlControl.employeeExists(employeeId)){System.out.println("Employee with ID: " + employeeId + " does not exist.");return null;}
        return employeeId;
    }
}
