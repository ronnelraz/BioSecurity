package com.razo.biosecuritychecklist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.razo.biosecuritychecklist.adapter.Adapter_business_menu;
import com.razo.biosecuritychecklist.conn.API;
import com.razo.biosecuritychecklist.func.Globalfunction;
import com.razo.biosecuritychecklist.func.SharedPref;
import com.razo.biosecuritychecklist.gettersetter.model_select_business;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class SelectBusiness extends AppCompatActivity implements ActivityFinish {

    private Globalfunction data;
    private SharedPref sharedPref;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    private List<model_select_business> list;

    @BindView(R.id.header)
    TextView header;

    @BindView(R.id.loading)
    LottieAnimationView loading;

    ActivityFinish activityFinish = this;
    View view;
    @BindView(R.id.back) MaterialButton bank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_business);
        ButterKnife.bind(this);
        data = new Globalfunction(this);
        sharedPref = new SharedPref(this);
        view = new View(this);

        bank.setOnClickListener(v -> {
            finish();
        });

        header.setAnimation(data.fadeIn());

        list = new ArrayList<>();
        recyclerView = findViewById(R.id.data);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(999999999);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter_business_menu(list,this,sharedPref,this,view,this);
        recyclerView.setAdapter(adapter);
        initialize_business_menu();

        Log.d("selected_business",sharedPref.getBusiness_selected());

        if(sharedPref.getBusiness_selected().equals("true") && sharedPref.getSub_Business_selected().equals("true")){
            data.intent(Home.class,SelectBusiness.this);
            finish();
        }



    }


    private void initialize_business_menu() {
        list.clear();
        loading.setVisibility(View.VISIBLE);
        loading.setAnimation(R.raw.loading);
        loading.loop(true);
        loading.playAnimation();
        API.getClient().SelectBusiness(sharedPref.getuser_login()).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                try {

                    JSONObject jsonResponse = new JSONObject(new Gson().toJson(response.body()));
                    boolean success = jsonResponse.getBoolean("success");
                    JSONArray result = jsonResponse.getJSONArray("data");

                    if(success){
                        loading.setVisibility(View.GONE);
                        loading.loop(true);
                        loading.playAnimation();


                        if(result.length() == 1){
                           sharedPref.setBusiness_multiple("false");
                        }
                        else{
                            sharedPref.setBusiness_multiple("true");
                        }


                        for (int i = 0; i < result.length(); i++) {
                            JSONObject object = result.getJSONObject(i);
                            if(result.length() == 1){
                                sharedPref.setBusiness_name(object.getString("BusinessName"));
                                sharedPref.setBusiness_code(object.getString("BusinessCode"));
                                sharedPref.setBusiness_selected("false");
                                data.intent(Home.class,SelectBusiness.this);
                                finish();
                            }
                            else{
                                model_select_business item = new model_select_business(
                                        object.getString("BusinessName"),
                                        object.getString("BusinessCode"),
                                        object.getInt("count")
                                );
                                list.add(item);
                            }






                        }

                        adapter = new Adapter_business_menu(list,getApplicationContext(),sharedPref,activityFinish,view,SelectBusiness.this);
                        recyclerView.setAdapter(adapter);
                    }
                    else{
                        loading.setAnimation(R.raw.nodatafile);
                        loading.setVisibility(View.VISIBLE);
                        loading.loop(true);
                        loading.playAnimation();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("swine",e.getMessage() + " Error");
                    loading.setAnimation(R.raw.nodatafile);
                    loading.setVisibility(View.VISIBLE);
                    loading.loop(true);
                    loading.playAnimation();

                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                if (t instanceof IOException) {
                    data.toast(R.raw.error,t.getMessage(), Gravity.TOP|Gravity.CENTER,0,50);

                }
            }
        });
    }

    @Override
    public void onFinish() {
       this.finish();
    }
}