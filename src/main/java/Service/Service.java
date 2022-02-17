package Service;

/**
 * The active part of a request -> response interaction. This manages the various backend functions
 */
public abstract class Service {


    /**
     * Responds to request that the object contains.
     */
    public abstract void execute();
}
