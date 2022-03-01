package Result;

public class EventResult extends Result{

    String associatedUsername, eventID, personID, country, city, eventType;
    double latitude, longitude;
    int year;

    /**
     * @param message Message
     * @param success Success
     */
    public EventResult(String message, boolean success) {
        super(message, success);
    }

    public EventResult(String associatedUsername, String eventID, String personID, String country, String city, String eventType, double latitude, double longitude, int year, boolean success) {
        super(null, success);
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.year = year;
    }
}
