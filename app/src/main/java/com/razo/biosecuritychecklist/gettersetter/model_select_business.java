package com.razo.biosecuritychecklist.gettersetter;

public class model_select_business {
    public String BusinessName,BusinessCode;
    public int Business_seq;

    public model_select_business(String businessName, String businessCode, int business_seq) {
        BusinessName = businessName;
        BusinessCode = businessCode;
        Business_seq = business_seq;
    }

    public String getBusinessName() {
        return BusinessName;
    }

    public void setBusinessName(String businessName) {
        BusinessName = businessName;
    }

    public String getBusinessCode() {
        return BusinessCode;
    }

    public void setBusinessCode(String businessCode) {
        BusinessCode = businessCode;
    }

    public int getBusiness_seq() {
        return Business_seq;
    }

    public void setBusiness_seq(int business_seq) {
        Business_seq = business_seq;
    }
}
