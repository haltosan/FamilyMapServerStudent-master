package serviceTests;

import Model.Event;
import Model.Person;
import Model.User;
import Request.LoginRequest;
import Result.LoginResult;
import Service.LoadService;
import Service.LoginService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends ServiceTest{

    @Test
    public void positive(){
        assertDoesNotThrow(()->loadService = new LoadService(db.getConnection(), new User[]{user}, new Person[]{}, new Event[]{}));
        loadService.execute();

        LoginRequest request = new LoginRequest("user", "passwd");
        assertDoesNotThrow(()->loginService = new LoginService(request, db.getConnection()));
        loginService.execute();
        LoginResult result = loginService.getResult();
        assertTrue(result.success);
        assertNotNull(result.authtoken);
    }

    @Test
    public void negative(){
        assertDoesNotThrow(()->loadService = new LoadService(db.getConnection(), new User[]{user}, new Person[]{}, new Event[]{}));
        loadService.execute();

        LoginRequest request = new LoginRequest("user", "not passwd");
        assertDoesNotThrow(()->loginService = new LoginService(request, db.getConnection()));
        loginService.execute();
        LoginResult result = loginService.getResult();
        assertFalse(result.success);
        assertNull(result.authtoken);
    }
}
