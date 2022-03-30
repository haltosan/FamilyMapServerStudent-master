package Model;

import Model.Model;

import java.util.Objects;

/**
 * Model.AuthToken object that corresponds with the sql table of the same name
 */
public class AuthToken extends Model {
    private final String authtoken;
    private String username;

    /**
     *
     * @param authtoken Random string to authenticate session
     * @param username Username to authenticate
     */
    public AuthToken(String authtoken, String username) {
        this.authtoken = authtoken;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthtoken(){
        return authtoken;
    }

    @Override
    public boolean equals(Object o){
        if(o.getClass() != this.getClass()){
            return false;
        }
        return Objects.equals(((AuthToken) o).getUsername(), this.getUsername());
    }
}
