package com.razo.biosecuritychecklist.gettersetter;


import com.razo.biosecuritychecklist.func.ListItem_Checklist;

public class modal_checklist_Details implements ListItem_Checklist {

    public String sub_code,sub_des,sub_seq,sub_meaning,main_code;
    public boolean radiocheck;
    public int position;

    public modal_checklist_Details(String sub_code, String sub_des, String sub_seq, String sub_meaning, String main_code, boolean radiocheck, int position) {
        this.sub_code = sub_code;
        this.sub_des = sub_des;
        this.sub_seq = sub_seq;
        this.sub_meaning = sub_meaning;
        this.main_code = main_code;
        this.radiocheck = radiocheck;
        this.position = position;
    }

    public String getSub_code() {
        return sub_code;
    }

    public void setSub_code(String sub_code) {
        this.sub_code = sub_code;
    }

    public String getSub_des() {
        return sub_des;
    }

    public void setSub_des(String sub_des) {
        this.sub_des = sub_des;
    }

    public String getSub_seq() {
        return sub_seq;
    }

    public void setSub_seq(String sub_seq) {
        this.sub_seq = sub_seq;
    }

    public String getSub_meaning() {
        return sub_meaning;
    }

    public void setSub_meaning(String sub_meaning) {
        this.sub_meaning = sub_meaning;
    }

    public String getMain_code() {
        return main_code;
    }

    public void setMain_code(String main_code) {
        this.main_code = main_code;
    }

    public boolean isRadiocheck() {
        return radiocheck;
    }

    public void setRadiocheck(boolean radiocheck) {
        this.radiocheck = radiocheck;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int getItemType() {
        return ListItem_Checklist.TYPE_MAIN;
    }
}
