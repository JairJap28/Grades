package com.example.jairjap.worksdidacticoscsj.Subjects;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jairjap.worksdidacticoscsj.GradesDB.CustomItemClickListener;
import com.example.jairjap.worksdidacticoscsj.GradesDB.UploadGrade.AdapterUpload;
import com.example.jairjap.worksdidacticoscsj.Preferences;
import com.example.jairjap.worksdidacticoscsj.R;
import com.example.jairjap.worksdidacticoscsj.Room.ScheduleRoom.ScheduleService;
import com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom.SubjectModel;
import com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom.SubjectServices;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;
import java.util.logging.Handler;

public class AdapterSubject extends RecyclerView.Adapter<AdapterSubject.ViewHolderSubjetc>{

    private WeakReference<Context> wContext;
    public List<SubjectModel> data;
    public boolean check[];
    private boolean opc;
    private CustomItemClickListener listener;

    //get the periods and its percentage to
    //calculate the final grade and needed grade
    private SparseIntArray periods_percentage;
    //max grade to calculate the needed grade
    private float max_grade;
    private float min_grade;

    private WeakReference<CoordinatorLayout> layoutWeakReference;

    public AdapterSubject(WeakReference<Context> wContext) {
        this.wContext = wContext;
    }


    //GETTER AND SETTERS
    public void setData(List<SubjectModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    void setPeriods_percentage(SparseIntArray periods_percentage) {
        this.periods_percentage = periods_percentage;
    }

    public void setMax_grade(float max_grade) {
        this.max_grade = max_grade;
    }

    void setMin_grade(float min_grade) {
        this.min_grade = min_grade;
    }

    void setListener(CustomItemClickListener listener) {
        this.listener = listener;
    }

    public void setLayoutWeakReference(WeakReference<CoordinatorLayout> layoutWeakReference) {
        this.layoutWeakReference = layoutWeakReference;
    }

    @NonNull
    @Override
    public ViewHolderSubjetc onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_subject_db, parent, false);
        final ViewHolderSubjetc mViewHolder = new ViewHolderSubjetc(v);
        mViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
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

        //set only two decimals
        String out = String.format("%.2f", (data.get(position).getGrade_needed()));
        holder.final_grade.setText(String.valueOf(out));

