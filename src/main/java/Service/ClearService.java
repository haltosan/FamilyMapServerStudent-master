package Service;

import DataAccess.*;
import Model.Event;
import Request.ClearRequest;
import Result.ClearResult;

import java.net.HttpURLConnection;
import java.sql.Connection;

/**
 * Clears the database
 */
public class ClearService extends Service{

    private final ClearRequest request;
    private ClearResult result;
    private final String username;

    /**
     * @param request The request that the user sent
     */
    public ClearService(ClearRequest request, Connection connection) {
        this.request = request;
        this.connection = connection;
        result = null;
        username = null;
    }

    public ClearService(String username, ClearRequest request, Connection connection){
        this.request = request;
        this.username = username;
        this.connection = connection;
    }

    /**
     * Clears the table as specified in the request
     */
    @Override
    public void execute() {
        try {
            if(username != null){
                AuthTokenDAO authTokenDAO = new AuthTokenDAO(connection);
                authTokenDAO.clear(username);

                EventDAO eventDAO = new EventDAO(connection);
                eventDAO.clear(username);

                PersonDAO personDAO = new PersonDAO(connection);
                personDAO.clear(username);

                UserDAO userDAO = new UserDAO(connection);
                userDAO.clear(username);
            }
            else {
                DataAccess[] tables = {new AuthTokenDAO(connection), new EventDAO(connection), new PersonDAO(connection),
                        new UserDAO(connection)};
                for (DataAccess table : tables) {
                    table.clear();
                }
            }
            result = new ClearResult("Clear succeeded.", true);
        }
        catch (DataAccessException exception){
            exception.printStackTrace();
            System.out.println("Clear service failed");
            result = new ClearResult("Error: Database access has failed in clear", false);
        }
    }

    public ClearResult getResult(){
        return result;
    }
}
