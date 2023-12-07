package com.example.schedulesoft.service;

import com.example.schedulesoft.dao.UserDAO;
import com.example.schedulesoft.domain.Contact;
import com.example.schedulesoft.domain.User;
import com.example.schedulesoft.dto.ContactDTO;
import com.example.schedulesoft.dto.UserDTO;
import com.example.schedulesoft.mapper.ContactMapper;
import com.example.schedulesoft.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private final UserDAO userDAO = new UserDAO();

    public UserService() {
    }

    public User getUserById(int id) {
        return UserMapper.toUser(userDAO.getById(id));
    }

    public ArrayList<User> getAllUsers() {

        List<UserDTO> userDTOs = userDAO.getAll();

        return userDTOs.stream()
                .map(UserMapper::toUser)
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
