package com.example.iaso.iaso.core.model;

/**
 * Created by Brooke on 4/22/2017.
 */

public class NotificationPreferences {
    private String notif_type; //push/email/both
    private Integer initial_notif; //on time (0)?  15 mins before schedule (.25)?
    private Integer additional_notif; //every 15 mins after the first notif? (.25)
    private boolean aggressive; //notifications go from 15 minutes between to 10 mins to 5 mins etc

    public String getNotif_type(){
        return notif_type;
    }

    public Integer getInitial_notif(){
        return initial_notif;
    }

    public Integer getAdditional_notif(){
        return additional_notif;
    }

    public boolean isAggressive(){
        return aggressive;
    }
}
