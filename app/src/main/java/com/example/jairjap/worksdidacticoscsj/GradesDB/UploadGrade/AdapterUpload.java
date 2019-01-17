package com.example.jairjap.worksdidacticoscsj.GradesDB.UploadGrade;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.jairjap.worksdidacticoscsj.GradesDB.PropertySubject;
import com.example.jairjap.worksdidacticoscsj.R;

import java.util.ArrayList;

public class AdapterUpload extends RecyclerView.Adapter<AdapterUpload.ViewHolder> {

    private Context c;
    private ArrayList<PropertySubject> data;
    private boolean opc;

    public AdapterUpload(ArrayList<PropertySubject> data, Context c){
        this.c = c;
        this.data = data;
        this.opc = true;
    }

    @NonNull
    @Override
    public AdapterUpload.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_grade_period, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUpload.ViewHolder holder, int position) {
        if(opc){
            holder.per.setVisibility(View.GONE);
        }
        else{
            holder.per.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateState(boolean opc){
        this.opc = opc;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        EditText grade;
        EditText percentage;
        LinearLayout per;

        public ViewHolder(View itemView) {
            super(itemView);

            grade = itemView.findViewById(R.id.editTextGradePeriod);
            percentage = itemView.findViewById(R.id.editTextPercentagePeriod);
            per = itemView.findViewById(R.id.linearLayoutPercentage);
        }
    }
}
