package Model;

import Model.Model;

/**
 * AuthToken object that corresponds with the sql table of the same name
 */
public class AuthToken extends Model {
    private String authtoken;
    private String username;

    /**
     *
     * @param authtoken Random string to authenticate session
     * @param username Username to authenticate
     */
    public AuthToken(String authtoken, String username) {
        this.authtoken = authtoken;
        this.username = username;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
