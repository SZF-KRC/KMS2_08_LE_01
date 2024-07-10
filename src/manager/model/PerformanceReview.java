package manager.model;

import java.util.Date;

public class PerformanceReview {
    private int employeeId;
    private String review;
    private int score;
    private Date reviewDate;

    public PerformanceReview(int employeeId, String review, int score, Date reviewDate) {
        this.employeeId = employeeId;
        this.review = review;
        this.score = score;
        this.reviewDate = reviewDate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getReview() {
        return review;
    }

    public int getScore() {
        return score;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    @Override
    public String toString() {
        return "PerformanceReview{" +
                "employeeId=" + employeeId +
                ", review='" + review + '\'' +
                ", score=" + score +
                ", reviewDate=" + reviewDate +
                '}';
    }
}
