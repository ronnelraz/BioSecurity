package com.razo.biosecuritychecklist.adapter;

import static com.razo.biosecuritychecklist.func.Globalfunction.createItem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import com.github.hariprasanths.bounceview.BounceView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.razo.biosecuritychecklist.ActivityFinish;
import com.razo.biosecuritychecklist.R;
import com.razo.biosecuritychecklist.SelectBusiness;
import com.razo.biosecuritychecklist.conn.API;
import com.razo.biosecuritychecklist.func.Globalfunction;
import com.razo.biosecuritychecklist.func.SharedPref;
import com.razo.biosecuritychecklist.gettersetter.model_select_business;
import com.razo.biosecuritychecklist.gettersetter.model_select_sub_business;
import com.razo.biosecuritychecklist.gettersetter.model_swine_business;
import com.razo.biosecuritychecklist.gettersetter.model_swine_sub_business;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import moe.feng.common.view.breadcrumbs.BreadcrumbsView;
import moe.feng.common.view.breadcrumbs.DefaultBreadcrumbsCallback;
import moe.feng.common.view.breadcrumbs.model.BreadcrumbItem;
import retrofit2.Call;
import retrofit2.Callback;

public class Adapter_business_menu extends RecyclerView.Adapter<Adapter_business_menu.ViewHolder> {
    Context mContext;
    List<model_select_business> newsList;
    SharedPref sharedPref;
    ActivityFinish activityFinish;
    View view;
    RecyclerView rviewbottom,swineCompany,swine_integ_sub;
    TextView titlesub_menu;
    BreadcrumbsView breadcrumbview;
    SelectBusiness selectBusiness;



    private BottomSheetBehavior bottomSheetBehavior;
    private RecyclerView.Adapter adapter,adapter_Swine_business,adapter_integ_sub,breeder_adapter;
    private List<model_select_sub_business> sublist;
    private List<model_swine_business> swine_company_list = new ArrayList<>();
    private List<model_swine_sub_business> swine_sub_businesses_list = new ArrayList<>();
    LinearLayout subBroilerFarm,famlistcontainer,farmList_sub;
    ImageView imageViews_com;
    ImageView imageViews_integ;





    public Adapter_business_menu(List<model_select_business> list, Context context,SharedPref sharedPref,ActivityFinish finish,View view, SelectBusiness selectBusiness) {
        super();
        this.newsList = list;
        this.mContext = context;
        this.sharedPref = sharedPref;
        this.activityFinish = finish;
        this.view = view;
        this.selectBusiness = selectBusiness;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_business,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final model_select_business getData = newsList.get(position);
        BounceView.addAnimTo(holder.menu);

        holder.menu.startAnimation(Globalfunction.getInstance(mContext).bouce());

        holder.title.setText(getData.getBusinessName());
        if(getData.getBusinessCode().equals("POULTRY")){
            holder.icon.setImageResource(R.drawable.poultry);
        }
        else{
            holder.icon.setImageResource(R.drawable.swine);
        }

//        if(position == 0){
//            if(!sharedPref.getBusiness_code().equals("null")){
//                if(sharedPref.getSub_Business_selected().equals("false")){
//                    showBottomSheetDialog(view,sharedPref.getBusiness_name(),sharedPref.getBusiness_code());
//                    Toast.makeText(mContext, sharedPref.getBusiness_code(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        }

        holder.menu.setOnClickListener(v -> {
            sharedPref.setBusiness_name(getData.getBusinessName());
            sharedPref.setBusiness_code(getData.getBusinessCode());
            showBottomSheetDialog(v,getData.getBusinessName(),getData.getBusinessCode());

        });



    }


