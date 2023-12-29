package com.example.schedulesoft.auth;

/**
 * Class that holds a user session. The purpose of this class is to hold and clear a user session.
 */
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

    /**
     * @return current user session
     */
    public UserSession getSession() {
        return session;
    }

    /**
     * sets the user session
     * @param session session the user session is being set
     */
    public void setSession(UserSession session) {
        this.session = session;
    }

    /**
     * Clears user session (sets it to null)
     */
    public void clear() {
        this.session = null;
    }

    /**
     * @return true if the session is not null
     */
    public boolean containsSession() {
        return session != null;
    }


}
