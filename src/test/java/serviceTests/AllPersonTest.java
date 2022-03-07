package serviceTests;

import DataAccess.DataAccessException;
import Model.Event;
import Model.Person;
import Model.User;
import Result.AllPersonResult;
import Service.AllPersonService;
import Service.LoadService;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class AllPersonTest extends ServiceTest{

    @Test
    public void positive(){
        assertDoesNotThrow(()->loadService = new LoadService(db.getConnection(), new User[]{}, new Person[]{person}, new Event[]{}));
        loadService.execute();

        assertDoesNotThrow(()->allPersonService = new AllPersonService(db.getConnection(), "user"));
        allPersonService.execute();
        AllPersonResult result = allPersonService.getResult();
        assertEquals(person, result.data[0], Arrays.toString(result.data));
    }

    @Test
    public void negative(){
        assertDoesNotThrow(()->loadService = new LoadService(db.getConnection(), new User[]{}, new Person[]{person}, new Event[]{}));
        loadService.execute();

        assertDoesNotThrow(()->allPersonService = new AllPersonService(db.getConnection(), "not user"));
        allPersonService.execute();
        assertNull(allPersonService.getResult().data);
    }
}
