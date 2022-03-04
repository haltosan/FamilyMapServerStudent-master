package Request;

import Model.Event;
import Model.Person;
import Model.User;

public class LoadRequest extends Request{

    public User[] users;
    public Person[] persons;
    public Event[] events;

    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }
}
