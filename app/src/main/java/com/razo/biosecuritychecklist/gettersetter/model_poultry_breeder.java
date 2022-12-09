package com.razo.biosecuritychecklist.gettersetter;

public class model_poultry_breeder {

    public String org_code,short_name,org_name_loc,business_group_code,business_type,menu;

    public model_poultry_breeder(String org_code, String short_name, String org_name_loc, String business_group_code, String business_type, String menu) {
        this.org_code = org_code;
        this.short_name = short_name;
        this.org_name_loc = org_name_loc;
        this.business_group_code = business_group_code;
        this.business_type = business_type;
        this.menu = menu;
    }

    public String getOrg_code() {
        return org_code;
    }

    public String getShort_name() {
        return short_name;
    }

    public String getOrg_name_loc() {
        return org_name_loc;
    }

    public String getBusiness_group_code() {
        return business_group_code;
    }

    public String getBusiness_type() {
        return business_type;
    }

    public String getMenu() {
        return menu;
    }
}
