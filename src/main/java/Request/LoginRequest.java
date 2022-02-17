package Request;

import Result.LoginResult;

/**
 * Login response sent from the client
 */
public class LoginRequest extends Request{

    private LoginResult result;

    /**
     *
     * @param json The json that was sent from the client
     */
    public LoginRequest(String json) {
        super(json);
        result = null;
    }

    @Override
    public LoginResult getResult() {
        return result;
    }
}
