package serviceTests;

import DataAccess.DataAccessException;
import Model.Event;
import Model.Person;
import Model.User;
import Result.PersonResult;
import Service.LoadService;
import Service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest extends ServiceTest{

    @BeforeEach
    public void setup() throws DataAccessException {
        super.setup();
        Person[] people = {new Person("billyID", "user", "Billy", "Bob", "m", null, null, null),
                new Person("joeID", "user", "Joe", "Bob", "m", null, null, null)};
        loadService = new LoadService(db.getConnection(), new User[]{}, people, new Event[]{});
        loadService.execute();
        db.closeConnection(true);
    }

    @Test
    public void positive(){
        assertDoesNotThrow(() ->personService = new PersonService(db.getConnection(), "billyID", "user"));
        personService.execute();
        PersonResult result = personService.getResult();
        assertTrue(result.success);
        assertEquals("Billy", result.firstName);

        assertDoesNotThrow(() ->personService = new PersonService(db.getConnection(), "joeID", "user"));
        personService.execute();
        result = personService.getResult();
        assertTrue(result.success);
        assertEquals("m", result.gender);
    }

    @Test
    public void negative(){
        assertDoesNotThrow(() ->personService = new PersonService(db.getConnection(), "billyID", "not user"));
        personService.execute();
        PersonResult result = personService.getResult();
        assertFalse(result.success);
    }
}
