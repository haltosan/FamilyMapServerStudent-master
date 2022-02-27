package DataAccess;

import Model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The data access object for getting persons
 */
public class PersonDAO extends DataAccess {

    /**
     *
     * @param connection Database connection
     */
    public PersonDAO(Connection connection) {
        super(connection);
        tableName = "Person";
    }

    public Person find(String personID) throws DataAccessException{
        String sql = "SELECT * FROM " + tableName + " WHERE personID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, personID);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                return new Person(result.getString("personID"), result.getString("associatedUsername"), result.getString("firstName"), result.getString("lastName"), result.getString("gender"), result.getString("fatherID"), result.getString("motherID"), result.getString("spouseID"));
            }
            else{
                return null;
            }
        }
        catch(SQLException exception){
            exception.printStackTrace();
            throw new DataAccessException("Issue in find Person");
        }
    }

    public void insert(Person person) throws DataAccessException{
        String sql = "INSERT INTO " + tableName + "(personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, person.getPersonID());
            statement.setString(2, person.getAssociatedUsername());
            statement.setString(3, person.getFirstName());
            statement.setString(4, person.getLastName());
            statement.setString(5, person.getGender());
            statement.setString(6, person.getFatherID());
            statement.setString(7, person.getMotherID());
            statement.setString(8, person.getSpouseID());

            statement.executeUpdate();
        }
        catch(SQLException exception){
            exception.printStackTrace();
            throw new DataAccessException("Issue with Person insert");
        }
    }
}
