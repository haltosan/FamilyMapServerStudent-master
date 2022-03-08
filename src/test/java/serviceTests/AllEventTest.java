package serviceTests;

import Model.Event;
import Model.Person;
import Model.User;
import Result.AllEventResult;
import Service.AllEventService;
import Service.LoadService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AllEventTest extends ServiceTest{

    @Test
    public void positive(){
        Event e1 = new Event("e1", "user", "pid", 100, 100, "country", "country", "type a", 0);
        Event e2 = new Event("e2", "user", "pid", 100, 100, "country", "country", "type a", 0);
        Event e3 = new Event("e3", "user", "pid", 100, 100, "country", "country", "type a", 0);
        Event[] events = {e1, e2, e3};
        assertDoesNotThrow(() -> loadService = new LoadService(db.getConnection(), new User[]{}, new Person[]{}, events));
        loadService.execute();

        assertDoesNotThrow(() ->allEventService = new AllEventService(db.getConnection(), "user"));
        allEventService.execute();
        AllEventResult result = allEventService.getResult();
        assertEquals(e1, result.data[0]);
        assertEquals(e2, result.data[1]);
        assertEquals(e3, result.data[2]);
    }

    @Test
    public void negative(){
        Event e1 = new Event("e1", "user", "pid", 100, 100, "country", "country", "type a", 0);
        Event e2 = new Event("e2", "user", "pid", 100, 100, "country", "country", "type a", 0);
        Event e3 = new Event("e3", "user", "pid", 100, 100, "country", "country", "type a", 0);
        Event[] events = {e1, e2, e3};
        assertDoesNotThrow(() -> loadService = new LoadService(db.getConnection(), new User[]{}, new Person[]{}, events));
        loadService.execute();

        assertDoesNotThrow(() ->allEventService = new AllEventService(db.getConnection(), "not user"));
        allEventService.execute();
        assertNull(allEventService.getResult().data);
    }
}
