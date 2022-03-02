package DataAccess;

import Model.AuthToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The data access object for getting authTokens
 */
public class AuthTokenDAO extends DataAccess {

    public void addStuff(Connection connection) throws DataAccessException {
        AuthTokenDAO dao = new AuthTokenDAO(connection);
        dao.insert(new AuthToken("a1", "p1"));
        System.out.println(dao.find("p1").getAuthtoken());
    }

    /**
     *
     * @param connection db connection
     */
    public AuthTokenDAO(Connection connection) {
        super(connection);
        tableName = "authToken";
    }

    public AuthToken find(String username) throws DataAccessException{
        String sql = "SELECT * FROM " + tableName + " WHERE username = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                return new AuthToken(result.getString("authtoken"), result.getString("username"));
            }
            else{
                return null;
            }
        }
        catch (SQLException exception){
            exception.printStackTrace();
            throw new DataAccessException("Issue in find authToken");
        }
    }

    public AuthToken findFromToken(String authToken) throws DataAccessException{
        String sql = "SELECT * FROM " + tableName + " WHERE authtoken = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, authToken);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                return new AuthToken(result.getString("authtoken"), result.getString("username"));
            }
            else{
                return null;
            }
        }
        catch (SQLException exception){
            exception.printStackTrace();
            throw new DataAccessException("Issue in findFromToken authToken");
        }
    }

    public void insert(AuthToken token) throws DataAccessException{
        String sql = "INSERT INTO " + tableName + "(authtoken, username) VALUES(?,?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, token.getAuthtoken());
            statement.setString(2, token.getUsername());

            statement.executeUpdate();
        }
        catch (SQLException exception){
            exception.printStackTrace();
            throw new DataAccessException("Issue with authToken insert");
        }
    }
}
