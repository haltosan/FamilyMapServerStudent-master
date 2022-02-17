package Service;

import Model.AuthToken;
import Request.ClearRequest;

/**
 * Logs in a user, generates auth token, and serves starting data
 */
public class LoginService extends Service {

    private ClearRequest request;
    private AuthToken authToken;

    /**
     * @param request The request that the user sent
     */
    public LoginService(ClearRequest request) {
        this.request = request;
    }

    /**
     * Fetches an authToken for the given login request
     */
    @Override
    public void execute() {

    }

    public AuthToken getAuthToken(){
        return authToken;
    }
}
