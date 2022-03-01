package Result;

/**
 * A result object that is made in response to a request object. This is sent to the client
 */
public abstract class Result {

    protected String json;
    protected int code;

    /**
     * @param code The http status code to return to the user
     * @param json The json string to return to the client
     */
    public Result(int code, String json){
        this.code = code;
        this.json = json;
    }

    public String getJson(){
        return json;
    }
    public int getCode(){
        return code;
    }

}
