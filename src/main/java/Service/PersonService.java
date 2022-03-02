package Service;

import DataAccess.DataAccessException;
import DataAccess.PersonDAO;
import Handler.HandlerUtils;
import Model.Person;
import Result.PersonResult;

import java.sql.Connection;

public class PersonService extends Service{

    private final Connection connection;
    private final String personID;
    private final String associatedUsername;
    private PersonResult result;

    public PersonService(Connection connection, String personID, String associatedUsername){
        this.connection = connection;
        this.personID = personID;
        this.associatedUsername = associatedUsername;
    }

    @Override
    public void execute() {
        PersonDAO dao = new PersonDAO(connection);
        Person foundPerson;
        try {
            foundPerson = dao.find(personID);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            result = new PersonResult("Database issue", false);
            return;
        }

        if(foundPerson == null){
            result = null;
            return;
        }

        if(!foundPerson.getAssociatedUsername().equalsIgnoreCase(associatedUsername)){
            result = new PersonResult("Requested person does not belong to this user", false);
            return;
        }
        result = new PersonResult(foundPerson.getAssociatedUsername(), foundPerson.getPersonID(), foundPerson.getFirstName(), foundPerson.getLastName(), foundPerson.getGender(), foundPerson.getFatherID(), foundPerson.getMotherID(), foundPerson.getSpouseID(), true);

    }

    public PersonResult getResult(){
        return result;
    }
}
