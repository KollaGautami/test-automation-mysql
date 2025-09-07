package com.example.framework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DBManager {
    private static final String URL = "jdbc:mysql://localhost:3306/test_framework";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";

    public static void saveTestResult(String testName, String status, String message) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "INSERT INTO test_results (test_name, status, message) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, testName);
            stmt.setString(2, status);
            stmt.setString(3, message);
            stmt.executeUpdate();

            // Debug print
            System.out.println("✅ Saved to DB: " + testName + " | " + status);
        } catch (Exception e) {
            e.printStackTrace(); // ✅ will show MySQL errors in console
        }
    }
}


