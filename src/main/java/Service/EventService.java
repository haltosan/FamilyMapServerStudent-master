package Service;

import DataAccess.DataAccessException;
import DataAccess.EventDAO;
import Model.Event;
import Result.EventResult;

import java.sql.Connection;

public class EventService extends Service{

    private final Connection connection;
    private final String eventID;
    private final String associatedUsername;
    private EventResult result;

    public EventService(Connection connection, String eventID, String associatedUsername) {
        this.connection = connection;
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
    }

    @Override
    public void execute() {
        EventDAO dao = new EventDAO(connection);
        Event foundEvent;
        try {
            foundEvent = dao.find(eventID);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            result = new EventResult("Dao failed", false);
            return;
        }
        if(!foundEvent.getAssociatedUsername().equalsIgnoreCase(associatedUsername)){
            result = new EventResult("Not associated with username", false);
            return;
        }
        result = new EventResult(foundEvent.getAssociatedUsername(), foundEvent.getEventID(), foundEvent.getPersonID(), foundEvent.getCountry(), foundEvent.getCity(), foundEvent.getEventType(), foundEvent.getLatitude(), foundEvent.getLongitude(), foundEvent.getYear(), true);
    }

    public EventResult getResult(){
        return result;
    }
}
