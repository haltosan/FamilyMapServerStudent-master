package Model;

import java.util.Objects;

/**
 * Person object that corresponds with the sql table of the same name
 */
public class Person extends Model {
    private String personID;
    private final String associatedUsername; //2
    private final String firstName;
    private final String lastName; //4
    private final String gender;
    private String fatherID; //6
    private String motherID;
    private String spouseID; //8

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
    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender(){
        return gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    @Override
    public boolean equals(Object o){
        if(o.getClass() != this.getClass()){
            return false;
        }
        return Objects.equals(((Person) o).getPersonID(), this.getPersonID());
    }
}
