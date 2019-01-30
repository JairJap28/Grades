package com.example.jairjap.worksdidacticoscsj.GradesDB.UploadGrade;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.jairjap.worksdidacticoscsj.GradesDB.PropertySubject;
import com.example.jairjap.worksdidacticoscsj.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class AdapterUpload extends RecyclerView.Adapter<AdapterUpload.ViewHolder> {

    private Context c;
    private SparseArray<Float> period_grade;
    private boolean opc;

    public AdapterUpload(WeakReference<Context> wContext){
        this.c = wContext.get();
        this.opc = true;
    }

    public void setPeriod_grade(SparseArray<Float> period_grade) {
        this.period_grade = period_grade;
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

        holder.grade.setText(String.valueOf(period_grade.valueAt(position)));
    }

    @Override
    public int getItemCount() {
        return period_grade.size();
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

            grade.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try{
                        float grade = Float.parseFloat(s.toString());
                        period_grade.setValueAt(getAdapterPosition(), grade);
                    }catch (Exception e){

                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    try{
                        float grade = Float.parseFloat(s.toString());
                        period_grade.setValueAt(getAdapterPosition(), grade);
                    }catch (Exception e){

                    }
                }
            });

        }
    }
}
