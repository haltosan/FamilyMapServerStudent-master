package Service;

import DataAccess.DataAccessException;
import DataAccess.EventDAO;
import DataAccess.PersonDAO;
import DataAccess.UserDAO;
import Model.Event;
import Model.Person;
import Model.User;
import Result.LoadResult;

import java.sql.Connection;

public class LoadService extends Service{

    private final Connection connection;
    private final User[] users;
    private final Person[] people;
    private final Event[] events;
    private LoadResult result;

    public LoadService(Connection connection, User[] users, Person[] people, Event[] events) {
        this.connection = connection;
        this.users = users;
        this.people = people;
        this.events = events;
    }

    @Override
    public void execute() {
        UserDAO userDAO = new UserDAO(connection);
        PersonDAO personDAO = new PersonDAO(connection);
        EventDAO eventDAO = new EventDAO(connection);

        for(User user : users){
            try {
                userDAO.insert(user);
            }
            catch (DataAccessException exception){
                exception.printStackTrace();
                result = new LoadResult("Faulted on inserting user " + user.getPersonID(), false);
                return;
            }
        }

        for(Person person : people){
            try{
                personDAO.insert(person);
            }
            catch (DataAccessException exception){
                exception.printStackTrace();
                result = new LoadResult("Faulted on inserting person " + person.getPersonID(), false);
                return;
            }
        }

        for(Event event : events){
            try{
                eventDAO.insert(event);
            }
            catch (DataAccessException exception){
                exception.printStackTrace();
                result = new LoadResult("Faulted on inserting event " + event.getEventID(), false);
                return;
            }
        }

        result = new LoadResult("Successfully added " + users.length + " users, " + people.length + " persons, and " + events.length + " events", true);
    }

    public LoadResult getResult() {
        return result;
    }
}
