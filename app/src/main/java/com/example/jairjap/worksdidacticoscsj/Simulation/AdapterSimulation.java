package com.example.jairjap.worksdidacticoscsj.Simulation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jairjap.worksdidacticoscsj.R;

import java.util.ArrayList;

public class AdapterSimulation extends RecyclerView.Adapter<AdapterSimulation.ViewHolderSimulation> {

    public  ArrayList<PropertyGrade> data;
    private Context context;
    private boolean opc;

    public AdapterSimulation(Context c, ArrayList<PropertyGrade>data){
        this.data = data;
        this.context = c;
        this.opc = true;
    }

    @NonNull
    @Override
    public ViewHolderSimulation onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_simulation, parent, false);
        return new ViewHolderSimulation(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSimulation holder, final int position) {
        holder.name.setText(data.get(position).getName() + " " + (position + 1));
        holder.grade.setText(data.get(position).getGrade() + "");

        if(opc){
            holder.credits.setVisibility(View.GONE);
            holder.creditsTitle.setVisibility(View.GONE);
        }
        else{
            holder.credits.setVisibility(View.VISIBLE);
            holder.creditsTitle.setVisibility(View.VISIBLE);
        }

    }

    void addSubject(String subject){
        data.add(new PropertyGrade(subject, "0"));
        notifyItemInserted(data.size() - 1);
    }

    void percentageOOpc(boolean opc){
        this.opc = opc;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolderSimulation extends RecyclerView.ViewHolder{

        CardView cvSubjetc;
        EditText grade;
        EditText credits;
        Button delete;
        TextView creditsTitle;
        TextView name;

        public ViewHolderSimulation(View itemView) {
            super(itemView);

            cvSubjetc = itemView.findViewById(R.id.cvSimulateGrade);
            grade = itemView.findViewById(R.id.editTextGradeSimulate);
            credits = itemView.findViewById(R.id.editTextCreditSimulate);
            name = itemView.findViewById(R.id.textViewName);
            delete = itemView.findViewById(R.id.btnDeletePeriod);
            creditsTitle = itemView.findViewById(R.id.textViewCreditsSimulation);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DeleteAsyncTask(context).execute();
                    data.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });

            grade.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    data.get(getAdapterPosition()).setGrade(grade.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            credits.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    data.get(getAdapterPosition()).setCredits(credits.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }
    }
}
