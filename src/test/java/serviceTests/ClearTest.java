package serviceTests;

import Model.Event;
import Model.Person;
import Model.User;
import Service.ClearService;
import Service.LoadService;
import Service.PersonService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClearTest extends ServiceTest{

    @Test
    public void positive(){
        assertDoesNotThrow(() -> clearService = new ClearService(null, db.getConnection()));
        clearService.execute();
        assertTrue(clearService.getResult().success);
    }

    @Test
    public void positive1(){
        Person[] people = {person};
        assertDoesNotThrow(()->loadService = new LoadService(db.getConnection(), new User[]{}, people, new Event[]{}));
        loadService.execute();

        assertDoesNotThrow(() -> clearService = new ClearService(null, db.getConnection()));
        clearService.execute();
        assertTrue(clearService.getResult().success);

        assertDoesNotThrow(() -> personService = new PersonService(db.getConnection(), "bobID", "user"));
        personService.execute();
        assertNull(personService.getResult());
    }
}
