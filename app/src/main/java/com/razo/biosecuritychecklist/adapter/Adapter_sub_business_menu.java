package com.razo.biosecuritychecklist.adapter;

import static com.razo.biosecuritychecklist.func.Globalfunction.createItem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.core.util.PairKt;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.github.hariprasanths.bounceview.BounceView;
import com.razo.biosecuritychecklist.ActivityFinish;
import com.razo.biosecuritychecklist.R;
import com.razo.biosecuritychecklist.SelectBusiness;
import com.razo.biosecuritychecklist.conn.API;
import com.razo.biosecuritychecklist.data.data_poultry_business;
import com.razo.biosecuritychecklist.func.Globalfunction;
import com.razo.biosecuritychecklist.func.SharedPref;
import com.razo.biosecuritychecklist.gettersetter.model_poultry_breeder;
import com.razo.biosecuritychecklist.gettersetter.model_select_sub_business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import moe.feng.common.view.breadcrumbs.BreadcrumbsView;
import moe.feng.common.view.breadcrumbs.DefaultBreadcrumbsCallback;
import moe.feng.common.view.breadcrumbs.model.BreadcrumbItem;

public class Adapter_sub_business_menu extends RecyclerView.Adapter<Adapter_sub_business_menu.ViewHolder> {
    Context mContext;
    List<model_select_sub_business> newsList;
    String Business_name;
    String Business_code;


 
    SharedPref sharedPref;
    ActivityFinish activityFinish;
    RecyclerView recyclerView,swineCompany;
    RecyclerView.Adapter adapter;
    List<model_poultry_breeder> poultry_breederList = new ArrayList<>();
    BreadcrumbsView title_subheader;
    LinearLayout subBroilerFarm;
    LinearLayout famlistcontainer;
    LottieAnimationView loading;
    SelectBusiness selectBusiness;



    public Adapter_sub_business_menu(List<model_select_sub_business> list, Context context,
                                     String Business_name,
                                     String Business_code,
                                     SharedPref sharedPref,
                                     ActivityFinish finish,
                                     RecyclerView recyclerView,
                                     BreadcrumbsView title_subheader,
                                     LinearLayout subBroilerFarm,
                                     LinearLayout famlistcontainer,
                                     LottieAnimationView loading,
                                     RecyclerView swineCompany,
                                     SelectBusiness selectBusiness) {
        super();
        this.newsList = list;
        this.mContext = context;
        this.Business_name = Business_name;
        this.Business_code = Business_code;
        this.sharedPref = sharedPref;
        this.activityFinish = finish;
        this.recyclerView = recyclerView;
        this.title_subheader = title_subheader;
        this.subBroilerFarm = subBroilerFarm;
        this.famlistcontainer = famlistcontainer;
        this.loading = loading;
        this.swineCompany = swineCompany;
        this.selectBusiness = selectBusiness;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_bsuiness,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final model_select_sub_business getData = newsList.get(position);
        BounceView.addAnimTo(holder.subBusinessID);
        holder.subBusinessID.startAnimation(Globalfunction.getInstance(mContext).bouce());
        holder.title.setText(getData.getBusinessName());

//        Toast.makeText(mContext, Business_code, Toast.LENGTH_SHORT).show();
        if (Business_code.equals("POULTRY")) {
            holder.icons.setImageResource(R.drawable.poultry_);
        }
        else if (Business_code.equals("SWINE")){
            holder.icons.setImageResource(R.drawable.swine_);
        }



        holder.subBusinessID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String[] getSub_businessCode = getData.getBusinessCode().split("_");

                if(getSub_businessCode[0].equals("PTBRO")){

                    recyclerView.setVisibility(View.GONE);
                    subBroilerFarm.setVisibility(View.VISIBLE);
                    title_subheader.setAnimation(Globalfunction.getInstance(v.getContext()).fadeIn());

                    CardView company = (CardView) subBroilerFarm.getChildAt(0);
                    CardView integ = (CardView) subBroilerFarm.getChildAt(1);

                    BounceView.addAnimTo(company);
                    BounceView.addAnimTo(integ);

                    company.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            subBroilerFarm.setVisibility(View.GONE);
                            famlistcontainer.setVisibility(View.VISIBLE);

                            title_subheader.setItems(new ArrayList<>(Arrays.asList(
                                    BreadcrumbItem.createSimpleItem(Business_name),
                                    createItem(getData.getBusinessName()),
                                    createItem("COMPANY FARM")
                            )));
                            new data_poultry_business(mContext,swineCompany,adapter,poultry_breederList,subBroilerFarm,famlistcontainer,loading,selectBusiness)
                                    .loadDataPoultry_breeder(API.getClient().Poultry_broilerCompany_Business(),"Poultry/Company/Broiler");


                        }
                    });
                    integ.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            subBroilerFarm.setVisibility(View.GONE);
                            famlistcontainer.setVisibility(View.VISIBLE);
                            title_subheader.setItems(new ArrayList<>(Arrays.asList(
                                    BreadcrumbItem.createSimpleItem(Business_name),
                                    createItem(getData.getBusinessName()),
                                    createItem("INTEGRATION")
                            )));
                            new data_poultry_business(mContext,swineCompany,adapter,poultry_breederList,subBroilerFarm,famlistcontainer,loading,selectBusiness)
                                    .loadDataPoultry_breeder(API.getClient().Poultry_broilerInteg_Business(),"Poultry/Integ/Broiler");

                        }
                    });




                    title_subheader.setItems(new ArrayList<>(Arrays.asList(
                            BreadcrumbItem.createSimpleItem(Business_name),
                            createItem(getData.getBusinessName())
                    )));

                    title_subheader.setCallback(new DefaultBreadcrumbsCallback<BreadcrumbItem>() {
                        @Override
                        public void onNavigateBack(BreadcrumbItem item, int position) {
                                if(position == 0){
                                    recyclerView.setVisibility(View.VISIBLE);
                                    BounceView.addAnimTo(recyclerView);
                                    subBroilerFarm.setVisibility(View.GONE);
                                    famlistcontainer.setVisibility(View.GONE);
                                }
                                else if(position == 1){
                                    recyclerView.setVisibility(View.GONE);
                                    famlistcontainer.setVisibility(View.GONE);
                                    subBroilerFarm.setVisibility(View.VISIBLE);
                                    BounceView.addAnimTo(subBroilerFarm);
                                }


                        }

                        @Override
                        public void onNavigateNewLocation(BreadcrumbItem newItem, int changedPosition) {
                            Toast.makeText(mContext, newItem.getSelectedItem(), Toast.LENGTH_SHORT).show();
                        }
                    });

