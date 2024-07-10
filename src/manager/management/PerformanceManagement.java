package manager.management;

import manager.control.InputControl;
import manager.interfaces.IEmployeeService;
import manager.model.Employee;
import manager.model.PerformanceReview;
import manager.services.EmployeeService;
import manager.services.PerformanceService;

import java.util.Date;

public class PerformanceManagement {
    private InputControl control;
    private IEmployeeService employeeService;
    private PerformanceService performanceService;

    public PerformanceManagement(InputControl control, IEmployeeService employeeService, PerformanceService performanceService) {
        this.control = control;
        this.employeeService = employeeService;
        this.performanceService = performanceService;
    }

    public void addPerformanceReview() {
        int employeeId = control.intEntry("Enter Employee ID for performance review: ");
        String review = control.stringEntry("Enter performance review: ");
        int score = control.intEntry("Enter performance score (1-10): ");
        Date reviewDate = new Date();

        PerformanceReview performanceReview = new PerformanceReview(employeeId, review, score, reviewDate);
        performanceService.addPerformanceReview(performanceReview);
        System.out.println("Performance review added for employee ID: " + employeeId);
    }

    public void listPerformanceReviews() {
        int employeeId = control.intEntry("Enter Employee ID to list performance reviews: ");
        performanceService.getPerformanceReviewsForEmployee(employeeId).forEach(System.out::println);
    }

    public void trackGoals() {
        int employeeId = control.intEntry("Enter Employee ID to track goals: ");
        String goal = control.stringEntry("Enter goal: ");
        Date startDate = new Date();
        Date endDate = control.dateEntry("Enter goal end date (yyyy-mm-dd): ");

        performanceService.trackGoals(employeeId, goal, startDate, endDate);
        System.out.println("Goal tracked for employee ID: " + employeeId);
    }
}
