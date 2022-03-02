package Result;


public class PersonResult extends Result {
    public String associatedUsername, personID, firstName, lastName, gender, fatherID, motherID, spouseID;

    public PersonResult(String associatedUsername, String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID, boolean success) {
        super(null, success);
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }
    public PersonResult(String message, boolean success){
        super(message, success);
    }
}
