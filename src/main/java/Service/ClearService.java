package Service;

import Request.ClearRequest;

/**
 * Clears the database
 */
public class ClearService extends Service{

    private ClearRequest request;

    /**
     * @param request The request that the user sent
     */
    public ClearService(ClearRequest request) {
        this.request = request;
    }

    /**
     * Clears the table as specified in the request
     */
    @Override
    public void execute() {

    }
}
