package DataAccess;

import Model.AuthToken;

import java.sql.Connection;

/**
 * The data access object for getting authTokens
 */
public class AuthTokenDAO extends DataAccess {


    /**
     *
     * @param connection db connection
     */
    public AuthTokenDAO(Connection connection) {
        super(connection);
        tableName = "authToken";
    }


}
