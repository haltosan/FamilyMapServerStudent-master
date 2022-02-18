package Model;

import java.util.Objects;

/**
 * Event object that corresponds with the sql table of the same name
 */
public class Event extends Model {
    private final String eventID;
    private final String associatedUsername;
    private String personID;
    private final double latitude;
    private final double longitude;
    private final String country;
    private final String city;
    private final String eventType;
    private final int year;

    /**
     *
     * @param eventID Model.Event's GUID
     * @param associatedUsername Username of person in event
     * @param personID Model.Person's GUID
     * @param latitude Model.Event's latitude
     * @param longitude Model.Event's longitude
     * @param country Country from longitude and latitude
     * @param city City from above location
     * @param eventType Type of event (birth, death, etc.)
     * @param year When event occurred
     */
    public Event(String eventID, String associatedUsername, String personID, double latitude, double longitude, String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getEventID() {
        return eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEventType() {
        return eventType;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o){
        if(o.getClass() != this.getClass()){
            return false;
        }
        Event other = (Event)o;
        return (Objects.equals(other.getEventID(), this.eventID));
    }
}
