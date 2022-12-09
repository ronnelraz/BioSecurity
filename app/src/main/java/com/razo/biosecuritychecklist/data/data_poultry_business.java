package com.razo.biosecuritychecklist.data;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;
import com.razo.biosecuritychecklist.R;
import com.razo.biosecuritychecklist.SelectBusiness;
import com.razo.biosecuritychecklist.adapter.Adapter_poultry_breeder;
import com.razo.biosecuritychecklist.adapter.Adapter_swine_integ_sub;
import com.razo.biosecuritychecklist.conn.API;
import com.razo.biosecuritychecklist.func.Globalfunction;
import com.razo.biosecuritychecklist.gettersetter.model_poultry_breeder;
import com.razo.biosecuritychecklist.gettersetter.model_swine_sub_business;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class data_poultry_business {

    Context mContext;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<model_poultry_breeder> poultry_breederList;
    LinearLayout farmList_sub,famlistcontainer;
    LottieAnimationView loading;
    SelectBusiness selectBusiness;


    public data_poultry_business(Context mContext, RecyclerView recyclerView, RecyclerView.Adapter adapter, List<model_poultry_breeder> poultry_breederList, LinearLayout farmList_sub, LinearLayout famlistcontainer, LottieAnimationView loading, SelectBusiness selectBusiness) {
        this.mContext = mContext;
        this.recyclerView = recyclerView;
        this.adapter = adapter;
        this.poultry_breederList = poultry_breederList;
        this.farmList_sub = farmList_sub;
        this.famlistcontainer = famlistcontainer;
        this.loading = loading;
        this.selectBusiness = selectBusiness;
    }


    public void loadDataPoultry_breeder(Call<Object> objectCall,String menu) {
        poultry_breederList.clear();
        loading.setVisibility(View.VISIBLE);
        loading.setAnimation(R.raw.loading);
        loading.loop(true);
        loading.playAnimation();

        objectCall.enqueue(new Callback<Object>() {
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


                        for (int i = 0; i < result.length(); i++) {
                            JSONObject object = result.getJSONObject(i);

                            model_poultry_breeder item = new model_poultry_breeder(
                                    object.getString("OrgCode"),
                                    object.getString("ShortName"),
                                    object.getString("OrgNameLoc"),
                                    object.getString("business_group_code"),
                                    object.getString("business_type"),
                                    menu


                            );

                            poultry_breederList.add(item);


                        }

                        adapter = new Adapter_poultry_breeder(
                                mContext,
                                poultry_breederList,
                                loading,
                                selectBusiness);

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
                    Globalfunction.getInstance(mContext).toast(R.raw.error,t.getMessage(), Gravity.TOP|Gravity.CENTER,0,50);

                }
            }
        });
    }


}
