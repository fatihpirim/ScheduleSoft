package com.example.schedulesoft.dao;

import com.example.schedulesoft.dto.UserDTO;
import com.example.schedulesoft.exception.UsernameNotFoundException;
import com.example.schedulesoft.util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class encapsulating the details of accessing users in the database and performing (only) read operations on them
 * <p>
 * All operations are associated with the "users" table in the database
 * </p>
 */
public class UserDAO implements ReadOnlyDAO<UserDTO> {

    /**
     * Gets user with matching id from database
     *
     * @param id id of user
     * @return user with id
     */
    @Override
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

    /**
     *
     * Gets user with matching username from database
     *
     * @param username username of user
     * @return user with username
     * @throws UsernameNotFoundException thrown if the username does not exist in the database
     */
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

    /**
     * Gets all users from the database
     * @return all users
     */
    @Override
    public List<UserDTO> getAll() {
        String query = "SELECT * FROM client_schedule.users";

        try (Statement stmt = Database.connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            List<UserDTO> userDTOs = new ArrayList<>();

            while(rs.next()) {
                int id = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                String password = rs.getString("Password");
                Timestamp createdOn = rs.getTimestamp ("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdated = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                UserDTO userDTO = new UserDTO(id, username, password, createdOn, createdBy, lastUpdated, lastUpdatedBy);

                userDTOs.add(userDTO);
            }

            return userDTOs;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
