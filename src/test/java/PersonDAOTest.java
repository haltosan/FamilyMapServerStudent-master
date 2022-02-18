import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDAO;
import Model.Person;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;


public class PersonDAOTest {
    private Database db;
    private Person testPerson;
    private PersonDAO testDAO;

    @BeforeEach
    public void setup() throws DataAccessException{
        db = new Database();
        testPerson = new Person("p1", "p1", "Person", "one", "m", "f1", "m1", "p2");
        testDAO = new PersonDAO(db.getConnection());
        testDAO.clear();
    }

    @AfterEach
    public void tearDown(){
        db.closeConnection(false); //don't commit changes
    }

    @Test
    public void insertPositive(){
        Person testPerson2 = new Person("p2", "p2", "Person", "two", "f", "f2", "m2", "p1");
        Person testPerson3 = new Person("p3", "p3", "Person", "three", "m", "f2", "m2", "");
        try{
            testDAO.insert(testPerson);
            testDAO.insert(testPerson2);
            testDAO.insert(testPerson3);
        }
        catch (DataAccessException exception){
            fail();
        }
    }

    @Test
    public void insertNegative() {
        try {
            testDAO.insert(testPerson);
        }
        catch (DataAccessException exception){
            fail();
        }
        assertThrows(DataAccessException.class, () -> testDAO.insert(testPerson));
    }

    @Test
    public void findPositive(){
        try{
            testDAO.insert(testPerson);
            Person testPerson2 = new Person("p2", "p2", "Person", "two", "f", "f2", "m2", "p1");
            testDAO.insert(testPerson2);
            assertEquals(testPerson, testDAO.find(testPerson.getPersonID()));
            assertEquals(testPerson2, testDAO.find(testPerson2.getPersonID()));
        }
        catch (DataAccessException exception){
            fail();
        }
    }

    @Test
    public void findNegative(){
        try {
            testDAO.insert(testPerson);
            assertNull(testDAO.find("p2"));
        }
        catch (DataAccessException exception){
            fail();
        }
    }

    @Test
    public void clearPositive() throws DataAccessException {
        assertDoesNotThrow(() -> testDAO.clear());
        assertNull(testDAO.find(testPerson.getPersonID()));
    }
}
