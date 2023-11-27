package com.example.schedulesoft;

import com.example.schedulesoft.dao.UserDAO;
import com.example.schedulesoft.dto.UserDTO;
import com.example.schedulesoft.exception.UsernameNotFoundException;
import com.example.schedulesoft.mapper.UserMapper;
import com.example.schedulesoft.model.User;

public class UserAuth {

    private static final SessionHolder sessionHolder = SessionHolder.getInstance();

    public static boolean authenticate(String username, String password)  {

        UserDAO userDAO = new UserDAO();

        try {
             UserDTO userDTO = userDAO.getByUsername(username);
             User user = UserMapper.toUser(userDTO);

            if(password.equals(user.getPassword())) {

                UserSession session = new UserSession(user);
                sessionHolder.setSession(session);
                System.out.println(username + " has been authenticated successfully.");
                return true;

            }
        } catch (UsernameNotFoundException e) {
            System.out.println("USERNAME NOT FOUND IN DB");
        }

        System.out.println("Unable to authenticate " + username);
        System.out.println("Password is incorrect");
        return false;
    }

    public static boolean deauthenticate() throws Exception {

        if(sessionHolder.containsSession()) {
            String username = sessionHolder.getSession().getUser().getUsername();
            sessionHolder.clear();
            System.out.println(username + " has been logged out successfully");
            return true;
        } else {
            throw new Exception("Session holder should contain a session if deauthentication is possible");
        }
    }

}
