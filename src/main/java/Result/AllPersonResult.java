package Result;

import Model.Person;

public class AllPersonResult extends Result{

    Person[] data;

    /**
     * @param message Message
     * @param success Success
     */
    public AllPersonResult(String message, boolean success) {
        super(message, success);
    }

    public AllPersonResult(Person[] data, boolean success){
        super(null, success);
        this.data = data;
    }
}
