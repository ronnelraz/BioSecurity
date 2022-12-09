package com.razo.biosecuritychecklist.gettersetter;


import com.razo.biosecuritychecklist.func.ListItem_Checklist;

public class modal_checklist_Footer implements ListItem_Checklist {

    public boolean actionBtn;

    public modal_checklist_Footer(boolean actionBtn) {
        this.actionBtn = actionBtn;
    }

    public boolean isActionBtn() {
        return actionBtn;
    }

    public void setActionBtn(boolean actionBtn) {
        this.actionBtn = actionBtn;
    }

    @Override
    public int getItemType() {
        return ListItem_Checklist.TYPE_FOOTER;
    }
}
