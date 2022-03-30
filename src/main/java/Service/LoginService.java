package Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.UserDAO;
import Model.AuthToken;
import Model.User;
import Request.ClearRequest;
import Request.LoginRequest;
import Result.LoginResult;

import java.sql.Connection;

/**
 * Logs in a user, generates auth token, and serves starting data
 */
public class LoginService extends Service {

    private final LoginRequest request;
    private final Connection connection;
    private LoginResult result;

    /**
     * @param request The request that the user sent
     */
    public LoginService(LoginRequest request, Connection connection) {
        this.request = request;
        this.connection = connection;
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
        if(foundUser == null){
            result = new LoginResult("Error: Model.User not found.", false);
            return;
        }
        //look up auth token
        AuthTokenDAO authTokenDAO = new AuthTokenDAO(connection);
        int nonce = Nonce.getInstance().next();
        AuthToken authToken = new AuthToken("token" + nonce, request.username);
        try{
            authTokenDAO.insert(authToken);
        }
        catch (DataAccessException exception){
            exception.printStackTrace();
            System.out.println("Issue with creating auth token");
            result = new LoginResult("Error: Issue with creating auth token", false);
            return;
        }
        result = new LoginResult(authToken.getAuthtoken(), foundUser.getUsername(), foundUser.getPersonID(), true);
    }

    public LoginResult getResult(){
        return result;
    }
}
