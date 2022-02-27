package passoff;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Model.AuthToken;
import Model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;


public class AuthTokenDAOTest {
    private Database db;
    private AuthToken testToken;
    private AuthTokenDAO testDAO;

    @BeforeEach
    public void setUp() throws DataAccessException{
        db = new Database();
        testToken = new AuthToken("123", "p1");
        Connection connection = db.getConnection();
        testDAO = new AuthTokenDAO(connection);
        testDAO.clear();
    }

    @AfterEach
    public void tearDown(){
        db.closeConnection(false); //don't commit changes
    }

    @Test
    public void insertPositive() throws DataAccessException{
        testDAO.insert(testToken);
        AuthToken compareToken = testDAO.find(testToken.getUsername());
        assertNotNull(compareToken);
        assertEquals(testToken, compareToken);
    }

}
