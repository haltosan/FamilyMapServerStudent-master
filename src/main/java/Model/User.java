package Model;

import java.util.Objects;

/**
 * Person object that corresponds with the sql table of the same name
 */
public class User extends Model {
    private String username;
    private final String password; //2
    private final String email;
    private final String firstName; //4
    private final String lastName;
    private final String gender; //6
    private String personID;

    /**
     *
     * @param username Model.User's username
     * @param password Model.User's password
     * @param email Model.User's email for registration
     * @param firstName Model.User's first name for registration
     * @param lastName Model.User's last name for registration
     * @param gender Model.User's gender for registration
     * @param personID Model.User's autogenerated GUID
     */
    public User(String username, String password, String email, String firstName, String lastName, String gender, String personID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @Override
    public boolean equals(Object o){
        if(o.getClass() != this.getClass()){
            return false;
        }
        return Objects.equals(((User) o).getPersonID(), this.getPersonID());
    }
}
