import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import Model.User;
import DataAccess.UserDAO;
import DataAccess.Database;
import DataAccess.DataAccessException;


public class UserDAOTest {
    private Database db;
    private User testUser;
    private UserDAO testDAO;

    @BeforeEach
    public void setUp() throws DataAccessException{
        db = new Database();
        testUser = new User("p1", "passwd", "p@mail.com", "Person", "one", "m", "p1");
        testDAO = new UserDAO(db.getConnection());
        testDAO.clear();
    }

    @AfterEach
    public void tearDown(){
        db.closeConnection(false); //don't commit changes
    }

    @Test
    public void insertPositive(){
        User testUser2 = new User("p2", "p@$$w0rd", "p2@mail.co", "Person", "two", "f", "p2");
        try{
            testDAO.insert(testUser);
            testDAO.insert(testUser2);
        }
        catch (DataAccessException exception){
            exception.printStackTrace();
            fail();
        }
    }

    @Test
    public void insertNegative(){
        User nullUser = new User("p3", "passwd", "m@ai.l", null, null, "m", "p4");
        assertDoesNotThrow(() -> testDAO.insert(testUser));
        assertThrows(DataAccessException.class, () -> testDAO.insert(testUser));
        assertThrows(DataAccessException.class, () -> testDAO.insert(nullUser));
    }

    @Test
    public void findPositive(){
        try {
            testDAO.insert(testUser);
            assertEquals(testUser, testDAO.find(testUser.getPersonID()));
            assertEquals(testUser, testDAO.find(testUser.getUsername(), testUser.getPassword()));
        }
        catch (DataAccessException exception){
            exception.printStackTrace();
            fail();
        }
    }

    @Test
    public void findNegative(){
        try{
            assertNull(testDAO.find("p1"));
            assertNull(testDAO.find("p1", "passwd"));
        }
        catch (DataAccessException exception){
            exception.printStackTrace();
            fail();
        }
    }

    @Test
    public void clearPositive(){
        try {
            testDAO.insert(testUser);
            testDAO.clear();
            assertNull(testDAO.find("p1"));
        }
        catch (DataAccessException e){
            fail();
        }
    }
}
