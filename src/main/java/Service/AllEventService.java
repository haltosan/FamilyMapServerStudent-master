package Service;

import DataAccess.DataAccessException;
import DataAccess.EventDAO;
import Model.Event;
import Result.AllEventResult;

import java.sql.Connection;

public class AllEventService extends Service{

    private final Connection connection;
    private final String associatedUsername;
    private AllEventResult result;

    public AllEventService(Connection connection, String associatedUsername) {
        this.connection = connection;
        this.associatedUsername = associatedUsername;
    }

    @Override
    public void execute() {
        EventDAO eventDAO = new EventDAO(connection);
        Event[] events;
        try {
            events = eventDAO.findAll(associatedUsername);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            result = new AllEventResult("Service faulted", false);
            return;
        }

        result = new AllEventResult(events, true);
    }

    public AllEventResult getResult() {
        return result;
    }
}
