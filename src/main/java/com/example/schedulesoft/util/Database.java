package com.example.schedulesoft.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public abstract class Database {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser";
    private static String password = "Passw0rd!";

    public static Connection connection;
    private static PreparedStatement preparedStatement;
    public static String connectionStatus;

    public static void getConnection() {

        java.util.Properties connectionProps = new java.util.Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        connectionProps.put("useLegacyDatetimeCode", "false"); // Disable legacy datetime code
        connectionProps.put("serverTimezone", "UTC"); // Set server timezone to UTC

        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, connectionProps); // reference Connection object
            connectionStatus = "Database Connection successful!";
        }
        catch(ClassNotFoundException e) {
            System.out.println("Error:" + e.getMessage());
        }
        catch(SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    public static void closeConnection() {
        try {
            connection.close();
            connectionStatus = "Database Connection closed!";
            System.out.println(connectionStatus);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