//                    SpannableString ss = new SpannableString(String.format("%s › %s", Business_name, getData.getBusinessName()));
//                    ClickableSpan clickableSpan = new ClickableSpan() {
//                        @Override
//                        public void onClick(View textView) {
//                            title_subheader.setText(Business_name);
//                            recyclerView.setVisibility(View.VISIBLE);
//                            subBroilerFarm.setVisibility(View.GONE);
//                        }
//                        @Override
//                        public void updateDrawState(TextPaint ds) {
//                            super.updateDrawState(ds);
//                            ds.setUnderlineText(false);
//                        }
//                    };
//
//
//                    ss.setSpan(clickableSpan, 0, title_subheader.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                    title_subheader.setText(ss);
//                    title_subheader.setMovementMethod(LinkMovementMethod.getInstance());
//                    title_subheader.setHighlightColor(Color.TRANSPARENT);
                }
                else{

                    subBroilerFarm.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    famlistcontainer.setVisibility(View.VISIBLE);
                    title_subheader.setItems(new ArrayList<>(Arrays.asList(
                            BreadcrumbItem.createSimpleItem(Business_name),
                            createItem(getData.getBusinessName())
                    )));

                    title_subheader.setCallback(new DefaultBreadcrumbsCallback<BreadcrumbItem>() {
                        @Override
                        public void onNavigateBack(BreadcrumbItem item, int position) {
                            if(position == 0){
                                BounceView.addAnimTo(recyclerView);
                                recyclerView.setVisibility(View.VISIBLE);
                                subBroilerFarm.setVisibility(View.GONE);
                                famlistcontainer.setVisibility(View.GONE);
                            }
                            else if(position == 1){
                                recyclerView.setVisibility(View.GONE);
                                famlistcontainer.setVisibility(View.GONE);
                                subBroilerFarm.setVisibility(View.VISIBLE);
                                BounceView.addAnimTo(subBroilerFarm);
                            }


                        }

                        @Override
                        public void onNavigateNewLocation(BreadcrumbItem newItem, int changedPosition) {
                            Toast.makeText(mContext, newItem.getSelectedItem(), Toast.LENGTH_SHORT).show();
                        }
                    });


                    if(getData.getBusinessCode().split("_")[0].equals("PTBRE")){
                        new data_poultry_business(mContext,swineCompany,adapter,poultry_breederList,subBroilerFarm,famlistcontainer,loading,selectBusiness)
                                .loadDataPoultry_breeder(API.getClient().Poultry_breeder_Business(),"Poultry/Company/Breeder");
                    }
                    else if(getData.getBusinessCode().split("_")[0].equals("PTHT")){
                        new data_poultry_business(mContext,swineCompany,adapter,poultry_breederList,subBroilerFarm,famlistcontainer,loading,selectBusiness)
                                .loadDataPoultry_breeder(API.getClient().Poultry_hatchery_Business(),"Poultry/Company/Hatchery");
                    }




//                sharedPref.setBusiness_selected("true");
//                sharedPref.setSub_Business_name(getData.getBusinessName());
//                sharedPref.setSub_Business_code(getData.getBusinessCode());
//                sharedPref.setSub_Business_selected("true");
//                Globalfunction.getInstance(v.getContext()).intent(Home.class,v.getContext());
//                activityFinish.onFinish();

                }



            }
        });



    }


    public static TextView createLink(TextView targetTextView, String completeString,
                                      String partToClick, ClickableSpan clickableAction) {

        SpannableString spannableString = new SpannableString(completeString);

        // make sure the String is exist, if it doesn't exist
        // it will throw IndexOutOfBoundException
        int startPosition = completeString.indexOf(partToClick);
        int endPosition = completeString.lastIndexOf(partToClick) + partToClick.length();

        spannableString.setSpan(clickableAction, startPosition, endPosition,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        targetTextView.setText(spannableString);
        targetTextView.setMovementMethod(LinkMovementMethod.getInstance());

        return targetTextView;
    }


    @Override
    public int getItemCount() {
        return newsList.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public CardView subBusinessID;
        public ImageView icons;
        public TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            subBusinessID = itemView.findViewById(R.id.subBusinessID);
            title = itemView.findViewById(R.id.subbusinessName);
            icons = itemView.findViewById(R.id.icons);

        }
    }
}
