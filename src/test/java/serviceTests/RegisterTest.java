package serviceTests;

import static org.junit.jupiter.api.Assertions.*;

import DataAccess.DataAccessException;
import Request.RegisterRequest;
import Result.RegisterResult;
import Service.RegisterService;
import org.junit.jupiter.api.Test;

public class RegisterTest extends ServiceTest{

    @Test
    public void positive(){
        assertDoesNotThrow(() -> registerService = new RegisterService(new RegisterRequest("bob", "passwd", "me@ail.com", "Bob", "Senger", "m"), db.getConnection()));
        registerService.execute();
        RegisterResult result = registerService.getResult();
        assertNotNull(result);
        assertTrue(result.success);
    }

    @Test
    public void negative(){
        registerService.execute();
        assertTrue(registerService.getResult().success);
        registerService.execute(); //re-register the same user
        assertFalse(registerService.getResult().success);
    }
}
