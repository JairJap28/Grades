package com.example.jairjap.worksdidacticoscsj.Subjects;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jairjap.worksdidacticoscsj.GradesDB.CustomItemClickListener;
import com.example.jairjap.worksdidacticoscsj.GradesDB.UploadGrade.AdapterUpload;
import com.example.jairjap.worksdidacticoscsj.Preferences;
import com.example.jairjap.worksdidacticoscsj.R;
import com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom.SubjectModel;
import com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom.SubjectServices;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

public class AdapterSubject extends RecyclerView.Adapter<AdapterSubject.ViewHolderSubjetc>{

    private WeakReference<Context> wContext;
    public List<SubjectModel> data;
    public boolean check[];
    private boolean opc;
    private CustomItemClickListener listener;


    public AdapterSubject(WeakReference<Context> wContext) {
        this.wContext = wContext;
    }

    public void setData(List<SubjectModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setListener(CustomItemClickListener listener) {
        this.listener = listener;
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
        holder.subject.setText(data.get(position).getName());
        holder.ratingBar.setRating(data.get(position).getPriority());

        SparseArray<Float> period_grade = data.get(position).getPeriod_grade();

        for(int i = 0; i < period_grade.size(); i++){
            holder.grades[i].setText(String.valueOf(period_grade.valueAt(i)));
        }
    }

    @Override
    public int getItemCount() {
        if(data != null){
            return data.size();
        }
        else{
            return 0;
        }
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

        ViewHolderSubjetc(View itemView) {
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
                amount = Integer.parseInt(Preferences.getString(wContext.get(), Preferences.NUM_PERIODS));
            }
            catch (Exception e){}


            if(amount == 0){
                amount = 3;
            }
            for(int i = 0; i < amount; i++){
                grades[i].setVisibility(View.VISIBLE);
            }

        }

        //this subject is for you and give the option
        //to update the grade of the period
        @SuppressLint("ClickableViewAccessibility")
        void dialogSubjectGrade(){

            final Dialog dialog = new Dialog(wContext.get());
            dialog.setContentView(R.layout.dialog_add_note_db);

            Button cancel = dialog.findViewById(R.id.btnCancelAddGrade);
            Button save = dialog.findViewById(R.id.btnSaveAddGrade);
            LinearLayout linearLayoutBar = dialog.findViewById(R.id.linearLayout_ratingBar);
            final Switch defaultP = dialog.findViewById(R.id.switchDefaultGrades);
            RecyclerView rvGrades = dialog.findViewById(R.id.rvGradesPeriod);
            final AdapterUpload adapter;

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(wContext.get());
            rvGrades.setLayoutManager(linearLayoutManager);

            adapter = new AdapterUpload(wContext);
            //set the period grades
            adapter.setPeriod_grade(data.get(getAdapterPosition()).getPeriod_grade());
            rvGrades.setAdapter(adapter);

            dialog.show();

            defaultP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(wContext.get(), defaultP.isChecked() + "", Toast.LENGTH_SHORT).show();
                    opc = defaultP.isChecked();
                    adapter.updateState(opc);
                }
            });

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SparseArray<Float> aux_perioidGrades = new SparseArray<>();
                    adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                        @Override
                        public void onChanged() {
                            super.onChanged();
                        }
                    });

                    for(int i = 0; i < )
                    updateGrades();
                    dialog.dismiss();
                    Toast.makeText(wContext.get(), wContext.get().getResources().getString(R.string.done), Toast.LENGTH_SHORT).show();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        void updateGrades(){
            SparseArray<Float> period_grades = data.get(getAdapterPosition()).getPeriod_grade();
            for(int i = 0; i < period_grades.size(); i++){
                float aux = Float.parseFloat(grades[i].getText().toString().trim());
                period_grades.setValueAt(i, aux);
            }

            /*SubjectModel subjectModel = data.get(getAdapterPosition());
            subjectModel.setPeriod_grade(period_grades);

            SubjectServices services = new SubjectServices(wContext);
            services.updateSubject(subjectModel);*/
        }
    }
}
