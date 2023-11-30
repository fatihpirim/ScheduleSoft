package com.example.schedulesoft;

public class SessionHolder {

    private static SessionHolder instance;

    private UserSession session;

    private SessionHolder() {

    }

    synchronized public static SessionHolder getInstance() {

        if (instance == null) {
            instance = new SessionHolder();
        }

        return instance;
    }

    public UserSession getSession() {
        return session;
    }

    public void setSession(UserSession session) {
        this.session = session;
    }

    public void clear() {
        this.session = null;
    }

    public boolean containsSession() {
        return session != null;
    }


}
