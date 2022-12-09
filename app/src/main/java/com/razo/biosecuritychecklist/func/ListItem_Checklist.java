package com.razo.biosecuritychecklist.func;

public interface ListItem_Checklist {

    int TYPE_HEADER = 0;
    int TYPE_MAIN = 1;
    int TYPE_FOOTER = 2;

    int getItemType();
}
