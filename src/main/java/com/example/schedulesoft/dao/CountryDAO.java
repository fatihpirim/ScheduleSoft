package com.example.schedulesoft.dao;

import com.example.schedulesoft.dto.CountryDTO;
import com.example.schedulesoft.util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class encapsulating the details of accessing countries in the database and performing (only) read operations on them
 * <p>
 * All operations are associated with the "countries" table in the database
 * </p>
 */
public class CountryDAO implements ReadOnlyDAO<CountryDTO>{

    /**
     * Gets country with matching id from database
     *
     * @param id id of country
     * @return country with id
     */
    @Override
    public CountryDTO getById(int id)  {
        String query = "SELECT * FROM client_schedule.countries WHERE Country_ID = ?";

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

    /**
     * Gets all countries from the database
     * @return all countries
     */
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
