import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import Model.Event;

import java.sql.Connection;


public class EventDAOTest {
    private Database db;
    private Event testEvent;
    private EventDAO testDAO;

    @BeforeEach
    public void setUp() throws DataAccessException{
        db = new Database();
        testEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9, 140.1, "Japan", "Ushiku",
                "Biking_Around", 2016);
        Connection connection = db.getConnection();
        testDAO = new EventDAO(connection);
        testDAO.clear();
    }

    @AfterEach
    public void tearDown(){
        db.closeConnection(false); //don't commit changes
    }

    @Test
    public void insertPositive() throws DataAccessException{
        testDAO.insert(testEvent);
        Event compareEvent = testDAO.find(testEvent.getEventID());
        System.out.println(compareEvent.getEventID());
        System.out.println(testEvent.getEventID());
        assertNotNull(compareEvent);
        assertEquals(testEvent, compareEvent);
    }

    @Test
    public void insertNegative() throws DataAccessException{
        testDAO.insert(testEvent);
        assertThrows(DataAccessException.class, () -> testDAO.insert(testEvent));
    }

}
