package Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.UserDAO;
import Model.AuthToken;
import Model.User;
import Request.ClearRequest;
import Request.LoginRequest;
import Result.LoginResult;

/**
 * Logs in a user, generates auth token, and serves starting data
 */
public class LoginService extends Service {

    private final LoginRequest request;
    private LoginResult result;

    /**
     * @param request The request that the user sent
     */
    public LoginService(LoginRequest request) {
        this.request = request;
    }

    /**
     * Fetches an authToken for the given login request
     */
    @Override
    public void execute() {
        //check for username and password in database
        UserDAO userDAO = new UserDAO(connection);
        User foundUser;
        try {
            foundUser = userDAO.find(request.username, request.password);
        }
        catch (DataAccessException exception){
            exception.printStackTrace();
            System.out.println("Issue with finding user");
            result = new LoginResult("Error: Issue with finding user", false);
            return;
        }
        //look up auth token
        AuthTokenDAO authTokenDAO = new AuthTokenDAO(connection);
        AuthToken authToken;
        try{
            authToken = authTokenDAO.find(foundUser.getUsername());
        }
        catch (DataAccessException exception){
            exception.printStackTrace();
            System.out.println("Issue with finding auth token");
            result = new LoginResult("Error: Issue with finding auth token", false);
            return;
        }
        result = new LoginResult(authToken.getAuthtoken(), foundUser.getUsername(), foundUser.getPersonID(), true);
    }

    public LoginResult getResult(){
        return result;
    }
}
