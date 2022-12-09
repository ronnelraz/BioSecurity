package com.razo.biosecuritychecklist;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.razo.biosecuritychecklist.adapter.Adapter_menu_list;
import com.razo.biosecuritychecklist.func.Globalfunction;
import com.razo.biosecuritychecklist.func.SharedPref;
import com.razo.biosecuritychecklist.gettersetter.model_menu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class Home extends AppCompatActivity  {


    private Globalfunction data;
    private SharedPref sharedPref;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.gridlayout)
    RecyclerView gridlayout;

    List<model_menu> menulist = new ArrayList<>();
    RecyclerView.Adapter adapter;

//    @BindViews({R.id.user_login_name,R.id.selected_business})
//    TextView[] user_login;
      public static TextView user,business;

    String[] menu_array = new String[]{
            "TRANSACTION LIST",
            "CREATE",
            "EDIT",
            "CONFIRM",
            "CANCEL",
            "GENERATE PDF",

    };

    int[] menu_icon = new int[]{
            R.drawable.list,R.drawable.add,
            R.drawable.edit,R.drawable.confirm,R.drawable.cancel,
            R.drawable.pdf
    };

    int[] menu_seq = new int[]{
          1,2,3,4,5
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        data = new Globalfunction(this);
        sharedPref = new SharedPref(this);
        user  = findViewById(R.id.user_login_name);
        business = findViewById(R.id.selected_business);
        setSupportActionBar(toolbar);



        if(sharedPref.session()){
            Log.d("login","session started");
        }
        else{
            Log.d("login","session ended");
        }


        SharedPref.getInstance(this).setSub_Business_name("");

        user.setText(Html.fromHtml("<strong>User : </strong>" +sharedPref.getuser_login()));
        if(!sharedPref.getSub_Business_name().isEmpty()){
            business.setText(Html.fromHtml("<strong>"+sharedPref.getSub_Business_name()+"</strong>"));
            if(sharedPref.getBusiness_code().equals("PT")){
                business.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.business_icon_poultry, 0);
            }
            else{
                business.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.business_icon_swine, 0);
            }
        }




        for(int i = 0; i < menu_array.length; i++){
            model_menu list = new model_menu(
                    i,
                    menu_icon[i],
                    menu_array[i]
            );
            menulist.add(list);
        }


        GridLayoutManager layout_manager = new GridLayoutManager(this, 2);
        layout_manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position == 0 ? 2 : 1);
            }
        });
        adapter = new Adapter_menu_list(menulist,this);
        gridlayout.setLayoutManager(layout_manager);
        gridlayout.setHasFixedSize(true);
        gridlayout.setItemViewCacheSize(999999999);
        gridlayout.setAdapter(adapter);

    }


    public static void tintMenuIcon(Context context, MenuItem item, @ColorRes int color) {
        Drawable normalDrawable = item.getIcon();
        Drawable wrapDrawable = DrawableCompat.wrap(normalDrawable);
        DrawableCompat.setTint(wrapDrawable, context.getResources().getColor(color));
        item.setIcon(wrapDrawable);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem profile = menu.findItem(R.id.switch_user);

        if(sharedPref.getBusiness_multiple().equals("true")){
            profile.setVisible(false);
        }
        else{
            profile.setVisible(false);
        }

        MenuItem logout = menu.findItem(R.id.logout);
        tintMenuIcon(Home.this, profile,android.R.color.white);
        tintMenuIcon(Home.this, logout, android.R.color.holo_red_light);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.switch_user:

                data.AlertDialog(this,
                        R.drawable.question,
                        "Switch Business",
                        "Are you sure you want to switch Business type?",
                        true);
                data.dialog_positive_btn.setOnClickListener(v -> {
                    data.alert_dialog.dismiss();
                    sharedPref.setBusiness_selected("false");
                    data.intent(SelectBusiness.class,this);
                    finish();
                });


                data.dialog_negative_btn.setOnClickListener(v -> {
                    data.alert_dialog.dismiss();
                });

                return true;
            case R.id.logout:
                data.AlertDialog(this,
                R.drawable.question,
                "Logout",
                "Are you sure you want logout?",
                true);
                data.dialog_positive_btn.setOnClickListener(v -> {
                    sharedPref.endSession("false");
                    sharedPref.setBusiness_selected("false");
                    data.alert_dialog.dismiss();
                    data.intent(MainActivity.class,this);
                    finish();
                });


                data.dialog_negative_btn.setOnClickListener(v -> {
                    data.alert_dialog.dismiss();
                });

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();

    }



}