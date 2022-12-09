package com.razo.biosecuritychecklist.adapter;

import static com.razo.biosecuritychecklist.func.Globalfunction.createItem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.github.hariprasanths.bounceview.BounceView;
import com.razo.biosecuritychecklist.R;
import com.razo.biosecuritychecklist.SelectBusiness;
import com.razo.biosecuritychecklist.data.data_integ_sub_business;
import com.razo.biosecuritychecklist.gettersetter.model_swine_business;
import com.razo.biosecuritychecklist.gettersetter.model_swine_sub_business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import moe.feng.common.view.breadcrumbs.BreadcrumbsView;
import moe.feng.common.view.breadcrumbs.model.BreadcrumbItem;

public class Adapter_swine_integ_sub extends RecyclerView.Adapter<Adapter_swine_integ_sub.ViewHolder> {
    Context mContext;
    List<model_swine_sub_business> swine_sub_businesses_list;
    LottieAnimationView loading;
    SelectBusiness selectBusiness;


    public Adapter_swine_integ_sub(Context mContext, List<model_swine_sub_business> swine_sub_businesses_list, LottieAnimationView loading, SelectBusiness selectBusiness) {
        this.mContext = mContext;
        this.swine_sub_businesses_list = swine_sub_businesses_list;
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
        final model_swine_sub_business getData = swine_sub_businesses_list.get(position);
         BounceView.addAnimTo(holder.farm);
         holder.farm_shortname.setText(getData.getFarm_name());
         holder.farm_org_code.setText(getData.getFarm_code());
         
    }







    @Override
    public int getItemCount() {
        return swine_sub_businesses_list.size();

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
