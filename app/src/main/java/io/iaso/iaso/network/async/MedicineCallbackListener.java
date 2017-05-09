package io.iaso.iaso.network.async;

import io.iaso.iaso.core.model.MedicineResponse;

public interface MedicineCallbackListener {
    void onMedicineCallback(MedicineResponse response);
}