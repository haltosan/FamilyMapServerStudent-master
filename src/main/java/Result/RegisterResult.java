package Result;

/**
 * Register response sent to the client
 */
public class RegisterResult extends Result{

    /**
     *
     * @param code HTTP status code
     * @param json Json to return to the client
     */
    public RegisterResult(int code, String json) {
        super(code, json);
    }

}
