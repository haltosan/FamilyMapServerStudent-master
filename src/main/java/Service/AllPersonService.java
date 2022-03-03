package Service;

import DataAccess.DataAccessException;
import DataAccess.PersonDAO;
import Model.Person;
import Result.AllPersonResult;

import java.sql.Connection;

public class AllPersonService extends Service{

    private final Connection connection;
    private final String associatedUsername;
    private AllPersonResult result;

    public AllPersonService(Connection connection, String associatedUsername) {
        this.connection = connection;
        this.associatedUsername = associatedUsername;
    }

    @Override
    public void execute() {
        PersonDAO dao = new PersonDAO(connection);
        Person[] people;
        try {
            people = dao.findAll(associatedUsername);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            result = new AllPersonResult("FindAll failed", false);
            return;
        }

        result = new AllPersonResult(people, true);
    }

    public AllPersonResult getResult() {
        return result;
    }
}
