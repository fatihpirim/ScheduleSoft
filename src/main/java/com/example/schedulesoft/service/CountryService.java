package com.example.schedulesoft.service;

import com.example.schedulesoft.dao.CountryDAO;
import com.example.schedulesoft.domain.Country;
import com.example.schedulesoft.domain.Customer;
import com.example.schedulesoft.dto.CountryDTO;
import com.example.schedulesoft.dto.CustomerDTO;
import com.example.schedulesoft.mapper.CountryMapper;
import com.example.schedulesoft.mapper.CustomerMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Intermediary between controllers (and other objects) and the country data access object (DAO).
 */
public class CountryService {

    private final CountryDAO countryDAO = new CountryDAO();

    public CountryService() {
    }

    /**
     *
     * @param id country id
     * @return Country with the id
     */
    public Country getCountryById(int id) {

        return CountryMapper.toCountry(countryDAO.getById(id));

    }

    /**
     * @return all countries in database
     */
    public ArrayList<Country> getAllCountries() {

        List<CountryDTO> countryDTOs = countryDAO.getAll();

        return countryDTOs.stream()
                .map(CountryMapper::toCountry)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
