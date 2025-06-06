package com.example.schedulesoft.dao;

import com.example.schedulesoft.dto.ContactDTO;
import com.example.schedulesoft.util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class encapsulating the details of accessing contacts in the database and performing (only) read operations on them
 * <p>
 * All operations are associated with the "contacts" table in the database
 * </p>
 */
public class ContactDAO implements ReadOnlyDAO<ContactDTO> {

    /**
     * Gets contact with matching id from database
     *
     * @param id id of contact
     * @return contact with id
     */
    @Override
    public ContactDTO getById(int id) {
        String query = "SELECT * FROM client_schedule.contacts WHERE Contact_ID = ?";

        try (PreparedStatement ps = Database.connection.prepareStatement(query)) {

            ps.setInt(1, id);

            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    String name = rs.getString("Contact_Name");
                    String email = rs.getString("Email");

                    return new ContactDTO(id, name, email);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Gets all contacts from the database
     * @return all contacts
     */
    @Override
    public List<ContactDTO> getAll() {
        String query = "SELECT * FROM client_schedule.contacts";

        try (Statement stmt = Database.connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            List<ContactDTO> contactDTOs = new ArrayList<>();

            while(rs.next()) {
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                ContactDTO contactDTO = new ContactDTO(id, name, email);

                contactDTOs.add(contactDTO);
            }

            return contactDTOs;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
