package Request;

import Result.RegisterResult;

/**
 * Register response sent from the client
 */
public class RegisterRequest extends Request{

    public String username, password, email, firstName, lastName, gender;

    public RegisterRequest(String username, String password, String email, String firstName, String lastName, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }
}
