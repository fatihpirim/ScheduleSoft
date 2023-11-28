package com.example.schedulesoft.dao;

import com.example.schedulesoft.dto.CustomerDTO;
import com.example.schedulesoft.util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements ReadWriteDAO<CustomerDTO> {

    @Override
    public CustomerDTO getById(int id) throws SQLException {

        String query = "SELECT * FROM client_schedule.customers WHERE Customer_ID = ?";

        try (PreparedStatement ps = Database.connection.prepareStatement(query)) {

            ps.setInt(1, id);

            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {

                    String customerName = rs.getString("Customer_Name");
                    String address = rs.getString("Address");
                    String postalCode = rs.getString("Postal_Code");
                    String phone = rs.getString("Phone");
                    Timestamp createDate = rs.getTimestamp("Create_Date");
                    String createdBy = rs.getString("Created_By");
                    Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                    String lastUpdatedBy = rs.getString("Last_Updated_By");
                    int divisionId = rs.getInt("Division_ID");

                    return new CustomerDTO(id, customerName, address, postalCode, phone, createDate, createdBy,
                            lastUpdate, lastUpdatedBy, divisionId);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<CustomerDTO> getAll() throws SQLException {

        String query = "SELECT * FROM client_schedule.customers";

        try (Statement stmt = Database.connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            List<CustomerDTO> customerDTOs = new ArrayList<>();

            while(rs.next()) {
                int id = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int divisionId = rs.getInt("Division_ID");

                CustomerDTO customerDTO = new CustomerDTO(id, customerName, address, postalCode, phone, createDate, createdBy,
                        lastUpdate, lastUpdatedBy, divisionId);

                customerDTOs.add(customerDTO);
            }

            return customerDTOs;
        }
    }

    @Override
    public int insert(CustomerDTO customerDTO) throws SQLException {

        String query = "INSERT INTO client_schedule.customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ? )";

        try (PreparedStatement ps = Database.connection.prepareStatement(query)) {

            ps.setString(1, customerDTO.getName());
            ps.setString(2, customerDTO.getAddress());
            ps.setString(3, customerDTO.getPostalCode());
            ps.setString(4, customerDTO.getPhone());
            ps.setTimestamp(5, customerDTO.getCreateDate());
            ps.setString(6, customerDTO.getCreatedBy());
            ps.setTimestamp(7, customerDTO.getLastUpdate());
            ps.setString(8, customerDTO.getLastUpdatedBy());
            ps.setInt(9, customerDTO.getDivisionId());

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(CustomerDTO customerDTO) throws SQLException {

        String query = "UPDATE client_schedule.customers SET Customer_ID = ?, Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";

        try (PreparedStatement ps = Database.connection.prepareStatement(query)) {

            ps.setInt(1, customerDTO.getId());
            ps.setString(2, customerDTO.getName());
            ps.setString(3, customerDTO.getAddress());
            ps.setString(4, customerDTO.getPostalCode());
            ps.setString(5, customerDTO.getPhone());
            ps.setTimestamp(6, customerDTO.getCreateDate());
            ps.setString(7, customerDTO.getCreatedBy());
            ps.setTimestamp(8, customerDTO.getLastUpdate());
            ps.setString(9, customerDTO.getLastUpdatedBy());
            ps.setInt(10, customerDTO.getDivisionId());

            ps.setInt(11, customerDTO.getId());

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(CustomerDTO customerDTO) throws SQLException {

        String query = "DELETE FROM client_schedule.customers WHERE Customer_ID = ?";

        try (PreparedStatement ps = Database.connection.prepareStatement(query)) {

            ps.setInt(0, customerDTO.getId());

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
