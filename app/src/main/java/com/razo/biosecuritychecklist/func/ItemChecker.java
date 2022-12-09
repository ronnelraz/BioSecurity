package com.razo.biosecuritychecklist.func;

import android.view.View;
import android.widget.RadioGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.razo.biosecuritychecklist.adapter.Adapter_Checklist;

public interface ItemChecker {

    void iscchecked(int position, RadioGroup radioGroup, RecyclerView.ViewHolder holder);
}
