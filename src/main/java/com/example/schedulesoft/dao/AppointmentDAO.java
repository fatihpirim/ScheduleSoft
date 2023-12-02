package com.example.schedulesoft.dao;

import com.example.schedulesoft.dto.AppointmentDTO;
import javafx.beans.property.IntegerProperty;

import java.sql.SQLException;
import java.util.List;

public class AppointmentDAO implements ReadWriteDAO<AppointmentDTO> {
    @Override
    public AppointmentDTO getById(int id) {
        return null;
    }

    @Override
    public List<AppointmentDTO> getAll() {
        return null;
    }

    @Override
    public int insert(AppointmentDTO appointmentDTO) {
        return 0;
    }

    @Override
    public int update(AppointmentDTO appointmentDTO)  {
        return 0;
    }

    @Override
    public int delete(AppointmentDTO appointmentDTO) {
        return 0;
    }
}
