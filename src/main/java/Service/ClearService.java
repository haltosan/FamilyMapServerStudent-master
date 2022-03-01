package Service;

import DataAccess.*;
import Request.ClearRequest;
import Result.ClearResult;

import java.sql.Connection;

/**
 * Clears the database
 */
public class ClearService extends Service{

    private final ClearRequest request;
    private ClearResult result;

    /**
     * @param request The request that the user sent
     */
    public ClearService(ClearRequest request, Connection connection) {
        this.request = request;
        this.connection = connection;
        result = null;
    }

    /**
     * Clears the table as specified in the request
     */
    @Override
    public void execute() {
        if(request == null){
            return;
        }
        try {
            DataAccess[] tables = {new AuthTokenDAO(connection), new EventDAO(connection), new PersonDAO(connection),
                                    new UserDAO(connection)};
            for(DataAccess table : tables){
                table.clear();
            }

        }
        catch (DataAccessException exception){
            exception.printStackTrace();
            System.out.println("Clear service failed");
            result = null;
        }
    }

    public ClearResult getResult(){
        return result;
    }
}
