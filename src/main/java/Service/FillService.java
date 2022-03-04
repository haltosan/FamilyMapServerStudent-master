package Service;

import DataAccess.DataAccessException;
import DataAccess.EventDAO;
import DataAccess.PersonDAO;
import DataAccess.UserDAO;
import Model.Event;
import Model.Person;
import Model.User;
import Result.FillResult;

import java.sql.Connection;
import java.util.*;

/**
 * Fills the database with data
 */
public class FillService extends Service{

    private final String username;
    private FillResult result;
    private final int generationCount;
    private final Random random;
    EventDAO eventDAO;
    PersonDAO personDAO;
    UserDAO userDAO;

    public FillService(Connection connection, String username, int generationCount) {
        this.username = username;
        this.generationCount = generationCount;
        random = new Random();
        eventDAO  = new EventDAO(connection);
        personDAO = new PersonDAO(connection);
        userDAO = new UserDAO(connection);
    }

    private String[] names = {"billy", "bob", "joe", "fred"};
    private String[] eventType = {"cake day party alone", "jail"};
    private double[][] locations = {{1,1},{2,2},{100, -100}};
    private String[] country = {"us", "us", "ireland", "ireland"};
    private String[] city = {"new york", "la", "on the street", "random pub"};
    private String[] genders = {"m","f"};

    /**
     * Fills the database as indicated in the request
     */
    @Override
    public void execute() {
        //make user's person obj and birth event
        User user;
        try {
            user = userDAO.findFromUsername(username);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            result = new FillResult("Getting user's id faulted", false);
            return;
        }

        if(user == null){
            result = new FillResult("User not found", true);
            return;
        }

        Person userPerson = new Person(user.getPersonID(), username, user.getFirstName(), user.getLastName(), user.getGender(), null, null, null);
        int userBirthYear = 2000;
        try {
            makeBirth(userPerson.getPersonID(), userBirthYear);
        } catch (DataAccessException exception) {
            exception.printStackTrace();
            result = new FillResult("Failed when giving user birth event", false);
            return;
        }

        //breadth first tree traversal! (I like these)
        Queue<Person> people = new ArrayDeque<>();
        Queue<Integer> birthYear = new ArrayDeque<>();
        people.add(userPerson);
        birthYear.add(userBirthYear);
        Queue<Person> donePeople = new ArrayDeque<>();
        int currentGeneration = 1;
        int peoplePerGeneration = 0;
        int personCount = 0;
        int eventCount = 1; //user birth counts as 1
        while(currentGeneration <= generationCount){
            int childBirthYear = birthYear.remove();
            Person child = people.remove();

            Person mother = newPerson("f");
            Person father = newPerson("m");
            //the marriage paperwork is being signed
            mother.setSpouseID(father.getPersonID());
            father.setSpouseID(mother.getPersonID());

            peoplePerGeneration += 2;
            int marriageDate = childBirthYear - 1;
            double[] marriageLatLong = newLongLat();
            String[] marriageLocation = newCountryCity();

            Person[] parents = {mother, father};
            for (Person parent : parents) {
                int bDay = childBirthYear - 21;
                try {
                    makeBirth(parent.getPersonID(), bDay); //todo: add variation to dates?
                    //Mawage. Mawage is wot bwings us togeder tooday. Mawage, that bwessed awangment, that dweam wifin a dweam… And wuv, tru wuv, will fowow you foweva… So tweasure your wuv.
                    eventDAO.insert(new Event("event" + Nonce.next(), username, parent.getPersonID(), marriageLatLong[0], marriageLatLong[1], marriageLocation[0], marriageLocation[1], "marriage", marriageDate));
                    makeDeath(parent.getPersonID(), bDay + 30);
                    eventCount += 3;
                    birthYear.add(bDay);
                    people.add(parent); //get them some parents
                    if(currentGeneration + 1 > generationCount){
                        personDAO.insert(parent);
                        personCount++;
                    }
                } catch (DataAccessException exception) {
                    exception.printStackTrace();
                    result = new FillResult("Failed when creating life events for " + parent.getPersonID(), false);
                    return;
                }
            }

            //the miracle of birth! (or maybe adoption)
            child.setMotherID(mother.getPersonID());
            child.setFatherID(father.getPersonID());
            donePeople.add(child); //make sure we add child to db later

            if(peoplePerGeneration >= Math.pow(2, currentGeneration)){ //each generation has 2^n people
                currentGeneration++;
                peoplePerGeneration = 0;
            }
        }

        for(Person person : donePeople){
            try {
                personDAO.insert(person);
                personCount++;
            } catch (DataAccessException exception) {
                exception.printStackTrace();
                result = new FillResult("Adding people to database faulted on " + person.getPersonID(), false);
                return;
            }
        }
        result = new FillResult("Successfully added " + personCount + " persons and " + eventCount + " events to the database.", true);
    }

    public FillResult getResult() {
        return result;
    }

    private void makeEvent(String personID, int year, String type) throws DataAccessException {
        double[] location = newLongLat();
        String[] countryCity = newCountryCity();
        if(type == null){
            type = newType();
        }
        eventDAO.insert(new Event("event" + Nonce.next(),username,personID,location[0],location[1],countryCity[0],countryCity[1],type,year));
    }
    private void makeBirth(String personID, int birthYear) throws DataAccessException {
        makeEvent(personID, birthYear, "birth");
    }
    private void makeDeath(String personID, int birthYear) throws DataAccessException {
        makeEvent(personID, birthYear + 49, "death");
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

    private Person newPerson(String gender){
        return new Person("person" + Nonce.next(),username,newName(),newName(),gender,null,null,null);
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