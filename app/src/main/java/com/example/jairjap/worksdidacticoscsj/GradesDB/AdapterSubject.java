package com.example.jairjap.worksdidacticoscsj.GradesDB;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jairjap.worksdidacticoscsj.GradesDB.UploadGrade.AdapterUpload;
import com.example.jairjap.worksdidacticoscsj.Preferences;
import com.example.jairjap.worksdidacticoscsj.R;
import com.example.jairjap.worksdidacticoscsj.SQLite.DatabaseHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AdapterSubject extends RecyclerView.Adapter<AdapterSubject.ViewHolderSubjetc>{

    private Context c;
    public ArrayList<PropertySubject> data;
    public boolean check[];
    private boolean opc;
    private DatabaseHelper myDb;
    private CustomItemClickListener listener;


    public AdapterSubject(ArrayList<PropertySubject> data, Context c, CustomItemClickListener listener){
        this.data = data;
        this.c = c;
        this.myDb = new DatabaseHelper(c);
        this.listener = listener;
        this.check = new boolean[data.size()];
        Arrays.fill(check, false);
    }

    @NonNull
    @Override
    public ViewHolderSubjetc onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_subject_db, parent, false);
        final ViewHolderSubjetc mViewHolder = new ViewHolderSubjetc(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                listener.onItemClick(v1, mViewHolder.getAdapterPosition());
            }
        });

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSubjetc holder, int position) {
        holder.subject.setText(data.get(position).getSubject());
        holder.ratingBar.setRating(Float.parseFloat(data.get(position).getPriority()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateRating(){
        Collections.sort(data);
        notifyDataSetChanged();
    }

    class ViewHolderSubjetc extends RecyclerView.ViewHolder{
        float ratingAux;
        LinearLayout linearLayout;
        CardView cardView;
        LinearLayout grade;
        RatingBar ratingBar;
        TextView subject;
        TextView grades[];

        public ViewHolderSubjetc(View itemView) {
            super(itemView);

            grades = new TextView[6];

            cardView = itemView.findViewById(R.id.cardView_gradeDB);
            linearLayout = itemView.findViewById(R.id.linearLayoutSubject);
            grade = itemView.findViewById(R.id.linearLayout_gradeDB);
            ratingBar = itemView.findViewById(R.id.ratintBarSubjetc);
            subject = itemView.findViewById(R.id.textViewSubjectName);
            grades[0] = itemView.findViewById(R.id.textViewGradePeriod1);
            grades[1] = itemView.findViewById(R.id.textViewGradePeriod2);
            grades[2] = itemView.findViewById(R.id.textViewGradePeriod3);
            grades[3] = itemView.findViewById(R.id.textViewGradePeriod4);
            grades[4] = itemView.findViewById(R.id.textViewGradePeriod5);
            grades[5] = itemView.findViewById(R.id.textViewGradePeriod6);

            setEnablePeriods(grades);

            grade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogSubjectGrade();
                }
            });
            
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    //Test changing rating bar
                   //Toast.makeText(c, rating + "", Toast.LENGTH_SHORT).show();
                }
            });
        }

        void setEnablePeriods(TextView[] grades){
            int amount = 0;
            try{
                amount = Integer.parseInt(Preferences.getString(c, Preferences.NUM_PERIODS));
            }
            catch (Exception e){}


            if(amount == 0){
                amount = 3;
            }
            for(int i = 0; i < amount; i++){
                grades[i].setVisibility(View.VISIBLE);
            }

        }


        @SuppressLint("ClickableViewAccessibility")
        void dialogSubjectGrade(){

            final Dialog dialog = new Dialog(c);
            dialog.setContentView(R.layout.dialog_add_note_db);
            ArrayList<PropertySubject> grade = new ArrayList<>();

            Button cancel = dialog.findViewById(R.id.btnCancelAddGrade);
            Button save = dialog.findViewById(R.id.btnSaveAddGrade);
            LinearLayout linearLayoutBar = dialog.findViewById(R.id.linearLayout_ratingBar);
            final Switch defaultP = dialog.findViewById(R.id.switchDefaultGrades);
            RecyclerView rvGrades = dialog.findViewById(R.id.rvGradesPeriod);
            final AdapterUpload adapter;

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(c);
            rvGrades.setLayoutManager(linearLayoutManager);

            for(int i = 0; i < 6;  i++){
                grade.add(new PropertySubject());
            }

            adapter = new AdapterUpload(grade, c);
            rvGrades.setAdapter(adapter);

            dialog.show();

            defaultP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(c, defaultP.isChecked() + "", Toast.LENGTH_SHORT).show();
                    opc = defaultP.isChecked();
                    adapter.updateState(opc);
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


        }
    }
}
