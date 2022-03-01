package Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.UserDAO;
import Model.AuthToken;
import Model.User;
import Request.RegisterRequest;
import Result.RegisterResult;

import java.sql.Connection;

/**
 * Registers a new user in the db
 */
public class RegisterService extends Service {

    private final RegisterRequest request;
    private RegisterResult result;

    /**
     * @param request The request that the user sent
     */
    public RegisterService(RegisterRequest request, Connection connection) {
        this.request = request;
        this.connection = connection;
        result = null;
    }

    /**
     * Register a new user given the request from the client
     */
    @Override
    public void execute() {
        //add user to database
        String personID = "person" + Nonce.next();
        User user = new User(request.username, request.password, request.email, request.firstName, request.lastName, request.gender, personID);
        UserDAO userDAO = new UserDAO(connection);
        AuthTokenDAO authTokenDAO = new AuthTokenDAO(connection);
        try {
            userDAO.insert(user);
        }
        catch (DataAccessException exception){
            exception.printStackTrace();
            System.out.println("Issue with adding user");
            result = new RegisterResult("Error: Issue with adding user", false);
            return;
        }
        //add auth token
        String authTokenString = "token" + Nonce.next();
        AuthToken authToken = new AuthToken(authTokenString, request.username);
        try{
            authTokenDAO.insert(authToken);
        }
        catch (DataAccessException exception){
            exception.printStackTrace();
            System.out.println("Issue with adding auth token");
            result = new RegisterResult("Error: Issue with adding auth token", false);
            return;
        }
        result = new RegisterResult(authTokenString, request.username, personID, true);
    }

    public RegisterResult getResult(){
        return result;
    }
}
