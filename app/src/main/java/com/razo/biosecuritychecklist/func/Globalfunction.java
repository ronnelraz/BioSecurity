package com.razo.biosecuritychecklist.func;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.airbnb.lottie.LottieAnimationView;
import com.github.hariprasanths.bounceview.BounceView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.razo.biosecuritychecklist.R;

import java.util.ArrayList;
import java.util.List;

import moe.feng.common.view.breadcrumbs.model.BreadcrumbItem;

public class Globalfunction {

    private static Globalfunction application;
    private static Context cont;

    //idle function


    public Globalfunction(Context context){
        cont = context;
    }

    public static synchronized Globalfunction getInstance(Context context){
        if(application == null){
            application = new Globalfunction(context);
        }
        return application;
    }



    //header clickable



    //    intent

    public void intent(Class<?> activity, Context context){
        Intent i = new Intent(context,activity);
        context.startActivity(i);

    }

    public static BreadcrumbItem createItem(String title) {
        List<String> list = new ArrayList<>();
        list.add(title);
        return new BreadcrumbItem(list);
    }

    //input watcher
    public void inputwatcher(TextInputEditText inputEditText, TextInputLayout layout, String msg){
        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i >= 1){
                    layout.setErrorEnabled(false);
                }
                else{
                    layout.setErrorEnabled(true);
                    layout.setErrorIconDrawable(null);
                    layout.setError(msg);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    public void toast(int raw,String body,int postion,int x ,int y){
        Toast toast = new Toast(cont);
        View vs = LayoutInflater.from(cont).inflate(R.layout.custom_toast, null);
        LottieAnimationView icon = vs.findViewById(R.id.icon);
        TextView msg = vs.findViewById(R.id.body);

        icon.setAnimation(raw);
        icon.loop(false);
        icon.playAnimation();
        msg.setText(body);
        toast.setGravity(postion, x, y);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(vs);
        toast.show();
    }


    public Animation fadeIn(){
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(1000);
        return fadeIn;
    }

    public Animation fadeOut(){
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(1000);
        return fadeOut;
    }

    public AnimationSet animationSet(){
        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeIn());
        animation.addAnimation(fadeOut());
        return animation;
    }

    public Animation bouce(){
        final Animation myAnim = AnimationUtils.loadAnimation(cont, R.anim.bounce);
        // Use bounce interpolator with amplitude 0.1 and frequency 15
        bouce interpolator = new bouce(0.1, 15);
        myAnim.setInterpolator(interpolator);
        return  myAnim;
    }


    //dialog
    public MaterialAlertDialogBuilder Material_dialog;
    public AlertDialog load_dialog;
    public void preloader(Context context){
        Material_dialog = new MaterialAlertDialogBuilder(context);
        View v = LayoutInflater.from(context).inflate(R.layout.modal_loading,null);
        Material_dialog.setView(v);

        load_dialog = Material_dialog.create();
        load_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        BounceView.addAnimTo(load_dialog);
        load_dialog.show();
    }

    //alert_dialog
    public MaterialAlertDialogBuilder material_alert_dialog;
    public AlertDialog alert_dialog;
    public ImageView dialog_icon;
    public TextView dialog_title;
    public TextView dialog_details;
    public MaterialButton dialog_positive_btn;
    public MaterialButton dialog_negative_btn;
    public void AlertDialog(Context context,int icon,String title, String details, boolean showNegative){
        material_alert_dialog = new MaterialAlertDialogBuilder(context);
        View v = LayoutInflater.from(context).inflate(R.layout.modal_alertdialog,null);
        dialog_icon = v.findViewById(R.id.dialog_icon);
        dialog_title = v.findViewById(R.id.dialog_title);
        dialog_details = v.findViewById(R.id.dialog_details);
        dialog_positive_btn = v.findViewById(R.id.dialog_positive_btn);
        dialog_negative_btn = v.findViewById(R.id.dialog_negative_btn);

        dialog_icon.setImageResource(icon);
        dialog_title.setText(title);
        dialog_details.setText(details);

        if (showNegative) {
            dialog_negative_btn.setVisibility(View.VISIBLE);
        } else {
            dialog_negative_btn.setVisibility(View.GONE);
        }


        material_alert_dialog.setView(v);
        alert_dialog = material_alert_dialog.create();
        alert_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        BounceView.addAnimTo(alert_dialog);
        alert_dialog.show();
    }




}
