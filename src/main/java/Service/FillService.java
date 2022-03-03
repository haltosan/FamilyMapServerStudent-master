package Service;

import DataAccess.DataAccessException;
import DataAccess.EventDAO;
import DataAccess.PersonDAO;
import DataAccess.UserDAO;
import Model.Event;
import Model.Person;
import Model.User;
import Request.FillRequest;
import Result.FillResult;

import java.sql.Connection;
import java.util.Random;

/**
 * Fills the database with data
 */
public class FillService extends Service{

    private final FillRequest request;
    private final Connection connection;
    private final String username;
    private FillResult result;
    private final int generationCount;
    private Random random;

    public FillService(FillRequest request, Connection connection, String username, int generationCount) {
        this.request = request;
        this.connection = connection;
        this.username = username;
        this.generationCount = generationCount;
        random = new Random();
    }

    private String[] names = {"billy", "bob", "joe", "fred"};
    private String[] eventType = {"cake day party alone", "jail"};
    private double[][] locations = {{1,1},{2,2},{100, -100}};
    private String[] country = {"us", "us", "ireland", "ireland"};
    private String[] city = {"new york", "la", "on the street", "random pub"};

    /**
     * Fills the database as indicated in the request
     */
    @Override
    public void execute() {
        EventDAO eventDAO  = new EventDAO(connection);
        PersonDAO personDAO = new PersonDAO(connection);
        UserDAO userDAO = new UserDAO(connection);

        //make user's person obj and birth event
        User user;
        try {
            user = userDAO.findFromUsername(username);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            result = new FillResult("Getting user's id faulted", false);
            return;
        }

        try {
            personDAO.insert(new Person(user.getPersonID(), username, user.getFirstName(), user.getLastName(), user.getGender(), null, null, null));
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            result = new FillResult("Creating user's person faulted", false);
            return;
        }


    }

    private void makeBirth(String username, String personID, int birthYear){
        double[] location = newLongLat();
        String[] countryCity = newCountryCity();
        new Event("event" + Nonce.next(),username,personID,location[0],location[1],countryCity[0],countryCity[1],"birth",birthYear);
    }

    private void makeDeath(String username, String personID, int birthYear){
        double[] location = newLongLat();
        String[] countryCity = newCountryCity();
        new Event("event" + Nonce.next(),username,personID,location[0],location[1],countryCity[0],countryCity[1],"death",birthYear + 49);
    }

    private String newName(){
        return names[random.nextInt(names.length)];
    }
    private String newType(){
        return eventType[random.nextInt(eventType.length)];
    }
    private double[] newLongLat(){
        return locations[random.nextInt(locations.length)];
    }
    private String[] newCountryCity(){
        int index = random.nextInt(country.length);
        return new String[]{country[index], city[index]};
    }
}

/*
Each person, excluding the user, must have at least three events with the following types:
    birth, marriage, and death. They may have additional events as well, but we will only be checking for these three.
The user’s person object needs to have at least a birth event, and may have additional events, but we will only be checking for the birth event.
Parents must be born at least 13 years before their children.
Parents must be at least 13 years old when they are married.
Parents must not die before their child is born.
Women must not give birth when older than 50 years old.
Birth events must be the first event for a person chronologically.
Death events must be the last event for a person chronologically.
Nobody must die at an age older than 120 years old.
Each person in a married couple has their own marriage event. Each event will have a unique event ID,
    but both marriage events must have matching years and locations.
Event locations may be randomly selected, or you may try to make them more realistic
    (e.g., many people live their lives in a relatively small geographical area).

 */