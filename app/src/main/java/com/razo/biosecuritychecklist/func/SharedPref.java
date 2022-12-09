package com.razo.biosecuritychecklist.func;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    public static final String TAG = "Swine";
    private static SharedPref application;
    private static Context cont;


    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static final String SHARED_DATA = "SHARED_DATA";
    private static final String SHARED_USER = "USER_AD";
    private static final String SHARED_LOGIN = "false";
    private static final String BUSINESS_NAME = "BUSINESS_NAME";
    private static final String BUSINESS_CODE = "BUSINESS_CODE";
    private static final String BUSINESS_MULTIPLE = "BUSINESS_MULTIPLE";
    private static final String BUSINESS_SELECTED = "BUSINESS_SELECTED";


    private static final String SUB_BUSINESS_NAME = "SUB_BUSINESS_NAME";
    private static final String SUB_BUSINESS_CODE = "SUB_BUSINESS_CODE";
    private static final String SUB_BUSINESS_MULTIPLE = "SUB_BUSINESS_MULTIPLE";
    private static final String SUB_BUSINESS_SELECTED = "SUB_BUSINESS_SELECTED";


    public SharedPref(Context context){
        cont = context;
    }

    public static synchronized SharedPref getInstance(Context context){
        if(application == null){
            application = new SharedPref(context);
        }
        return application;
    }


    public boolean setLogin(String username,String login){
        sharedPreferences = cont.getSharedPreferences(SHARED_DATA,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(SHARED_USER,username);
        editor.putString(SHARED_LOGIN,login);
        editor.apply();
        return true;
    }


    public boolean setBusiness_name(String business){
        sharedPreferences = cont.getSharedPreferences(SHARED_DATA,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BUSINESS_NAME,business);
        editor.apply();
        return true;
    }


    public boolean setSub_Business_name(String business){
        sharedPreferences = cont.getSharedPreferences(SHARED_DATA,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(SUB_BUSINESS_NAME,business);
        editor.apply();
        return true;
    }





    public boolean setBusiness_selected(String selected){
        sharedPreferences = cont.getSharedPreferences(SHARED_DATA,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BUSINESS_SELECTED,selected);
        editor.apply();
        return true;
    }

    public boolean setSub_Business_selected(String selected){
        sharedPreferences = cont.getSharedPreferences(SHARED_DATA,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(SUB_BUSINESS_SELECTED,selected);
        editor.apply();
        return true;
    }

    public boolean setBusiness_multiple(String multiple){
        sharedPreferences = cont.getSharedPreferences(SHARED_DATA,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BUSINESS_MULTIPLE,multiple);
        editor.apply();
        return true;
    }

    public boolean setSub_Business_multiple(String multiple){
        sharedPreferences = cont.getSharedPreferences(SHARED_DATA,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(SUB_BUSINESS_MULTIPLE,multiple);
        editor.apply();
        return true;
    }

    public boolean setBusiness_code(String business){
        sharedPreferences = cont.getSharedPreferences(SHARED_DATA,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BUSINESS_CODE,business);
        editor.apply();
        return true;
    }

    public boolean setSub_Business_code(String business){
        sharedPreferences = cont.getSharedPreferences(SHARED_DATA,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(SUB_BUSINESS_CODE,business);
        editor.apply();
        return true;
    }

    public String getBusiness_name(){
        sharedPreferences = cont.getSharedPreferences(SHARED_DATA,Context.MODE_PRIVATE);
        return sharedPreferences.getString(BUSINESS_NAME, "null");
    }

    public String getSub_Business_name(){
        sharedPreferences = cont.getSharedPreferences(SHARED_DATA,Context.MODE_PRIVATE);
        return sharedPreferences.getString(SUB_BUSINESS_NAME, "null");
    }

    public String getBusiness_selected(){
        sharedPreferences = cont.getSharedPreferences(SHARED_DATA,Context.MODE_PRIVATE);
        return sharedPreferences.getString(BUSINESS_SELECTED, "false");
    }

    public String getSub_Business_selected(){
        sharedPreferences = cont.getSharedPreferences(SHARED_DATA,Context.MODE_PRIVATE);
        return sharedPreferences.getString(SUB_BUSINESS_SELECTED, "false");
    }

    public String getBusiness_multiple(){
        sharedPreferences = cont.getSharedPreferences(SHARED_DATA,Context.MODE_PRIVATE);
        return sharedPreferences.getString(BUSINESS_MULTIPLE, "false");
    }

    public String getSub_Business_multiple(){
        sharedPreferences = cont.getSharedPreferences(SHARED_DATA,Context.MODE_PRIVATE);
        return sharedPreferences.getString(SUB_BUSINESS_MULTIPLE, "false");
    }

    public String getBusiness_code(){
        sharedPreferences = cont.getSharedPreferences(SHARED_DATA,Context.MODE_PRIVATE);
        return sharedPreferences.getString(BUSINESS_CODE, "null");
    }

    public String getSubBusiness_code(){
        sharedPreferences = cont.getSharedPreferences(SHARED_DATA,Context.MODE_PRIVATE);
        return sharedPreferences.getString(SUB_BUSINESS_CODE, "null");
    }



    public String getuser_login(){
        sharedPreferences = cont.getSharedPreferences(SHARED_DATA,Context.MODE_PRIVATE);
        return sharedPreferences.getString(SHARED_USER, "USER_AD");
    }


    public boolean session(){
        sharedPreferences = cont.getSharedPreferences(SHARED_DATA,Context.MODE_PRIVATE);
        return !sharedPreferences.getString(SHARED_LOGIN, "false").equals("false");
    }

    public boolean endSession(String login){
        sharedPreferences = cont.getSharedPreferences(SHARED_DATA,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(SHARED_LOGIN,login);
        editor.apply();
        return true;
    }


}
