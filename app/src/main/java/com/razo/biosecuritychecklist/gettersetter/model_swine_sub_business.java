package com.razo.biosecuritychecklist.gettersetter;

public class model_swine_sub_business {

    public String farm_code,farm_name,business_group_code;

    public model_swine_sub_business(String farm_code, String farm_name, String business_group_code) {
        this.farm_code = farm_code;
        this.farm_name = farm_name;
        this.business_group_code = business_group_code;
    }

    public String getFarm_code() {
        return farm_code;
    }

    public String getFarm_name() {
        return farm_name;
    }

    public String getBusiness_group_code() {
        return business_group_code;
    }
}
