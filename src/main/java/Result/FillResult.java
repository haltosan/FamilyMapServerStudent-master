package Result;

/**
 * Fill response sent to the client
 */
public class FillResult extends Result{

    /**
     *
     * @param code HTTP status code
     * @param json Json to return to the client
     */
    public FillResult(int code, String json) {
        super(code, json);
    }

}
