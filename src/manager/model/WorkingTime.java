package manager.model;

import java.sql.Timestamp;

public class WorkingTime {
    private int employeeId;
    private Timestamp startWorkTime;
    private Timestamp endWorkTime;
    private Timestamp startBreakTime;
    private Timestamp endBreakTime;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Timestamp getStartWorkTime() {
        return startWorkTime;
    }

    public void setStartWorkTime(Timestamp startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    public Timestamp getEndWorkTime() {
        return endWorkTime;
    }

    public void setEndWorkTime(Timestamp endWorkTime) {
        this.endWorkTime = endWorkTime;
    }

    public Timestamp getStartBreakTime() {
        return startBreakTime;
    }

    public void setStartBreakTime(Timestamp startBreakTime) {
        this.startBreakTime = startBreakTime;
    }

    public Timestamp getEndBreakTime() {
        return endBreakTime;
    }

    public void setEndBreakTime(Timestamp endBreakTime) {
        this.endBreakTime = endBreakTime;
    }
}
