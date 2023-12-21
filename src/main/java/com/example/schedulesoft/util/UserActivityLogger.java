package com.example.schedulesoft.util;

import com.example.schedulesoft.enums.Message;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class UserActivityLogger {

    private static UserActivityLogger instance;

    private UserActivityLogger() {
    }

    public static UserActivityLogger getInstance() {
        if (instance == null) {
            instance = new UserActivityLogger();
        }
        return instance;
    }

    public void logLoginAttempt(Message message) {

        String report = createLogInAttemptReport(message);

        writeToLogFile(report, System.getProperty("user.dir")+"/login_activity.txt");
    }

    public void logLoginAttempt(Message message, String username) {

        String report = createLogInAttemptReport(message, username);

        writeToLogFile(report, System.getProperty("user.dir")+"/login_activity.txt");
    }

    private String createLogInAttemptReport(Message message) {
        ZonedDateTime loginAttemptDateTime = ZonedDateTime.now();
        String dateTime = loginAttemptDateTime.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"));
        String timestamp = Timestamp.valueOf(loginAttemptDateTime.toLocalDateTime()).toString();
        String zoneId = loginAttemptDateTime.getZone().toString();

        String loginResult = "";

        if(message.equals(Message.SUCCESS)) {
            loginResult = "SUCCESS";
        } else if(message.equals(Message.FAILURE)) {
            loginResult = "FAILURE";
        }

        String line1 = String.format("%-18s %-30s %-20s \n", "Login Attempt On:", dateTime, zoneId);
        String line2 = String.format("%-18s %-30s %-20s \n", "Timestamp:", timestamp, zoneId);
        String line3 = String.format("%-18s %-30s \n\n", "Result:", loginResult);

        return line1 + line2 + line3;
    }

    private String createLogInAttemptReport(Message message, String username) {
        String report = createLogInAttemptReport(message);

        String line0 = String.format("%-18s %-30s\n", "Username:", username);

        return line0 + report;
    }

    private void writeToLogFile(String report, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(report);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
