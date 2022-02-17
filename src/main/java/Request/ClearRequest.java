package Request;

import Result.ClearResult;

/**
 * Clear response sent from the client
 */
public class ClearRequest extends Request{

    private ClearResult result;

    /**
     *
     * @param json The json that was sent from the client
     */
    public ClearRequest(String json) {
        super(json);
        result = null;
    }

    @Override
    public ClearResult getResult() {
        return result;
    }
}
