package com.example.iaso.iaso.network.async;

import com.example.iaso.iaso.core.model.MedicineResponse;

public interface MedicineCallbackListener {
    void onMedicineCallback(MedicineResponse response);
}