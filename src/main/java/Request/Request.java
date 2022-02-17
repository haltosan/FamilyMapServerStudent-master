package Request;

import Result.Result;

/**
 * A request object that is generated from a client's http request. This is sent from the client
 */
public abstract class Request {

    protected String json;

    /**
     *
     * @param json The json that was sent from the client
     */
    public Request(String json) {
        this.json = json;
    }

    /**
     * Generates the appropriate result (response) for a client request
     * @return The result object to be sent to the client
     */
    public abstract Result getResult();
}
