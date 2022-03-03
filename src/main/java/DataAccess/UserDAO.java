package DataAccess;

import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 * The data access object for getting users
 */
public class UserDAO extends DataAccess {

    /**
     *
     * @param connection Database connect
     */
    public UserDAO(Connection connection) {
        super(connection);
        tableName = "User";
    }

    public void insert(User user) throws DataAccessException{
        String sql = "INSERT INTO " + tableName + " (username, password, email, firstName, lastName, gender, personID) VALUES(?,?,?,?,?,?,?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.setString(6, user.getGender());
            statement.setString(7, user.getPersonID());

            statement.executeUpdate();
        }
        catch(SQLException exception){
            exception.printStackTrace();
            throw new DataAccessException("Issue with User insert");
        }
    }

    public User find(String username, String password) throws DataAccessException {
        String sql = "SELECT * FROM " + tableName + " WHERE username = ? AND password = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return new User(resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("email"), resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("gender"), resultSet.getString("personID"));
            }
            else{
                return null;
            }
        }
        catch(SQLException exception){
            exception.printStackTrace();
            throw new DataAccessException("Issue in User username/password find");
        }
    }

    public User find(String personID) throws DataAccessException{
        String sql = "SELECT * FROM User WHERE personID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, personID);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return new User(resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("email"), resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("gender"), resultSet.getString("personID"));
            }
            else{
                return null;
            }
        }
        catch(SQLException exception){
            exception.printStackTrace();
            throw new DataAccessException("Issue in User personID find");
        }
    }

    public User findFromUsername(String username) throws DataAccessException {
        String sql = "SELECT * FROM User WHERE username = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return new User(resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("email"), resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("gender"), resultSet.getString("personID"));
            }
            else{
                return null;
            }
        }
        catch(SQLException exception){
            exception.printStackTrace();
            throw new DataAccessException("Issue in User username find");
        }
    }

}
