package io.iaso.iaso.core.model;

import java.util.ArrayList;

/**
 * Created by Brooke on 4/22/2017.
 */

public class UserData {
    private String username; //display purpose
    private String email; //user should be able to change their email
    private String ID;

    public void setEmail(String _email){email = _email;}
    public void setUsername(String _username){username = _username;}
    public void setID(String id){ID = id;}
    public String getUsername(){
        return username;
    }
    public String getID(){return ID;}
    public String getEmail() {return email;}


}
