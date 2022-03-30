package DataAccess;

import Model.Event;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * The data access object for getting events
 */
public class EventDAO extends DataAccess {

    /**
     *
     * @param connection The database connection
     */
    public EventDAO(Connection connection) {
        super(connection);
        tableName = "Event";
    }

    public void clear(String username) throws DataAccessException{
        String sql = "DELETE FROM " + tableName + " WHERE associatedUsername = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, username);
            statement.executeUpdate();
        } catch (SQLException exception){
            exception.printStackTrace();
            throw new DataAccessException("Issue with clearing " + tableName);
        }
    }

    public void insert(Event event) throws DataAccessException{
        String sql = "INSERT INTO " + tableName + " (EventID, AssociatedUsername, PersonID, Latitude, Longitude, Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, event.getEventID());
            statement.setString(2, event.getAssociatedUsername());
            statement.setString(3, event.getPersonID());
            statement.setDouble(4, event.getLatitude());
            statement.setDouble(5, event.getLongitude());
            statement.setString(6, event.getCountry());
            statement.setString(7, event.getCity());
            statement.setString(8, event.getEventType());
            statement.setInt(9, event.getYear());

            statement.executeUpdate();
        }
        catch (SQLException exception){
            exception.printStackTrace();
            throw new DataAccessException("Issue with Model.Event insert");
        }
    }

    public Event find(String eventID) throws DataAccessException{
        String sql = "SELECT * FROM " + tableName + " WHERE eventID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, eventID);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return new Event(resultSet.getString("eventID"), resultSet.getString("associatedUsername"),
                        resultSet.getString("personID"), resultSet.getDouble("latitude"),
                        resultSet.getDouble("longitude"),resultSet.getString("country"),
                        resultSet.getString("city"),resultSet.getString("eventType"),resultSet.getInt("year"));
            }
            else{
                return null;
            }
        } catch(SQLException exception){
            exception.printStackTrace();
            throw new DataAccessException("Issue in find Model.Event");
        }
    }

    public Event[] findAll(String associatedUsername) throws DataAccessException{
        String sql = "SELECT * FROM " + tableName + " WHERE associatedUsername = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, associatedUsername);
            ResultSet resultSet = statement.executeQuery();
            LinkedList<Event> events = new LinkedList<>();
            while(resultSet.next()){
                events.add(new Event(resultSet.getString("eventID"), resultSet.getString("associatedUsername"),
                        resultSet.getString("personID"), resultSet.getDouble("latitude"),
                        resultSet.getDouble("longitude"),resultSet.getString("country"),
                        resultSet.getString("city"),resultSet.getString("eventType"),resultSet.getInt("year")));
            }
            if(events.size() <= 0){
                return null;
            }
            return events.toArray(new Event[0]);
        }
        catch(SQLException exception){
            exception.printStackTrace();
            throw new DataAccessException("Issue in find Model.Event");
        }
    }

}