        if(data.get(position).getGrade_needed() < min_grade){
            out = String.format("%.2f", (min_grade - data.get(position).getGrade_needed()));
            holder.required_grade.setText(String.valueOf(out));
        }
        else{
            holder.required_grade.setText("0.0");
        }
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }

    class ViewHolderSubjetc extends RecyclerView.ViewHolder{
        float ratingAux;
        LinearLayout linearLayout;
        CardView cardView;
        LinearLayout grade;
        RatingBar ratingBar;
        TextView subject;
        TextView grades[];
        TextView required_grade;
        TextView final_grade;

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

            required_grade = itemView.findViewById(R.id.textViewNeededGrade);
            final_grade = itemView.findViewById(R.id.textViewTotalGrade);

            setEnablePeriods(grades);

            grade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogOptions();
                }
            });

            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    data.get(getAdapterPosition()).setPriority(rating);
                    //updateRating();
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

        void updateRating(){
            Collections.sort(data);
            notifyDataSetChanged();
        }

        //this subject is for you and give the option
        //to update the grade of the period
        @SuppressLint("ClickableViewAccessibility")
        void dialogSubjectGrade(){

            class Foo{
                void updateGrades(){
                    float grade_needed = 0;
                    //get the grades in this variable
                    SparseArray<Float> aux_grades = data.get(getAdapterPosition()).getPeriod_grade();

                    //iterate trought the periods
                    for(int i = 0; i < periods_percentage.size(); i++){
                        //calculate de final grade
                        grade_needed += (periods_percentage.valueAt(i) * aux_grades.valueAt(i)) / 100.0;
                    }

                    SubjectModel subjectModel = data.get(getAdapterPosition());
                    subjectModel.setPeriod_grade(data.get(getAdapterPosition()).getPeriod_grade());
                    subjectModel.setGrade_needed(grade_needed);

                    SubjectServices services = new SubjectServices(wContext);
                    services.updatePeriodGrade(subjectModel);
                }
            }

            final Dialog dialog = new Dialog(wContext.get());
            dialog.setContentView(R.layout.dialog_add_note_db);

            Button cancel = dialog.findViewById(R.id.btnCancelAddGrade);
            Button save = dialog.findViewById(R.id.btnSaveAddGrade);
            LinearLayout linearLayoutBar = dialog.findViewById(R.id.linearLayout_ratingBar);
            LinearLayout container = dialog.findViewById(R.id.linearLayout_container_period_grades);
            RecyclerView rvGrades = dialog.findViewById(R.id.rvGradesPeriod);
            final AdapterUpload adapter;

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(wContext.get());
            rvGrades.setLayoutManager(linearLayoutManager);

            adapter = new AdapterUpload(wContext);
            //set the period grades
            adapter.setPeriod_grade(data.get(getAdapterPosition()).getPeriod_grade());
            rvGrades.setAdapter(adapter);

            Animation animation1 = AnimationUtils.loadAnimation(wContext.get(), R.anim.lefttoright);
            container.startAnimation(animation1);
            dialog.show();
            //this is to set the background transparent
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Foo foo = new Foo();
                    foo.updateGrades();
                    Animation animation = AnimationUtils.loadAnimation(wContext.get(), R.anim.fadeout);
                    animation.setDuration(1000);
                    animation.setFillAfter(true);
                    hideDialog(dialog, 1000,false);
                    container.startAnimation(animation);
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation animation = AnimationUtils.loadAnimation(wContext.get(), R.anim.righttoleft);
                    animation.setDuration(1000);
                    animation.setFillAfter(true);
                    container.startAnimation(animation);
                    hideDialog(dialog, 1000,false);
                }
            });
        }

        //this dialog is to show the options
        void dialogOptions(){
            Dialog dialog = new Dialog(wContext.get());
            dialog.setContentView(R.layout.dialog_options_subject);

            RelativeLayout container = dialog.findViewById(R.id.relativeLayout_container_ops_subjets);

            LinearLayout btn_close = dialog.findViewById(R.id.linearLayout_btn_close);
            LinearLayout btn_delete = dialog.findViewById(R.id.linearLayout_btn_delete_subject);
            LinearLayout btn_edit = dialog.findViewById(R.id.linearLayout_btn_edit_subject);

            CoordinatorLayout coordinatorLayout = dialog.findViewById(R.id.coordinaterLayout_subjects);

            Animation animation = AnimationUtils.loadAnimation(wContext.get(), R.anim.lefttoright);
            container.startAnimation(animation);
            dialog.show();
            //this is to set the background transparent
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


            btn_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation animation = AnimationUtils.loadAnimation(wContext.get(), R.anim.righttoleft);
                    animation.setDuration(1000);
                    container.startAnimation(animation);
                    hideDialog(dialog, 1000,false);
                }
            });

            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation animation = AnimationUtils.loadAnimation(wContext.get(), R.anim.righttoleft);
                    animation.setDuration(1000);
                    animation.setFillAfter(true);
                    container.startAnimation(animation);
                    hideDialog(dialog, 1000,true);
            }
            });

            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation animation = AnimationUtils.loadAnimation(wContext.get(), R.anim.zoomout);
                    animation.setDuration(400);
                    container.startAnimation(animation);
                    hideDialog(dialog, 400,false);
                    showAlertDialog();
                }
            });

        }

        void showAlertDialog(){
            AlertDialog.Builder aBuilder = new AlertDialog.Builder(wContext.get());
            //Set message to the diallog
            aBuilder.setMessage(wContext.get().getResources().getString(R.string.are_you_sure_delete));
            aBuilder.setPositiveButton(wContext.get().getResources().getString(R.string.yes),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog_interface, int which) {
                            SubjectServices services = new SubjectServices(wContext);
                            services.deleteSubject(data.get(getAdapterPosition()));

                            ScheduleService service =  new ScheduleService();
                            service.delteById(data.get(getAdapterPosition()).getId());

                            data.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            showSnackBar(String.valueOf(wContext.get().getResources().getString(R.string.subject_deleted_successfully)));
                        }
                    });

            aBuilder.setNegativeButton(wContext.get().getResources().getString(R.string.no),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog_interface, int which) {
                            showSnackBar(String.valueOf(wContext.get().getResources().getString(R.string.be_careful)));
                        }
                    });

            AlertDialog alertDialog = aBuilder.create();
            alertDialog.show();
        }

        void hideDialog(Dialog dialog,long sleepTime, boolean flag){
            //if flag = true, that means we have to open the new dialog
            new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        Thread.sleep(sleepTime);
                        ((Activity) wContext.get()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.hide();
                                if(flag){
                                    dialogSubjectGrade();
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        ((Activity) wContext.get()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.hide();
                            }
                        });
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        void showSnackBar(String message){
            Snackbar.make(layoutWeakReference.get(),
                    message,
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
    }
}
