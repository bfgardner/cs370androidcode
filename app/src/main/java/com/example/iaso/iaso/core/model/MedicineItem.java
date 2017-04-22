package com.example.iaso.iaso.core.model;
/*

Java class to hold information about medication.

+Java DateFormat class

Date date = new Date(location.getTime());
DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
mTimeText.setText("Time: " + dateFormat.format(date));


 */



public class MedicineItem {

    private String medicineName;
    private String medicineDosage;
    private String medicineDetails;
    private String nextDose; // possibly date / time

    public String getMedicineName() {
        return medicineName;
    }


    public String getMedicineDosage() {
        return medicineDosage;
    }

    public String getMedicineDetails() {
        return medicineDetails;
    }

    public String getNextDose() {
        return nextDose;
    }

    public static class Builder {
        private MedicineItem instance = new MedicineItem();

        public Builder name(String name) {
            instance.medicineName = name;

            return this;
        }

        public Builder dosage(String dosage) {
            instance.medicineDosage = dosage;

            return this;
        }

        public Builder details(String details) {
            instance.medicineDetails = details;

            return this;
        }

        public Builder nextDose(String nextDose) {
            instance.nextDose = nextDose;

            return this;
        }

        public MedicineItem build() {
            MedicineItem item = instance;
            instance = null;

            return item;
        }

    }



}
