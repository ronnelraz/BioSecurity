package com.razo.biosecuritychecklist.gettersetter;


import com.razo.biosecuritychecklist.func.ListItem_Checklist;

public class modal_checklist_maintopic implements ListItem_Checklist {

    public String bu_type_code,main_code,main_topic,seq;

    public modal_checklist_maintopic(String bu_type_code, String main_code, String main_topic, String seq) {
        this.bu_type_code = bu_type_code;
        this.main_code = main_code;
        this.main_topic = main_topic;
        this.seq = seq;
    }

    public String getBu_type_code() {
        return bu_type_code;
    }

    public void setBu_type_code(String bu_type_code) {
        this.bu_type_code = bu_type_code;
    }

    public String getMain_code() {
        return main_code;
    }

    public void setMain_code(String main_code) {
        this.main_code = main_code;
    }

    public String getMain_topic() {
        return main_topic;
    }

    public void setMain_topic(String main_topic) {
        this.main_topic = main_topic;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    @Override
    public int getItemType() {
        return ListItem_Checklist.TYPE_HEADER;
    }
}
