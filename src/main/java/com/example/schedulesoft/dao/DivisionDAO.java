package com.example.schedulesoft.dao;

import com.example.schedulesoft.dto.DivisionDTO;
import com.example.schedulesoft.util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class encapsulating the details of accessing divisions in the database and performing (only) read operations on them
 * <p>
 * All operations are associated with the "first_level_divisions" table in the database
 * </p>
 */
public class DivisionDAO implements ReadOnlyDAO<DivisionDTO> {

    /**
     * Gets division with matching id from database
     *
     * @param id id of division
     * @return division with id
     */
    @Override
    public DivisionDTO getById(int id) {
        String query = "SELECT * FROM client_schedule.first_level_divisions WHERE Division_ID = ?";

        try (PreparedStatement ps = Database.connection.prepareStatement(query)) {

            ps.setInt(1, id);

            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    String division = rs.getString("Division");
                    Timestamp createdOn = rs.getTimestamp("Create_Date");
                    String createdBy = rs.getString("Created_By");
                    Timestamp lastUpdated = rs.getTimestamp("Last_Update");
                    String lastUpdatedBy = rs.getString("Last_Updated_By");
                    int countryId = rs.getInt("Country_ID");

                    return new DivisionDTO(id, division, createdOn, createdBy, lastUpdated, lastUpdatedBy, countryId);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Gets all divisions from the database
     * @return all divisions
     */
    @Override
    public List<DivisionDTO> getAll()  {
        String query = "SELECT * FROM client_schedule.first_level_divisions";

        try (Statement stmt = Database.connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            List<DivisionDTO> divisionDTOs = new ArrayList<>();

            while(rs.next()) {
                int id = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                Timestamp createdOn = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdated = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countryId = rs.getInt("Country_ID");

                DivisionDTO divisionDTO = new DivisionDTO(id, division, createdOn, createdBy, lastUpdated, lastUpdatedBy, countryId);

                divisionDTOs.add(divisionDTO);
            }

            return divisionDTOs;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
