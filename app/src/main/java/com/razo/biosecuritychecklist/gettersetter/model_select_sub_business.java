package com.razo.biosecuritychecklist.gettersetter;

public class model_select_sub_business {
    public String BusinessCode,BusinessName;
    public int Business_seq;

    public model_select_sub_business(String businessCode, String businessName, int business_seq) {
        BusinessCode = businessCode;
        BusinessName = businessName;
        Business_seq = business_seq;
    }

    public String getBusinessCode() {
        return BusinessCode;
    }

    public String getBusinessName() {
        return BusinessName;
    }

    public int getBusiness_seq() {
        return Business_seq;
    }

    public void setBusinessCode(String businessCode) {
        BusinessCode = businessCode;
    }

    public void setBusinessName(String businessName) {
        BusinessName = businessName;
    }

    public void setBusiness_seq(int business_seq) {
        Business_seq = business_seq;
    }
}