    private void showBottomSheetDialog(View v,String Business_Name,String Business_code) {
        View view = LayoutInflater.from(v.getContext()).inflate(R.layout.modal_sub_business,null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v.getContext(),R.style.BottomSheetDialog);

        LinearLayout linearLayout = view.findViewById(R.id.root);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        params.height = getScreenHeight();
        linearLayout.setLayoutParams(params);

        ImageView back = view.findViewById(R.id.back);
        back.setOnClickListener(v1 -> {
            sublist.clear();
            bottomSheetDialog.dismiss();
        });
        BounceView.addAnimTo(back);
        LottieAnimationView loading = view.findViewById(R.id.loading);
         rviewbottom = view.findViewById(R.id.datalist);
        titlesub_menu = view.findViewById(R.id.titlesub_menu);
        breadcrumbview = view.findViewById(R.id.breadcrumbview);
        subBroilerFarm = view.findViewById(R.id.subBroilerFarm);
        famlistcontainer = view.findViewById(R.id.farmList);
        imageViews_com = view.findViewById(R.id.icons_com);
        imageViews_integ = view.findViewById(R.id.icons_integ);
        swineCompany = view.findViewById(R.id.swineCompany);

        /**
         * swine integ
         * **/
        swine_integ_sub = view.findViewById(R.id.swine_sub);
        farmList_sub = view.findViewById(R.id.farmList_sub);





        titlesub_menu.setText(Business_Name);
        titlesub_menu.setVisibility(View.GONE);
        breadcrumbview.addItem(Globalfunction.getInstance(mContext).createItem(Business_Name));


        rviewbottom.setHasFixedSize(true);
        rviewbottom.setItemViewCacheSize(999999999);

        sublist = new ArrayList<>();
        rviewbottom.setLayoutManager(new LinearLayoutManager(v.getContext()));
        adapter = new Adapter_sub_business_menu(sublist,view.getContext(),Business_Name,Business_code,sharedPref,activityFinish,rviewbottom,breadcrumbview,subBroilerFarm,famlistcontainer,loading,swineCompany,selectBusiness);
        rviewbottom.setAdapter(adapter);




        loadData(v,loading,rviewbottom,Business_Name,Business_code,subBroilerFarm,famlistcontainer,swineCompany);

        bottomSheetDialog.setContentView(view);
        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();

    }


    //load farm company swine
    private void loadDataCompany(View v,LottieAnimationView loading,RecyclerView recyclerView,Call<Object> objectCall,BreadcrumbsView breadcrumbview) {
        loading.setVisibility(View.VISIBLE);
        loading.setAnimation(R.raw.loading);
        loading.loop(true);
        loading.playAnimation();

//        Call<Object> com = type.equals("com") ? API.getClient().SwineCompany() : API.getClient().SwineInteg();

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

                            model_swine_business item = new model_swine_business(
                                    object.getString("OrgCode"),
                                    object.getString("ShortName"),
                                    object.getString("OrgNameLoc"),
                                    object.getString("business_group_code"),
                                    object.getString("business_type")


                            );

                            swine_company_list.add(item);


                        }

                        adapter_Swine_business = new Adapter_swine_company(
                                swine_company_list,
                                v.getContext(),
                                breadcrumbview,
                                swine_integ_sub,
                                adapter_integ_sub,
                                swine_sub_businesses_list,
                                farmList_sub,
                                famlistcontainer,
                                loading,
                                selectBusiness);

                        recyclerView.setAdapter(adapter_Swine_business);
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
                    Globalfunction.getInstance(v.getContext()).toast(R.raw.error,t.getMessage(), Gravity.TOP|Gravity.CENTER,0,50);

                }
            }
        });
    }


    private void loadData(View v,LottieAnimationView loading,RecyclerView recyclerView,String Business_name,String Business_code,LinearLayout subBroilerFarm,LinearLayout famlistcontainer,RecyclerView swineCompany) {
        sublist.clear();

        loading.setVisibility(View.VISIBLE);
        loading.setAnimation(R.raw.loading);
        loading.loop(true);
        loading.playAnimation();
        API.getClient().SelectSubBusiness(sharedPref.getuser_login(),Business_code).enqueue(new Callback<Object>() {
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

                        if(Business_code.equals("SWINE")){
                            recyclerView.setVisibility(View.GONE);
                            subBroilerFarm.setVisibility(View.VISIBLE);
                            imageViews_com.setImageResource(R.drawable.swine_);
                            imageViews_integ.setImageResource(R.drawable.swine_);

                        }
//                        else{
//                            recyclerView.setVisibility(View.GONE);
//                            subBroilerFarm.setVisibility(View.VISIBLE);
//                        }



                        if(Business_code.equals("SWINE")){
                            CardView company = (CardView) subBroilerFarm.getChildAt(0);
                            CardView integ = (CardView) subBroilerFarm.getChildAt(1);

                            BounceView.addAnimTo(company);
                            BounceView.addAnimTo(integ);
                            company.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getSelectedBusiness( "SWINE BUSINESS","COMPANY");
                                    subBroilerFarm.setVisibility(View.GONE);
                                    swine_company_list.clear();
                                    loadDataCompany(v,loading,swineCompany,API.getClient().SwineCompany(),breadcrumbview);
                                }
                            });

                            integ.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getSelectedBusiness( "SWINE BUSINESS","INTEGRATION");
                                    subBroilerFarm.setVisibility(View.GONE);
                                    swine_company_list.clear();
                                    loadDataCompany(v,loading,swineCompany,API.getClient().SwineInteg(),breadcrumbview);
                                }
                            });
                        }
