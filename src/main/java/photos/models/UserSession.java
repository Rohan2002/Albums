/**
 * This class will store the active user session of the application.
 * It provides the global state for the application. The state is the active username.
 * 
 * @author Rohan Deshpande
 * @version 1.0
 */
package main.java.photos.models;

public class UserSession {
    /**
     * Session "instance" will consist of the global state called username.
     */
    private static UserSession instance;
    private String username;

    /**
     * A private constructor to avoid creation of the direct instance of this class.
     * 
     * @param void
     */
    private UserSession() {
    }

    /**
     * Creates the global state store called "instance"
     * 
     * @return UserSession
     */
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    /**
     * Sets the state username in the global state.
     * 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the active username in the global state.
     * 
     * @return The string username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Clear the active username within UserSession.
     * It will be used in the logout functionality.
     */
    public void clearSession() {
        username = null;
    }
}
