package Service;

import Request.FillRequest;

/**
 * Fills the database with data
 */
public class FillService extends Service{

    private FillRequest request;

    /**
     * @param request The request that the user sent
     */
    public FillService(FillRequest request) {
        this.request = request;
    }

    /**
     * Fills the database as indicated in the request
     */
    @Override
    public void execute() {

    }
}
