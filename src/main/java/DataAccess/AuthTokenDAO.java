package DataAccess;

import Model.AuthToken;

/**
 * The data access object for getting authTokens
 */
public class AuthTokenDAO extends DataAccess {

    private String username;
    private AuthToken model;

    /**
     *
     * @param username The username the DAO will fetch from the db
     */
    public AuthTokenDAO(String username) {
        this.username = username;
        model = null;
    }

    @Override
    public AuthToken getModel() {
        return model;
    }

    /**
     * Makes a request to the database to get an authtoken object with the same username
     */
    @Override
    public void makeRequest() {

    }
}
