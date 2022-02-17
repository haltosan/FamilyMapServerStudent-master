package DataAccess;

import java.sql.*;

/**
 * This interacts with the database. This returns objects that represent the db info.
 */
public abstract class DataAccess {

    protected String tableName;
    protected final Connection connection;

    public DataAccess(Connection connection){
        this.connection = connection;
    }

    public void clear() throws DataAccessException{
        String sql = "DELETE FROM ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, tableName);
            statement.executeUpdate();
        } catch (SQLException exception){
            exception.printStackTrace();
            throw new DataAccessException("Issue with clearing " + tableName);
        }
    }
}
