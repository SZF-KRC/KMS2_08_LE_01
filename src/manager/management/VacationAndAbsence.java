package manager.management;

import manager.control.InputControl;
import manager.control.SqlControl;
import manager.services.VacationAndAbsenceService;

public class VacationAndAbsence {
    private InputControl control;
    private VacationAndAbsenceService vacationService;
    private SqlControl sqlControl;

    public VacationAndAbsence(InputControl control, VacationAndAbsenceService vacationService, SqlControl sqlControl) {
        this.control = control;
        this.vacationService = vacationService;
        this.sqlControl = sqlControl;
    }

    public void requestHoliday() {
        Integer employeeId = getValidEmployeeId("request holiday");
        if (employeeId != null){vacationService.requestHoliday(employeeId,control.intEntry("Enter number of leave days: "));}
    }

    public void getCurrentHolidays() {
        Integer employeeId = getValidEmployeeId("check current holidays");
        if (employeeId != null){System.out.println("Employee ID: " + employeeId + " has " + vacationService.getCurrentHolidays(employeeId) + " days of holidays left.");}
    }

    private Integer getValidEmployeeId(String prompt){
        if (!sqlControl.anyEmployeeExists()){ System.out.println("No Employees in the Database");return null; }
        int employeeId = control.intEntry("Enter Employee ID to " + prompt + ": ");
        if (!sqlControl.employeeExists(employeeId)){System.out.println("Employee with ID: " + employeeId + " does not exist.");return null;}
        return employeeId;
    }
}
