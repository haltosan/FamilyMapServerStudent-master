package Request;

import Result.FillResult;

/**
 * Fill response sent from the client
 */
public class FillRequest extends Request{

    private FillResult result;

    /**
     *
     * @param json The json that was sent from the client
     */
    public FillRequest(String json) {
        super(json);
        result = null;
    }

    @Override
    public FillResult getResult() {
        return result;
    }
}
