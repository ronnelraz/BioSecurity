package com.razo.biosecuritychecklist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.askjeffreyliu.flexboxradiogroup.CustomizableRadioButton;

public class CustomRadioButton extends CustomizableRadioButton {
    private TextView textView;

    public CustomRadioButton(Context context) {
        super(context);
        initView();
    }

    public CustomRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        View view = inflate(getContext(), R.layout.my_radio_button, null);
        addView(view);
        textView = view.findViewById(R.id.text);
    }

    @Override
    public void setChecked(boolean b) {
        super.setChecked(b);
        if (textView != null) {
            textView.setText(b ? android.R.string.yes : android.R.string.no);
        }
    }
}