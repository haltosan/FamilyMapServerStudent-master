package Request;

import Result.LoginResult;

/**
 * Login response sent from the client
 */
public class LoginRequest extends Request{

    public String username, password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
