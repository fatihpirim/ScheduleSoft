package com.example.schedulesoft.mapper;

import com.example.schedulesoft.dto.UserDTO;
import com.example.schedulesoft.exception.InvalidIdException;
import com.example.schedulesoft.domain.User;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class UserMapper {

    public static UserDTO toDto(User user) throws InvalidIdException {

        if(user.getId() == 0) { // and int is 0 by default -> id is 0 by default
            throw new InvalidIdException();
        }

        int id = user.getId();
        String username = user.getUsername();
        String password = user.getPassword();
        Timestamp createdOn = Timestamp.valueOf(user.getCreatedOn().toLocalDateTime());
        String createdBy = user.getCreatedBy();
        Timestamp lastUpdated = Timestamp.valueOf(user.getLastUpdated().toLocalDateTime());
        String lastUpdatedBy = user.getLastUpdatedBy();

        return new UserDTO(id, username, password, createdOn, createdBy, lastUpdated, lastUpdatedBy);
    }

    public static User toUser(UserDTO userDTO) {

        int id = userDTO.getId();
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        ZonedDateTime createdOn = userDTO.getCreatedOn().toLocalDateTime().atZone(ZoneId.of("UTC"));
        String createdBy = userDTO.getCreatedBy();
        ZonedDateTime lastUpdated = userDTO.getLastUpdated().toLocalDateTime().atZone(ZoneId.of("UTC"));
        String lastUpdatedBy = userDTO.getLastUpdatedBy();

        User user = new User(username, password, createdOn, createdBy, lastUpdated, lastUpdatedBy);
        user.setId(id);

        return user;
    }

}
