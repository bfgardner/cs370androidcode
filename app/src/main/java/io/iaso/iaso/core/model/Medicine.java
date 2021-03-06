package io.iaso.iaso.core.model;

/**
 */

public class Medicine {

    private String med_name;
    private String description;
    private String dosage;
    private String prev_dose_taken;
    private String instructions;
   // private ArrayList<String> warnings;
    private String nextDose;
    private String dosage_times; //looks something like 08001900, can parse before display??
    private Integer doses_per_day;
    private String main_usage;
   // private Pharmacy pharmacy;

    public String medicine_id;
    public String getMedicine_id() {return medicine_id;}
    public String getMed_name(){
        return med_name;
    }

    public String getDescription() {
        return description;
    }

    public String getDosage() {
        return dosage;
    }

    public String isPrevDoseTaken(){
        return prev_dose_taken;
    }

    public String getInstructions() {return instructions;}

    //public ArrayList<String> getWarnings(){
      //  return warnings;
   // }
    public String getNextDose() {return nextDose;}

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

        public Builder dosesPerDay(String num_per){
           // Integer ans = Integer.parseInt(num_per);
            instance.doses_per_day = Integer.parseInt(num_per);
            return this;
        }

        public Builder mainUsage(String mainUse){
            instance.main_usage = mainUse;
            return this;
        }

        public Builder doseTimes(String times){
            instance.dosage_times = times;
            return this;
        }

        public Builder instruct(String string){
            instance.instructions = string;
            return this;
        }

        public Builder identify(String id){
            instance.medicine_id = id;
            return this;
        }

        public Medicine build() {
            Medicine item = instance;
            instance = null;

            return item;
        }

    }
}
