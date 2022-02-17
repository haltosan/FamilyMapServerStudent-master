package Request;

import Result.RegisterResult;

/**
 * Register response sent from the client
 */
public class RegisterRequest extends Request{

    private RegisterResult result;

    /**
     *
     * @param json The json that was sent from the client
     */
    public RegisterRequest(String json) {
        super(json);
        result = null;
    }

    @Override
    public RegisterResult getResult() {
        return result;
    }
}
