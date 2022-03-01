package Result;

/**
 * A result object that is made in response to a request object. This is sent to the client
 */
public abstract class Result {

    public String message;
    public boolean success;

    /**
     * @param message Message
     * @param success Success
     */
    public Result(String message, boolean success){
        this.message = message;
        this.success = success;
    }

}
