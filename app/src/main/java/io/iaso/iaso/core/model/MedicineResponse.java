package io.iaso.iaso.core.model;

import java.util.ArrayList;

/**
 * Created by Brooke on 4/22/2017.
 */

public class MedicineResponse {
        ArrayList<Medicine> medicines;
        public void setMedicines(ArrayList<Medicine> medicineList ) {medicines = medicineList; return;}
        public ArrayList<Medicine> getMedicines() {
            return medicines;
        }
}
