package com.razo.biosecuritychecklist.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.razo.biosecuritychecklist.Checklist_Create;
import com.razo.biosecuritychecklist.R;
import com.razo.biosecuritychecklist.func.Globalfunction;
import com.razo.biosecuritychecklist.func.ItemChecker;
import com.razo.biosecuritychecklist.func.ListItem_Checklist;
import com.razo.biosecuritychecklist.gettersetter.modal_checklist_Details;
import com.razo.biosecuritychecklist.gettersetter.modal_checklist_maintopic;


import java.util.ArrayList;

public class Adapter_Checklist extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<ListItem_Checklist> items;
    private Context context;
    private Checklist_Create checklist_create;
    private RecyclerView rv;
    private RecyclerView.Adapter adapter;


    public Adapter_Checklist(ArrayList<ListItem_Checklist> items, Context context,Checklist_Create checklist_create,RecyclerView rv,RecyclerView.Adapter adapter) {
        this.items = items;
        this.context = context;
        this.checklist_create = checklist_create;
        this.rv = rv;
        this.adapter = adapter;


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ListItem_Checklist.TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.checklist_header, parent, false);
            return  new VHSubHeader(v);
        } else if(viewType == ListItem_Checklist.TYPE_MAIN) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.checklist_item, parent, false);
            return new VHItem(v);
        }
        else if(viewType == ListItem_Checklist.TYPE_FOOTER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.checklist_footer, parent, false);
            return new VHFooter(v);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if(holder instanceof VHSubHeader) {
            modal_checklist_maintopic Header = (modal_checklist_maintopic) items.get(position);
            VHSubHeader mainTopic = (VHSubHeader)holder;
            mainTopic.sub_details_header.setText(Header.getMain_topic());

        } else if(holder instanceof VHItem) {
            modal_checklist_Details subDetails = (modal_checklist_Details) items.get(position);
            VHItem sub = (VHItem)holder;
            if(position == (getItemCount()-1)){
                sub.sub_title.setText(subDetails.getSub_des());
                sub.sub_meaning.setText(subDetails.getSub_meaning().substring(4));
                sub.layout_action.setVisibility(View.VISIBLE);



            }
            else{
                sub.layout_action.setVisibility(View.GONE);
                sub.sub_title.setText(subDetails.getSub_des());
                sub.sub_meaning.setText(subDetails.getSub_meaning().substring(4));

            }

            sub.itemGroup.setOnCheckedChangeListener((radioGroup, i) -> {
                RadioButton checked = radioGroup.findViewById(i);
                String rbcheckStatus = checked.getId() == R.id.score1 ? "1" :
                        (checked.getId() == R.id.score2 ? "2" :
                                (checked.getId() == R.id.score3 ? "3" :
                                        checked.getId() == R.id. score4 ? "4" :
                                            checked.getId() == R.id. score5 ? "5" : "0"));
                subDetails.setRadiocheck(true);
                scrollpostion(position);
                Log.d("swine",rbcheckStatus + " position:" +position);


            });


            sub.save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!subDetails.isRadiocheck()){
                        scrollpostion(position);
                        Toast.makeText(v.getContext(), "Please Finish all Form before you save.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(v.getContext(), "ok", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            sub.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Globalfunction.getInstance(v.getContext()).AlertDialog(v.getContext(),R.drawable.danger_dialog,"Cancel","Are you sure you want to Cancel this Form?",true);
                    Globalfunction.getInstance(v.getContext()).dialog_positive_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checklist_create.finish();
                        }
                    });
                    Globalfunction.getInstance(v.getContext()).dialog_negative_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Globalfunction.getInstance(v.getContext()).alert_dialog.dismiss();
                        }
                    });

                }
            });



        }
        else if(holder instanceof VHFooter) {
            VHFooter footer = (VHFooter)holder;
            footer.save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            footer.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }

    public void scrollpostion(int position){
        adapter.notifyItemInserted(position);
        rv.scrollToPosition(position);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getItemType();
    }

    class VHFooter extends RecyclerView.ViewHolder{
        public MaterialButton save,cancel;
        public VHFooter(View itemView) {
            super(itemView);
            this.save = itemView.findViewById(R.id.save);
            this.cancel = itemView.findViewById(R.id.cancel);
        }
    }

    class VHSubHeader extends RecyclerView.ViewHolder{
        public TextView sub_details_header;
        public VHSubHeader(View itemView) {
            super(itemView);
            this.sub_details_header = itemView.findViewById(R.id.sub_details_header);
        }
    }

    class VHItem extends RecyclerView.ViewHolder {
        public TextView sub_title,sub_meaning;
        public RadioGroup itemGroup;
        public MaterialRadioButton choices;
        public LinearLayout layoutdetails,layout_action;
        public MaterialButton save,cancel;
        public VHItem(View itemView) {
            super(itemView);
            this.sub_title = itemView.findViewById(R.id.sub_title);
            this.itemGroup = itemView.findViewById(R.id.itemGroup);
            this.choices = itemGroup.findViewById(itemGroup.getCheckedRadioButtonId());
            this.sub_meaning = itemView.findViewById(R.id.sub_meaning);
            this.layoutdetails = itemView.findViewById(R.id.details);
            this.layout_action = itemView.findViewById(R.id.actionbtn);
            this.save = itemView.findViewById(R.id.save);
            this.cancel = itemView.findViewById(R.id.cancel);

        }
    }
}