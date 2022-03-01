package Result;

/**
 * Login response sent to the client
 */
public class LoginResult extends Result {

    public String authtoken, username, personID;

    public LoginResult(String message, boolean success) {
        super(message, success);
        authtoken = null;
        username = null;
        personID = null;
    }

    public LoginResult(String authtoken, String username, String personID, boolean success) {
        super(null, success);
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
    }
}
