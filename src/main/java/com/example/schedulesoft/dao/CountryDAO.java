package com.example.schedulesoft.dao;

import com.example.schedulesoft.dto.CountryDTO;
import com.example.schedulesoft.util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryDAO implements ReadOnlyDAO<CountryDTO>{
    @Override
    public CountryDTO getById(int id)  {
        String query = "SELECT * FROM client_schedule.countries WHERE Country_Id = ?";

        try (PreparedStatement ps = Database.connection.prepareStatement(query)) {

            ps.setInt(1, id);

            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    String country = rs.getString("Country");
                    Timestamp createdOn = rs.getTimestamp("Create_Date");
                    String createdBy = rs.getString("Created_By");
                    Timestamp lastUpdated = rs.getTimestamp("Last_Update");
                    String lastUpdatedBy = rs.getString("Last_Updated_By");

                    return new CountryDTO(id, country, createdOn, createdBy, lastUpdated, lastUpdatedBy);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<CountryDTO> getAll()  {
        String query = "SELECT * FROM client_schedule.countries";

        try (Statement stmt = Database.connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            List<CountryDTO> countryDTOs = new ArrayList<>();

            while(rs.next()) {
                int id = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                Timestamp createdOn = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdated = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                CountryDTO countryDTO = new CountryDTO(id, country, createdOn, createdBy, lastUpdated, lastUpdatedBy);

                countryDTOs.add(countryDTO);
            }

            return countryDTOs;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
