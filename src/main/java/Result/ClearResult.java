package Result;

/**
 * Clear response sent to the client
 */
public class ClearResult extends Result{

    /**
     *
     * @param code HTTP status code
     * @param json Json to return to the client
     */
    public ClearResult(int code, String json) {
        super(code, json);
    }
}
