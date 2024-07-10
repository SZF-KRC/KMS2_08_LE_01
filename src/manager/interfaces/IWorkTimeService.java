package manager.interfaces;

public interface IWorkTimeService {

    void checkIn(int employeeId);
    void checkOut(int employeeId);
    void startBreak(int employeeId);
    void endBreak(int employeeId);
    void listCurrentlyWorkingEmployees();
    void listCurrentlyOnBreakEmployees();
}
