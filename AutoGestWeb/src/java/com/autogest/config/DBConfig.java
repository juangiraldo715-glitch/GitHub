package com.autogest.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConfig {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/autogest_web", "root", "ProgramA-56*38");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}