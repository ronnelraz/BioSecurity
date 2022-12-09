package com.razo.biosecuritychecklist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;


import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.razo.biosecuritychecklist.adapter.Adapter_Checklist;
import com.razo.biosecuritychecklist.conn.API;
import com.razo.biosecuritychecklist.func.Globalfunction;
import com.razo.biosecuritychecklist.func.ItemChecker;
import com.razo.biosecuritychecklist.func.ListItem_Checklist;
import com.razo.biosecuritychecklist.func.SharedPref;
import com.razo.biosecuritychecklist.gettersetter.modal_checklist_Details;
import com.razo.biosecuritychecklist.gettersetter.modal_checklist_Footer;
import com.razo.biosecuritychecklist.gettersetter.modal_checklist_maintopic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class Checklist_Create extends AppCompatActivity {

    private Globalfunction data;
    private SharedPref sharedPref;

    @BindView(R.id.toolbar) MaterialToolbar toolbar;


    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    private ArrayList<ListItem_Checklist> items =  new ArrayList<>();
    @BindView(R.id.loading)
    LinearLayout loading;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_create);
        ButterKnife.bind(this);
        data = new Globalfunction(this);
        sharedPref = new SharedPref(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow));
        toolbar.setNavigationOnClickListener(v -> {
            //What to do on back clicked
            data.intent(Home.class,v.getContext());
            finish();
        });

        recyclerView = findViewById(R.id.checklistData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(999999999);
        adapter = new Adapter_Checklist(items,this,this,recyclerView,adapter);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setItemAnimator(null);
        recyclerView.setLayoutManager(linearLayoutManager);
        loading.setVisibility(View.VISIBLE);
        LoadFarmlist("SWCOM");
    }

    private void LoadFarmlist(String bu_code) {
//        data.Preloader(getActivity(),"Please wait...");
        items.clear();
        API.getClient().Checklist(bu_code).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                try {

                    JSONObject jsonResponse = new JSONObject(new Gson().toJson(response.body()));
                    boolean success = jsonResponse.getBoolean("success");
                    String checklist_type = jsonResponse.getString("type");
                    String code = jsonResponse.getString("code");


                    JSONArray checklistData = jsonResponse.getJSONArray("data");

                    if(success){
                        loading.setVisibility(View.GONE);
                        for (int i = 0; i < checklistData.length(); i++) {
                            JSONObject object = checklistData.getJSONObject(i);
                            modal_checklist_maintopic main_items = new modal_checklist_maintopic(
                                    object.getString("bu_type_code"),
                                    object.getString("main_code"),
                                    object.getString("main_topic"),
                                    object.getString("seq")
                            );
                            items.add(main_items);
                            JSONArray details =  object.getJSONArray("sub_details");
                            for (int i_d = 0; i_d < details.length(); i_d++){

                                JSONObject details_data = details.getJSONObject(i_d);
                                modal_checklist_Details details_item = new modal_checklist_Details(
                                        details_data.getString("sub_code"),
                                        details_data.getString("sub_des"),
                                        details_data.getString("sub_seq"),
                                        details_data.getString("sub_meaning"),
                                        details_data.getString("main_code"),
                                        false,
                                        i_d

                                );
                                items.add(details_item);
                            }


                        }
//                        modal_checklist_Footer footer = new modal_checklist_Footer(true);
//                        items.add(footer);
                        adapter = new Adapter_Checklist(items,Checklist_Create.this,Checklist_Create.this,recyclerView,adapter);
                        recyclerView.setAdapter(adapter);
                    }
                    else{
                        data.toast(R.raw.error,"Invalid Params", Gravity.TOP|Gravity.CENTER,0,50); //50
//                        data.loaddialog.dismiss();
                        loading.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("swine",e.getMessage());
                    loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                if (t instanceof IOException) {
                    data.toast(R.raw.error,t.getMessage(), Gravity.TOP|Gravity.CENTER,0,50);
//                    data.loaddialog.dismiss();
                    loading.setVisibility(View.GONE);
                }
            }
        });
    }


}