package Service;

import Request.RegisterRequest;

/**
 * Registers a new user in the db
 */
public class RegisterService extends Service {

    private RegisterRequest request;

    /**
     * @param request The request that the user sent
     */
    public RegisterService(RegisterRequest request) {
        this.request = request;
    }

    /**
     * Register a new user given the request from the client
     */
    @Override
    public void execute() {

    }
}
