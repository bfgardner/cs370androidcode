package com.example.iaso.iaso.core.model;

import java.util.ArrayList;

/**
 * Created by Brooke on 4/22/2017.
 */

public class Medicine {

    private String med_name;
    private String description;
    private String dosage;
    private boolean prev_dose_taken;
    private String instructions;
    private ArrayList<String> warnings;
    //should next dose be stored as three sep. integers or as a string/date??
    private Integer next_dose_days;
    private Integer next_dose_hours;
    private Integer next_dose_minutes;
    private String nextDose;
    private String rec_unit; //recurrance
    private String rec_unit_freq;
    private Integer num_doses_per_unit;
    private ArrayList<Integer> doseTimes; //24 hour clock -- 1700 = 5 pm
    private Pharmacy pharmacy;

    public String getMed_name(){
        return med_name;
    }

    public String getDescription() {
        return description;
    }

    public String getDosage() {
        return dosage;
    }

    public boolean isPrevDoseTaken(){
        return prev_dose_taken;
    }

    public ArrayList<String> getWarnings(){
        return warnings;
    }

    public Integer getNext_dose_days(){
        return next_dose_days;
    }

    public Integer getNext_dose_hours(){
        return next_dose_hours;
    }

    public Integer getNext_dose_minutes(){
        return next_dose_minutes;
    }

    public String getRec_unit(){
        return rec_unit;
    }

    public String getRec_unit_freq(){
        return rec_unit_freq;
    }

    public Integer getNum_doses_per_unit(){
        return num_doses_per_unit;
    }

    public ArrayList<Integer> getDoseTimes(){
        return doseTimes;
    }

    public Pharmacy getPharmacy(){
        return pharmacy;
    }

    public static class Builder {
        private Medicine instance = new Medicine();

        public Builder name(String name) {
            instance.med_name = name;

            return this;
        }

        public Builder dosage(String _dosage) {
            instance.dosage = _dosage;

            return this;
        }

        public Builder description(String _description) {
            instance.description = _description;

            return this;
        }

        public Builder nextDose(String nextDose) {
            instance.nextDose = nextDose;

            return this;
        }

        public Medicine build() {
            Medicine item = instance;
            instance = null;

            return item;
        }

    }
}
