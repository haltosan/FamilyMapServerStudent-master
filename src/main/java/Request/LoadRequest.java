package Request;

import Model.Event;
import Model.Person;
import Model.User;

public class LoadRequest extends Request{

    User[] users;
    Person[] persons;
    Event[] events;

    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }
}
