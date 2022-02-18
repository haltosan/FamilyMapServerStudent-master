package DataAccess;

import Model.Event;

import java.sql.*;

/**
 * The data access object for getting events
 */
public class EventDAO extends DataAccess {

    public static void main(String[] args) throws DataAccessException {
        Database db = new Database();
        EventDAO dao = new EventDAO(db.getConnection());
        Event event1 = new Event("1", "a", "u1", 10, 11, "c1", "ci1", "basic", 1910);
        Event event2 = new Event("2", "b", "u1", 20, 21, "c2", "ci1", "basic", 1920);
        Event event3 = new Event("1", "c", "u1", 10, 11, "c1", "ci1", "basic", 1910);

        dao.clear();
        db.closeConnection(true);
    }

    /**
     *
     * @param connection The database connection
     */
    public EventDAO(Connection connection) {
        super(connection);
        tableName = "Event";
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
            throw new DataAccessException("Issue with Event insert");
        }
    }

    public Event find(String eventID) throws DataAccessException{
        String sql = "Select * FROM Event WHERE eventID = ?";
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
            throw new DataAccessException("Issue in find Event");
        }
    }

//    @Override
//    public void clear() throws DataAccessException {
//        String sql = "DELETE FROM Events";
//        try(PreparedStatement statement = connection.prepareStatement(sql)){
//            statement.executeUpdate();
//        } catch (SQLException exception){
//            exception.printStackTrace();
//            throw new DataAccessException("Issue with clearing Events");
//        }
//    }

}