//                        else  if(Business_code.equals("POULTRY")){
//                            company.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    getSelectedBusiness( "POULTRY BUSINESS","COMPANY FARM");
//                                    subBroilerFarm.setVisibility(View.GONE);
//                                }
//                            });
//
//                            integ.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    getSelectedBusiness( "POULTRY BUSINESS","INTEGRATION FARM");
//                                    subBroilerFarm.setVisibility(View.GONE);
//                                }
//                            });
//                        }



                        for (int i = 0; i < result.length(); i++) {
                            JSONObject object = result.getJSONObject(i);

                            model_select_sub_business item = new model_select_sub_business(
                                    object.getString("BusinessCode"),
                                    object.getString("BusinessName"),
                                    object.getInt("count")

                            );

                            sublist.add(item);


                        }

                        adapter = new Adapter_sub_business_menu(sublist,v.getContext(),Business_name,Business_code,sharedPref,activityFinish,rviewbottom,breadcrumbview,subBroilerFarm,famlistcontainer,loading,swineCompany,selectBusiness);
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
                    Globalfunction.getInstance(v.getContext()).toast(R.raw.error,t.getMessage(), Gravity.TOP|Gravity.CENTER,0,50);

                }
            }
        });
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }




    public void getSelectedBusiness(String business_name,String selected_business){
        breadcrumbview.setItems(new ArrayList<>(Arrays.asList(
                BreadcrumbItem.createSimpleItem(business_name),
                createItem(selected_business)
        )));

        breadcrumbview.setCallback(new DefaultBreadcrumbsCallback<BreadcrumbItem>() {
            @Override
            public void onNavigateBack(BreadcrumbItem item, int position) {
                if(position == 0){
                    swine_sub_businesses_list.clear();
                    subBroilerFarm.setVisibility(View.VISIBLE);
                    famlistcontainer.setVisibility(View.GONE);
                    farmList_sub.setVisibility(View.GONE);
                }
                else if(position == 1){
                    swine_sub_businesses_list.clear();
                    subBroilerFarm.setVisibility(View.GONE);
                    famlistcontainer.setVisibility(View.VISIBLE);
                    farmList_sub.setVisibility(View.GONE);
                }
                else if(position == 2){
                    Toast.makeText(mContext, "wala pa", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNavigateNewLocation(BreadcrumbItem newItem, int changedPosition) {
                Toast.makeText(mContext, newItem.getSelectedItem(), Toast.LENGTH_SHORT).show();
            }
        });
        famlistcontainer.setVisibility(View.VISIBLE);

//        SpannableString ss = new SpannableString(String.format("%s › %s", business_name, selected_business));
//        ClickableSpan clickableSpan = new ClickableSpan() {
//            @Override
//            public void onClick(View textView) {
//                titlesub_menu.setText(business_name);
//                subBroilerFarm.setVisibility(View.VISIBLE);
//            }
//            @Override
//            public void updateDrawState(TextPaint ds) {
//                super.updateDrawState(ds);
//                ds.setUnderlineText(false);
//            }
//        };
//
//
//        ss.setSpan(clickableSpan, 0, titlesub_menu.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        titlesub_menu.setText(ss);
//        titlesub_menu.setMovementMethod(LinkMovementMethod.getInstance());
//        titlesub_menu.setHighlightColor(Color.TRANSPARENT);
    }


    @Override
    public int getItemCount() {
        return newsList.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public CardView menu;
        public ImageView icon;
        public TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
             menu = itemView.findViewById(R.id.businessID);
             icon = itemView.findViewById(R.id.icon);
             title = itemView.findViewById(R.id.businessName);


        }
    }
}
