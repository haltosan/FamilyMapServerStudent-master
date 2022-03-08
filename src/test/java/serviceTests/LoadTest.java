package serviceTests;

import Model.Event;
import Model.Person;
import Model.User;
import Result.LoadResult;
import Service.LoadService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoadTest extends ServiceTest{

    @Test
    public void positive(){
        User u1 = new User("other user", "passwd", "e", "e", "e", "m", "u1");
        User[] users = {user, u1};

        Person p1 = new Person("p1", "user", "e", "e","m",null, null, null);
        Person p2 = new Person("p2", "user", "e", "e","m",null, null, null);
        Person p3 = new Person("p3", "other user", "e", "e","m",null, null, null);
        Person[] people = {p1, p2, p3};

        Event e1 = new Event("e1", "user", "p1", 100, 100, "c", "c", "c", 100);
        Event e2 = new Event("e2", "other user", "p2", 100, 100, "c", "c", "c", 100);
        Event e3 = new Event("e3", "other user", "p3", 100, 100, "c", "c", "c", 100);
        Event[] events = {e1, e2, e3};
        assertDoesNotThrow(()-> loadService = new LoadService(db.getConnection(), users, people, events));
        loadService.execute();
        assertTrue(loadService.getResult().success);
    }

    @Test
    public void negative(){
        Person p1 = new Person("p1", "user", "e", "e","m",null, null, null);
        Person p2 = new Person("p1", "user", "e", "e","m",null, null, null);
        Person p3 = new Person("p2", "other user", "e", "e","m",null, null, null);
        Person[] people = {p1, p2, p3};
        assertDoesNotThrow(()-> loadService = new LoadService(db.getConnection(), new User[]{}, people, new Event[]{}));
        assertDoesNotThrow(()-> loadService.execute());
        LoadResult result = loadService.getResult();
        assertFalse(result.success);
    }
}
