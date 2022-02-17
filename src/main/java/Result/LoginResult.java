package Result;

/**
 * Login response sent to the client
 */
public class LoginResult extends Result{

    /**
     *
     * @param code HTTP status code
     * @param json Json to return to the client
     */
    public LoginResult(int code, String json) {
        super(code, json);
    }

    /**
     * Sends response to client
     */
    @Override
    public void sendRequest() {

    }
}
