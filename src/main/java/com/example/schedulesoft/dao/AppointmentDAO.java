package com.example.schedulesoft.dao;

import com.example.schedulesoft.dto.AppointmentDTO;
import com.example.schedulesoft.util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO implements ReadWriteDAO<AppointmentDTO> {
    @Override
    public AppointmentDTO getById(int id) {

        String query = "SELECT * FROM client_schedule.appointments WHERE Appointment_ID = ?";

        try (PreparedStatement ps = Database.connection.prepareStatement(query)) {

            ps.setInt(1, id);

            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {

                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    Timestamp start = rs.getTimestamp("Start");
                    Timestamp end = rs.getTimestamp("End");
                    Timestamp createDate = rs.getTimestamp("Create_Date");
                    String createdBy = rs.getString("Created_By");
                    Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                    String lastUpdatedBy = rs.getString("Last_Updated_By");
                    int customerId = rs.getInt("Customer_ID");
                    int userId = rs.getInt("User_ID");
                    int contactId = rs.getInt("Contact_ID");

                    return new AppointmentDTO(id, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;

    }

    @Override
    public List<AppointmentDTO> getAll() {
        String query = "SELECT * FROM client_schedule.appointments";

        try (Statement stmt = Database.connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            List<AppointmentDTO> appointmentDTOs = new ArrayList<>();

            while(rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                AppointmentDTO appointmentDTO = new AppointmentDTO(id, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);

                appointmentDTOs.add(appointmentDTO);
            }

            return appointmentDTOs;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int insert(AppointmentDTO appointmentDTO) {
        String query = "INSERT INTO client_schedule.appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = Database.connection.prepareStatement(query)) {

            ps.setString(1,appointmentDTO.getTitle());
            ps.setString(2, appointmentDTO.getDescription());
            ps.setString(3, appointmentDTO.getLocation());
            ps.setString(4, appointmentDTO.getType());
            ps.setTimestamp(5, appointmentDTO.getStart());
            ps.setTimestamp(6, appointmentDTO.getEnd());
            ps.setTimestamp(7, appointmentDTO.getCreateDate());
            ps.setString(8, appointmentDTO.getCreatedBy());
            ps.setTimestamp(9, appointmentDTO.getLastUpdate());
            ps.setString(10, appointmentDTO.getLastUpdatedBy());
            ps.setInt(11, appointmentDTO.getCustomerId());
            ps.setInt(12,appointmentDTO.getUserId());
            ps.setInt(13, appointmentDTO.getContactId());

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int update(AppointmentDTO appointmentDTO)  {

        String query = "UPDATE client_schedule.appointments SET Appointment_ID = ?, Title = ?, Description = ?, Location = ?, Type = ?, " +
                "Start = ?, End = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

        try (PreparedStatement ps = Database.connection.prepareStatement(query)) {

            ps.setInt(1, appointmentDTO.getId());
            ps.setString(2,appointmentDTO.getTitle());
            ps.setString(3, appointmentDTO.getDescription());
            ps.setString(4, appointmentDTO.getLocation());
            ps.setString(5, appointmentDTO.getType());
            ps.setTimestamp(6, appointmentDTO.getStart());
            ps.setTimestamp(7, appointmentDTO.getEnd());
            ps.setTimestamp(8, appointmentDTO.getCreateDate());
            ps.setString(9, appointmentDTO.getCreatedBy());
            ps.setTimestamp(10, appointmentDTO.getLastUpdate());
            ps.setString(11, appointmentDTO.getLastUpdatedBy());
            ps.setInt(12, appointmentDTO.getCustomerId());
            ps.setInt(13,appointmentDTO.getUserId());
            ps.setInt(14, appointmentDTO.getContactId());

            ps.setInt(15, appointmentDTO.getId());

            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(AppointmentDTO appointmentDTO) {
        String query = "DELETE FROM client_schedule.appointments WHERE Appointment_ID = ?";

        try (PreparedStatement ps = Database.connection.prepareStatement(query)) {

            ps.setInt(1, appointmentDTO.getId());

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
