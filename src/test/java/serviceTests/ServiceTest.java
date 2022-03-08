package serviceTests;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.Person;
import Model.User;
import Request.LoginRequest;
import Request.RegisterRequest;
import Service.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {

    protected Database db;
    protected AllEventService allEventService;
    protected AllPersonService allPersonService;
    protected ClearService clearService;
    protected EventService eventService;
    protected FillService fillService;
    protected LoadService loadService;
    protected LoginService loginService;
    protected PersonService personService;
    protected RegisterService registerService;

    protected User user;
    protected Person person;

    @BeforeEach
    public void setup() throws DataAccessException {
        db = new Database();
        String username = "user";
        String password = "passwd";
        String userID = "userID";
        user = new User(username, password, "mail", "Bobert", "Whale", "m", userID);
        person = new Person("bobID", "user", "Bobert", "Whale", "m", null, null, null);
        allEventService = new AllEventService(db.getConnection(), username);
        allPersonService = new AllPersonService(db.getConnection(), username);
        clearService = new ClearService(null, db.getConnection());
        eventService = new EventService(db.getConnection(), "event1", username);
        fillService = new FillService(db.getConnection(), username, 4);
        loadService = new LoadService(db.getConnection(), null, null, null);
        loginService = new LoginService(new LoginRequest(username, password), db.getConnection());
        personService = new PersonService(db.getConnection(), userID, username);
        registerService = new RegisterService(new RegisterRequest(username, password, "mail@mail.com", "Bobert", "Whale", "m"), db.getConnection());
        clearService.execute();
        db.closeConnection(true);
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false); //don't commit changes
    }
}
