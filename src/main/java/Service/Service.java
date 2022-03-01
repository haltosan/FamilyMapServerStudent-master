package Service;

import DataAccess.Database;

import java.sql.Connection;

/**
 * The active part of a request -> response interaction. This manages the various backend functions
 */
public abstract class Service {

    protected Connection connection;

    /**
     * Responds to request that the object contains.
     */
    public abstract void execute();
}
