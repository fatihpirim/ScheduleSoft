package com.example.schedulesoft.service;

import com.example.schedulesoft.dao.DivisionDAO;
import com.example.schedulesoft.domain.Country;
import com.example.schedulesoft.domain.Division;
import com.example.schedulesoft.dto.CountryDTO;
import com.example.schedulesoft.dto.DivisionDTO;
import com.example.schedulesoft.mapper.CountryMapper;
import com.example.schedulesoft.mapper.DivisionMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Intermediary between controllers (and other objects) and the division data access object (DAO).
 */
public class DivisionService {

    private final DivisionDAO divisionDAO = new DivisionDAO();

    public DivisionService() {
    }

    /**
     *
     * @param id division id
     * @return Division with id
     */
    public Division getDivisionById(int id) {
        return DivisionMapper.toDivision(divisionDAO.getById(id));
    }

    /**
     * @return all divisions in database
     */
    public ArrayList<Division> getAllDivisions() {

        List<DivisionDTO> divisionDTOs = divisionDAO.getAll();

        return divisionDTOs.stream()
                .map(DivisionMapper::toDivision)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Uses lambda to filter divisions by country id. Concise way of filtering data.
     *
     * @param countryId country id
     * @return all divisions with country id in database
     */
    public ArrayList<Division> getAllDivisionsByCountryId(int countryId) {

        List<DivisionDTO> divisionDTOs = divisionDAO.getAll();

        return divisionDTOs.stream()
                .map(DivisionMapper::toDivision)
                .filter(division -> division.getCountryId() == countryId)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
