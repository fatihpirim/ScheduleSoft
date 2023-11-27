package com.example.schedulesoft.dao;

import com.example.schedulesoft.dto.UserDTO;
import com.example.schedulesoft.exception.UsernameNotFoundException;
import com.example.schedulesoft.util.Database;

import java.sql.*;

public class UserDAO {

    public UserDTO getById(int id) {
        String query = "SELECT * FROM client_schedule.users WHERE User_Id = ?";

        try (PreparedStatement ps = Database.connection.prepareStatement(query)) {

            ps.setInt(1, id);

            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    String username = rs.getString("User_Name");
                    String password = rs.getString("Password");
                    Timestamp createdOn = rs.getTimestamp("Create_Date");
                    String createdBy = rs.getString("Created_By");
                    Timestamp lastUpdated = rs.getTimestamp("Last_Update");
                    String lastUpdatedBy = rs.getString("Last_Updated_By");

                    return new UserDTO(id, username, password, createdOn, createdBy,
                            lastUpdated, lastUpdatedBy);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public UserDTO getByUsername(String username) throws UsernameNotFoundException {
        String query = "SELECT * FROM client_schedule.users WHERE User_Name = ?";

        try (PreparedStatement ps = Database.connection.prepareStatement(query)) {
            ps.setString(1, username);

            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    int id = rs.getInt("User_ID");
                    String password = rs.getString("Password");
                    Timestamp createdOn = rs.getTimestamp ("Create_Date");
                    String createdBy = rs.getString("Created_By");
                    Timestamp lastUpdated = rs.getTimestamp("Last_Update");
                    String lastUpdatedBy = rs.getString("Last_Updated_By");

                    return new UserDTO(id, username, password, createdOn, createdBy,
                            lastUpdated, lastUpdatedBy);
                } else {
                    throw new UsernameNotFoundException();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
