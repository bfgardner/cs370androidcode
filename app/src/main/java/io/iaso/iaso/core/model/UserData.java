package io.iaso.iaso.core.model;

import java.util.ArrayList;

/**
 * Created by Brooke on 4/22/2017.
 */

public class UserData {
    private String username; //display purpose
    private String email; //user should be able to change their email
    private String trusted_contact_email;
    private ArrayList<MedicineResponse> medicines;
    private Pharmacy favorite_pharmacy;

    public void setEmail(String _email){email = _email;}
    public void setUsername(String _username){username = _username;}
    public String getUsername(){
        return username;
    }

    public String getTrusted_contact_email(){
        return trusted_contact_email;
    }

    public String getEmail() {return email;}

    public Pharmacy getFavorite_pharmacy(){
        return favorite_pharmacy;
    }

    public ArrayList<MedicineResponse> getMedicines() {return medicines;}

}
