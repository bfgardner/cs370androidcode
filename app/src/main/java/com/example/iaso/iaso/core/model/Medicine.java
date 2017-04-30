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
   // private ArrayList<String> warnings;
    //should next dose be stored as three sep. integers or as a string/date??
    private Integer next_dose_days;
    private Integer next_dose_hours;
    private Integer next_dose_minutes;
    private String nextDose;
    private String dosage_times; //looks something like 08001900, can parse before display??
    private Integer doses_per_day;
    private String main_usage;
   // private Pharmacy pharmacy;

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

    //public ArrayList<String> getWarnings(){
      //  return warnings;
   // }

    public Integer getNext_dose_days(){
        return next_dose_days;
    }

    public Integer getNext_dose_hours(){
        return next_dose_hours;
    }

    public Integer getNext_dose_minutes(){
        return next_dose_minutes;
    }

    public String getDosage_times(){return dosage_times;}

    public Integer getDoses_per_day() {return doses_per_day;}

    public String getMain_usage() {return main_usage;}

   // public Pharmacy getPharmacy(){
     //   return pharmacy;
    //}

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
