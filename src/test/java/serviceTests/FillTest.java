package serviceTests;

import DataAccess.DataAccessException;
import Model.Event;
import Model.Person;
import Model.User;
import Service.FillService;
import Service.LoadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class FillTest extends ServiceTest{

    @BeforeEach
    public void setup() throws DataAccessException {
        super.setup();
        User[] users = {new User("user", "passwd", "mail.com", "Bobert", "Whale", "m", "bobID")};
        loadService = new LoadService(db.getConnection(), users, new Person[]{}, new Event[]{});
        loadService.execute();
        db.closeConnection(true);
    }

    @Test
    public void positive(){
        assertDoesNotThrow(() ->fillService = new FillService(db.getConnection(), "user", 2));
        fillService.execute();
        String message = fillService.getResult().message;
        assertEquals("Successfully added 7 persons and 19 events to the database.", message, message);
    }

    @Test
    public void positive2(){
        assertDoesNotThrow(() ->fillService = new FillService(db.getConnection(), "user", 4));
        fillService.execute();
        String message = fillService.getResult().message;
        assertEquals("Successfully added 31 persons and 91 events to the database.", message, message);
    }

    @Test
    public void negative() {
        assertDoesNotThrow(() ->fillService = new FillService(db.getConnection(), "not user", 4));
        fillService.execute();
        String message = fillService.getResult().message;
        assertEquals("User not found", message, message);

    }
}
