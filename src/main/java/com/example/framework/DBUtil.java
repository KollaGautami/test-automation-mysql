package com.example.framework;

import java.sql.*;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/test_framework";
    private static final String USER = "root";
    private static final String PASS = "12345";

    public static void saveResult(String testName, String status, String message) {
        String sql = "INSERT INTO test_results (test_name, status, message) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, testName);
            stmt.setString(2, status);
            stmt.setString(3, message);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
