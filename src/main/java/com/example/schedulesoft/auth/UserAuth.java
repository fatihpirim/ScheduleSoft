package com.example.schedulesoft.auth;

import com.example.schedulesoft.dao.UserDAO;
import com.example.schedulesoft.dto.UserDTO;
import com.example.schedulesoft.exception.UsernameNotFoundException;
import com.example.schedulesoft.mapper.UserMapper;
import com.example.schedulesoft.domain.User;

/**
 * Class responsible for handling user authentication during log in
 */
public class UserAuth {

    /**
     * Reference to session holder which will hold the user session (if the user is authenticated)
     */
    private static final SessionHolder sessionHolder = SessionHolder.getInstance();

    /**
     * Authenticates user using username and password
     * @param username username of user
     * @param password password of user
     * @return success if authenticated
     */
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

    /**
     * De-authenticates (logs out) the logged-in user
     * @return success if de-authenticated (logged out) successfully
     * @throws Exception thrown if the session holder is not holding a user session to begin with
     */
    public static boolean deauthenticate() throws Exception {

        if(sessionHolder.containsSession()) {
            String username = sessionHolder.getSession().getUser().getUsername();
            sessionHolder.clear();
            System.out.println(username + " has been logged out successfully");
            return true;
        } else {
            throw new Exception("Session holder should contain a session if de-authentication is possible");
        }
    }

}
