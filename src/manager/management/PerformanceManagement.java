package manager.management;

import manager.control.InputControl;
import manager.control.SqlControl;
import manager.model.PerformanceReview;
import manager.services.PerformanceService;

import java.util.Date;

public class PerformanceManagement {
    private InputControl control;

    private PerformanceService performanceService;
    private SqlControl sqlControl;

    public PerformanceManagement(InputControl control, PerformanceService performanceService, SqlControl sqlControl) {
        this.control = control;

        this.performanceService = performanceService;
        this.sqlControl = sqlControl;
    }

    public void addPerformanceReview() {
        Integer employeeId = getValidEmployeeId("performance review");
        if (employeeId == null){return;}
        String review = control.stringEntry("Enter performance review: ");
        int score = control.intEntry("Enter performance score (1-10): ");
        Date reviewDate = new Date();

        PerformanceReview performanceReview = new PerformanceReview(employeeId, review, score, reviewDate);
        performanceService.addPerformanceReview(performanceReview);
        System.out.println("Performance review added for employee ID: " + employeeId);
    }

    public void listPerformanceReviews() {
        Integer employeeId = getValidEmployeeId("list performance reviews");
        if (employeeId != null){performanceService.getPerformanceReviewsForEmployee(employeeId).forEach(System.out::println);}
    }

    public void trackGoals() {
        Integer employeeId = getValidEmployeeId("track goals");
        if (employeeId == null){return;}
        String goal = control.stringEntry("Enter goal: ");
        Date startDate = new Date();
        Date endDate = control.dateEntry("Enter goal end date (yyyy-mm-dd): ");

        performanceService.trackGoals(employeeId, goal, startDate, endDate);
        System.out.println("Goal tracked for employee ID: " + employeeId);
    }
    private Integer getValidEmployeeId(String prompt){
        if (!sqlControl.anyEmployeeExists()){ System.out.println("No Employees in the Database");return null; }
        int employeeId = control.intEntry("Enter Employee ID to " + prompt + ": ");
        if (!sqlControl.employeeExists(employeeId)){System.out.println("Employee with ID: " + employeeId + " does not exist.");return null;}
        return employeeId;
    }
}
