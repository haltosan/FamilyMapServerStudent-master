package Model;

import Model.Model;

/**
 * Person object that corresponds with the sql table of the same name
 */
public class User extends Model {
    private String username;
    private String password; //2
    private String email;
    private String firstName; //4
    private String lastName;
    private String gender; //6
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
