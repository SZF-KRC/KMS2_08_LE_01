package manager.model;

import java.util.Date;

public class Goal {
    private int id;
    private int employeeId;
    private String goal;
    private Date startDate;
    private Date endDate;

    public Goal(int id, int employeeId, String goal, Date startDate, Date endDate) {
        this.id = id;
        this.employeeId = employeeId;
        this.goal = goal;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getGoal() {
        return goal;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "Goal{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", goal='" + goal + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
