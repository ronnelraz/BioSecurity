package com.razo.biosecuritychecklist.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.github.hariprasanths.bounceview.BounceView;
import com.razo.biosecuritychecklist.R;
import com.razo.biosecuritychecklist.SelectBusiness;
import com.razo.biosecuritychecklist.gettersetter.model_poultry_breeder;

import java.util.List;

public class Adapter_poultry_breeder extends RecyclerView.Adapter<Adapter_poultry_breeder.ViewHolder> {
    Context mContext;
    List<model_poultry_breeder> poultry_breederList;
    LottieAnimationView loading;
    SelectBusiness selectBusiness;


    public Adapter_poultry_breeder(Context mContext, List<model_poultry_breeder> poultry_breederList, LottieAnimationView loading, SelectBusiness selectBusiness) {
        this.mContext = mContext;
        this.poultry_breederList = poultry_breederList;
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
        final model_poultry_breeder getData = poultry_breederList.get(position);
         BounceView.addAnimTo(holder.farm);
         holder.farm_shortname.setText(getData.getShort_name());
         holder.farm_org_code.setText(getData.getOrg_code());

         /**
          * Poultry/Company/Broiler
          * Poultry/Integ/Broiler
          * Poultry/Company/Breeder
          * Poultry/Company/Hatchery
          * ***/

        String[] getMenuSelected = getData.getMenu().split("/");

         holder.farm.setOnClickListener(v -> {
             /**Broiler Company**/
             if(getMenuSelected[1].equals("Company") && getMenuSelected[2].equals("Breeder")){
                //goto vet menu
             }
         });
         
    }







    @Override
    public int getItemCount() {
        return poultry_breederList.size();

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
