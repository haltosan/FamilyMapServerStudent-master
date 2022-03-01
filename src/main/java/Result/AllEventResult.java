package Result;

import Model.Event;

public class AllEventResult extends Result{

    Event[] data;

    /**
     * @param message Message
     * @param success Success
     */
    public AllEventResult(String message, boolean success) {
        super(message, success);
    }

    public AllEventResult(Event[] data, boolean success) {
        super(null, success);
        this.data = data;
    }
}
