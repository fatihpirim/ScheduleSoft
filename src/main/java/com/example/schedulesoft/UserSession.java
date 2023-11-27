package com.example.schedulesoft;

import com.example.schedulesoft.model.User;
import java.util.Objects;

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
