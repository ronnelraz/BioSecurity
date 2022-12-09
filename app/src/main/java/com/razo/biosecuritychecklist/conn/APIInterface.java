package com.razo.biosecuritychecklist.conn;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface APIInterface {


    @FormUrlEncoded
    @POST("Login")
    Call<Object> LOGIN_API(
            @Field("username") String username,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("SelectBU")
    Call<Object> SelectBusiness(
            @Field("username") String username
    );


    @FormUrlEncoded
    @POST("SelectSubBU")
    Call<Object> SelectSubBusiness(
            @Field("username") String username,
            @Field("bu_code") String bu_code
    );


    @POST("SwineCompany")
    Call<Object> SwineCompany();

    @POST("SwineInteg")
    Call<Object> SwineInteg();

    @FormUrlEncoded
    @POST("SwineInteg_sub_Business")
    Call<Object> SwineInteg_sub_Business(
            @Field("org_code") String org_code
    );

    @POST("Poultry_breeder_Business")
    Call<Object> Poultry_breeder_Business();

    @POST("Poultry_hatchery_Business")
    Call<Object> Poultry_hatchery_Business();

    @POST("Poultry_broilerCompany_Business")
    Call<Object> Poultry_broilerCompany_Business();

    @POST("Poultry_broilerInteg_Business")
    Call<Object> Poultry_broilerInteg_Business();


    @FormUrlEncoded
    @POST("Checklist")
    Call<Object> Checklist(
            @Field("bu_code") String bu_code
    );
}
