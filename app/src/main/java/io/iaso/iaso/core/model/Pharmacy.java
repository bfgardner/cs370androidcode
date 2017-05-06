package io.iaso.iaso.core.model;

/**
 * Created by Brooke on 4/22/2017.
 */

public class Pharmacy {
    private String pharmacy_name;
    private Integer pharmacy_street_number;
    private String pharmacy_street;
    private String pharmacy_city;
    private String pharmacy_state;
    private Integer pharmacy_zip_code;

    public String getPharmacy_name(){
        return pharmacy_name;
    }

    public Integer getPharmacy_street_number(){
        return pharmacy_street_number;
    }

    public String getPharmacy_street(){
        return pharmacy_street;
    }

    public String getPharmacy_city(){
        return pharmacy_city;
    }

    public String getPharmacy_state(){
        return pharmacy_state;
    }

    public Integer getPharmacy_zip_code(){
        return pharmacy_zip_code;
    }
}
