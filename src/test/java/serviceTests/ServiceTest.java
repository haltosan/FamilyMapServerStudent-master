package serviceTests;

import DataAccess.DataAccessException;
import DataAccess.Database;
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
    protected FillService fillService;
    protected LoadService loadService;
    protected LoginService loginService;
    protected PersonService personService;
    protected RegisterService registerService;

    @BeforeEach
    public void setup() throws DataAccessException {
        db = new Database();
        String username = "bob";
        String password = "passwd";
        String personID = "person0";
        allEventService = new AllEventService(db.getConnection(), username);
        allPersonService = new AllPersonService(db.getConnection(), username);
        clearService = new ClearService(null, db.getConnection());
        fillService = new FillService(db.getConnection(), username, 4);
        loadService = new LoadService(db.getConnection(), null, null, null);
        loginService = new LoginService(new LoginRequest(username, password), db.getConnection());
        personService = new PersonService(db.getConnection(), personID, username);
        registerService = new RegisterService(new RegisterRequest(username, password, "mail@mail.com", "Bobert", "Whale", "m"), db.getConnection());
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false); //don't commit changes
    }
}
