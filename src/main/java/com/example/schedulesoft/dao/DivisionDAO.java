package com.example.schedulesoft.dao;

import com.example.schedulesoft.dto.CountryDTO;
import com.example.schedulesoft.dto.DivisionDTO;
import com.example.schedulesoft.util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DivisionDAO implements ReadOnlyDAO<DivisionDTO> {
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
