package io.iaso.iaso.core.model;

/**
 * Created by Brooke on 4/22/2017.
 */

public class NotificationPreferences {
    private String notif_type; //push/email/both
    private String initial_notif; //on time (0)?  15 mins before schedule (.25)?
    private String additional_notif; //every 15 mins after the first notif? (.25)
    private String ID;

    public void setAdditional_notif(String num) {additional_notif = num;}
    public void setInitial_notif(String num){initial_notif = num;}
    public void setID(String id){ID = id;}

    public String getID() {return ID;}

    public String getNotif_type(){
        return notif_type;
    }

    public String getInitial_notif(){
        return initial_notif;
    }

    public String getAdditional_notif(){
        return additional_notif;
    }

}
