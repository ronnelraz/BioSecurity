package com.razo.biosecuritychecklist.adapter;

import static com.razo.biosecuritychecklist.func.Globalfunction.createItem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.github.hariprasanths.bounceview.BounceView;
import com.razo.biosecuritychecklist.Home;
import com.razo.biosecuritychecklist.R;
import com.razo.biosecuritychecklist.SelectBusiness;
import com.razo.biosecuritychecklist.data.data_integ_sub_business;
import com.razo.biosecuritychecklist.func.SharedPref;
import com.razo.biosecuritychecklist.gettersetter.model_swine_business;
import com.razo.biosecuritychecklist.gettersetter.model_swine_sub_business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import moe.feng.common.view.breadcrumbs.BreadcrumbsView;
import moe.feng.common.view.breadcrumbs.model.BreadcrumbItem;

public class Adapter_swine_company extends RecyclerView.Adapter<Adapter_swine_company.ViewHolder> {
    Context mContext;
    List<model_swine_business> newsList;
    BreadcrumbsView title_subheader;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<model_swine_sub_business> swine_sub_businesses_list;
    LinearLayout farmList_sub,famlistcontainer;
    LottieAnimationView loading;
    SelectBusiness selectBusiness;


    public Adapter_swine_company(
            List<model_swine_business> list,
            Context context,
            BreadcrumbsView title_subheader,
            RecyclerView recyclerView,
            RecyclerView.Adapter adapter,
            List<model_swine_sub_business> swine_sub_businesses_list,
            LinearLayout farmList_sub,
            LinearLayout famlistcontainer,
            LottieAnimationView loading,
            SelectBusiness selectBusiness) {

        super();
        this.newsList = list;
        this.mContext = context;
        this.title_subheader = title_subheader;
        this.recyclerView = recyclerView;
        this.adapter = adapter;
        this.swine_sub_businesses_list = swine_sub_businesses_list;
        this.farmList_sub = farmList_sub;
        this.famlistcontainer = famlistcontainer;
        this.loading = loading;
        this.selectBusiness = selectBusiness;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_farmlist,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final model_swine_business getData = newsList.get(position);
         BounceView.addAnimTo(holder.farm);
         holder.farm_shortname.setText(getData.getShort_name());
         holder.farm_org_code.setText(getData.getOrg_code());

         holder.farm.setOnClickListener(v -> {
             if(getData.business_type.equals("Company")){
                 SharedPref.getInstance(v.getContext()).setSub_Business_name("SWINE/COMPANY/"+getData.getShort_name());
//                 new Home().business.setText("SWINE/COMPANY/"+getData.getShort_name());
                 selectBusiness.finish();

             }
             else{
                 farmList_sub.setVisibility(View.VISIBLE);
                 famlistcontainer.setVisibility(View.GONE);
                 title_subheader.setItems(new ArrayList<>(Arrays.asList(
                         BreadcrumbItem.createSimpleItem("SWINE BUSINESS"),
                         createItem("INTEGRATION"),
                         createItem(getData.getShort_name().toUpperCase(Locale.ROOT).split("-")[1])
                 )));
                 swine_sub_businesses_list.clear();
                 new data_integ_sub_business(mContext,recyclerView,adapter,swine_sub_businesses_list,farmList_sub,famlistcontainer,selectBusiness)
                         .loadDataInteg(v,loading,recyclerView,getData.getOrg_code());

             }
         });


    }







    @Override
    public int getItemCount() {
        return newsList.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public CardView farm;
        public TextView farm_shortname,farm_org_code;

        public ViewHolder(View itemView) {
            super(itemView);
            farm = itemView.findViewById(R.id.farm);
            farm_shortname = itemView.findViewById(R.id.farm_shortname);
            farm_org_code = itemView.findViewById(R.id.farm_org_code);


        }
    }
}
