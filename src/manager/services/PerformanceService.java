package manager.services;

import manager.database.DatabaseConnection;
import manager.model.PerformanceReview;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PerformanceService {
    public void addPerformanceReview(PerformanceReview review) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO PerformanceReview (employeeId, review, score, reviewDate) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, review.getEmployeeId());
                statement.setString(2, review.getReview());
                statement.setInt(3, review.getScore());
                statement.setDate(4, new java.sql.Date(review.getReviewDate().getTime()));
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PerformanceReview> getPerformanceReviewsForEmployee(int employeeId) {
        List<PerformanceReview> reviews = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM PerformanceReview WHERE employeeId = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, employeeId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("employeeId");
                        String review = resultSet.getString("review");
                        int score = resultSet.getInt("score");
                        Date reviewDate = resultSet.getDate("reviewDate");
                        reviews.add(new PerformanceReview(id, review, score, reviewDate));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public void trackGoals(int employeeId, String goal, Date startDate, Date endDate) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Goals (employeeId, goal, startDate, endDate) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, employeeId);
                statement.setString(2, goal);
                statement.setDate(3, new java.sql.Date(startDate.getTime()));
                statement.setDate(4, new java.sql.Date(endDate.getTime()));
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
