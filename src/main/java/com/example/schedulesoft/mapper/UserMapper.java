package com.example.schedulesoft.mapper;

import com.example.schedulesoft.dto.UserDTO;
import com.example.schedulesoft.exception.UserIdNullException;
import com.example.schedulesoft.model.User;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class UserMapper {

    public static UserDTO toDto(User user) throws UserIdNullException {

        if(user.getId() == 0) {
            throw new UserIdNullException();
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
