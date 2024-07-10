package manager.services;

import manager.model.PerformanceReview;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PerformanceService {
    private List<PerformanceReview> performanceReviews = new ArrayList<>();

    public void addPerformanceReview(PerformanceReview review) {
        performanceReviews.add(review);
    }

    public List<PerformanceReview> getPerformanceReviewsForEmployee(int employeeId) {
        return performanceReviews.stream()
                .filter(review -> review.getEmployeeId() == employeeId)
                .collect(Collectors.toList());
    }

    public void trackGoals(int employeeId, String goal, Date startDate, Date endDate) {
        // Implement logic to track goals
        System.out.println("Goal: " + goal + " tracked for employee ID: " + employeeId);
    }
}
