package com.razo.biosecuritychecklist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.razo.biosecuritychecklist.conn.API;
import com.razo.biosecuritychecklist.func.Globalfunction;
import com.razo.biosecuritychecklist.func.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {


    private Globalfunction data;
    private SharedPref sharedPref;

    @BindView(R.id.login)
    MaterialButton login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        data = new Globalfunction(this);
        sharedPref = new SharedPref(this);



        if(sharedPref.session()){
            data.intent(Home.class,this);
            finish();
        }
        else{
            Log.d("login","session ended");
        }

    }

    @BindViews({R.id.username,R.id.password})
    TextInputEditText[] input;
    @BindViews({R.id.username_layout,R.id.password_layout})
    TextInputLayout[] input_layout;
    public void login(View view) {
        String getusername = input[0].getText().toString();
        String getPassword = input[1].getText().toString();

        if(getusername.isEmpty()){
            input_layout[0].setError("Invalid User ID");
            data.inputwatcher(input[0],input_layout[0],"Invalid User ID");
            input[0].requestFocus();
        }
        else if(getPassword.isEmpty()){
            input_layout[1].setError("Invalid Password");
            data.inputwatcher(input[1],input_layout[1],"Invalid Password");
            input[1].requestFocus();
        }
        else{
            data.preloader(this);
            Log.d("login","Conection to the server");
            API.getClient().LOGIN_API(getusername.toLowerCase(),getPassword).enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(new Gson().toJson(response.body()));
                        int SUCCESS = jsonResponse.getInt("success");
                        String MESSAGE = jsonResponse.getString("message");

                        if(SUCCESS == 0){
                            data.load_dialog.dismiss();
                            sharedPref.setLogin(getusername,"true");
                            data.toast(R.raw.success,MESSAGE, Gravity.BOTTOM|Gravity.CENTER,0,50);

                            new Handler().postDelayed(() -> {
                                data.intent(Home.class,view.getContext());
                                finish();
                            },3000);





                        }
                        else{
                            data.load_dialog.dismiss();
                            data.toast(R.raw.error,MESSAGE, Gravity.BOTTOM|Gravity.CENTER,0,50);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("login",e.getMessage());

                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    if (t instanceof IOException) {
                        data.toast(R.raw.error,t.getMessage(), Gravity.BOTTOM|Gravity.CENTER,0,50);
                        data.load_dialog.dismiss();
                    }
                }
            });

        }
    }
}