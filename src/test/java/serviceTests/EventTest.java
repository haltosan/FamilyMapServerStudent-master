package serviceTests;

import Model.Event;
import Model.Person;
import Model.User;
import Result.EventResult;
import Service.EventService;
import Service.LoadService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest extends ServiceTest{

    @Test
    public void positive(){
        Event event = new Event("event1", "user", "userID", 100, 100, "country", "city", "misc", 2000);
        Event[] events = new Event[]{event};
        assertDoesNotThrow(()->loadService = new LoadService(db.getConnection(), new User[]{}, new Person[]{}, events));
        loadService.execute();

        assertDoesNotThrow(()-> eventService = new EventService(db.getConnection(), "event1", "user"));
        eventService.execute();
        EventResult result = eventService.getResult();
        assertTrue(result.success);
        assertEquals(event.getEventID(), result.eventID);
        assertEquals(event.getAssociatedUsername(), result.associatedUsername);
    }

    @Test
    public void negative(){
        Event event = new Event("event1", "user", "userID", 100, 100, "country", "city", "misc", 2000);
        Event[] events = new Event[]{event};
        assertDoesNotThrow(()->loadService = new LoadService(db.getConnection(), new User[]{}, new Person[]{}, events));
        loadService.execute();

        assertDoesNotThrow(()-> eventService = new EventService(db.getConnection(), "event1", "not user"));
        eventService.execute();
        EventResult result = eventService.getResult();
        assertFalse(result.success);
    }

}
