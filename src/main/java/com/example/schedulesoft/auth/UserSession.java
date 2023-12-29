package com.example.schedulesoft.auth;

import com.example.schedulesoft.domain.User;
import java.util.Objects;

/**
 * Class that holds a user (generally one that is authenticated).
 * The class contains simple getters and setters
 */
public class UserSession {

    private final User user;

    public UserSession(User user) {
        this.user = user;

    }

    public User getUser() {
        return user;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSession that = (UserSession) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "user=" + user ;
    }
}
