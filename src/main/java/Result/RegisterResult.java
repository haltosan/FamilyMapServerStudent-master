package Result;

/**
 * Register response sent to the client
 */
public class RegisterResult extends Result{

    public String authtoken, username, personID;

    public RegisterResult(String message, boolean success) {
        super(message, success);
    }
    public RegisterResult(String authtoken, String username, String personID, boolean success){
        super(null, success);
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
    }

}
