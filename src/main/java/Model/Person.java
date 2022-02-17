package Model;

import Model.Model;

/**
 * Person object that corresponds with the sql table of the same name
 */
public class Person extends Model {
    private String personID;
    private String associatedUsername; //2
    private String firstName;
    private String lastName; //4
    private String fatherID;
    private String motherID; //6
    private String spouseID;

    /**
     *
     * @param personID Model.Person's GUID
     * @param associatedUsername Model.Person's username
     * @param firstName Model.Person's first name for display
     * @param lastName Model.Person's last name for display
     * @param fatherID Model.Person's father (can be null)
     * @param motherID Model.Person's mother (can be null)
     * @param spouseID Model.Person's spouse (can be null)
     */
    public Person(String personID, String associatedUsername, String firstName, String lastName, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }
}
