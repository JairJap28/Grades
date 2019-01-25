package com.example.jairjap.worksdidacticoscsj.MainPack;

import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.Toast;

import com.example.jairjap.worksdidacticoscsj.GradesDB.Schedule.AdapterSchedule;
import com.example.jairjap.worksdidacticoscsj.GradesDB.Schedule.PropertySchedule;
import com.example.jairjap.worksdidacticoscsj.R;
import com.example.jairjap.worksdidacticoscsj.Room.ScheduleRoom.ScheduleModel;
import com.example.jairjap.worksdidacticoscsj.Room.SettingsRoom.SettingsModel;
import com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom.SubjectModel;
import com.example.jairjap.worksdidacticoscsj.SettingsPack.Settings;
import com.example.jairjap.worksdidacticoscsj.Simulation.Simulation;
import com.example.jairjap.worksdidacticoscsj.Subjects.Subject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Grade extends AppCompatActivity {

    private RecyclerView rvDays;
    private ArrayList<PropertySchedule> days;
    private boolean[] controlDays;
    private AdapterSchedule adapter;

    private GradeViewModel gradeViewModel;

    //this is if we want to add a new
    //seubject mark the switch schedule
    private boolean createSchedule;
    private float max_grade;
    private int amount_periods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView cvAdd = findViewById(R.id.addSubjectCardView);
        CardView cvSimulate = findViewById(R.id.cvSimulate);
        CardView cvShow = findViewById(R.id.cvShow);
        CardView cvSettings = findViewById(R.id.cvSetting);

        gradeViewModel = ViewModelProviders.of(this).get(GradeViewModel.class);

        cvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddSubject(new WeakReference<>(Grade.this));
            }
        });

        cvSimulate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSimulate(Grade.this);
            }
        });

        cvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Grade.this, Subject.class);
                startActivity(i);
            }
        });

        cvSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Grade.this, Settings.class);
                startActivity(i);
            }
        });


        //OBSERVERS FOR LIVE DATA
        gradeViewModel.getSchedule().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean != null){
                    createSchedule = aBoolean;
                }
            }
        });

        gradeViewModel.getMaxGrade().observe(this, new Observer<Float>() {
            @Override
            public void onChanged(@Nullable Float aFloat) {
                if(aFloat != null){
                    //the minimum grade must be greather tha 3
                    max_grade = (aFloat > 3.0f) ? aFloat: 5.0f;
                }
            }
        });

        gradeViewModel.getPercentagePeriods().observe(this, new Observer<SparseIntArray>() {
            @Override
            public void onChanged(@Nullable SparseIntArray sparseIntArray) {
                if(sparseIntArray != null){
                    //verify if the array is empty
                    amount_periods = (sparseIntArray.size() > 0) ? sparseIntArray.size() : 3;
                }
            }
        });
    }


    void dialogAddSubject(final WeakReference<Activity> activity){

        days = new ArrayList<>();
        controlDays = new boolean[7];
        Arrays.fill(controlDays, false);

        final Dialog dialog = new Dialog(activity.get());
        dialog.setContentView(R.layout.add_grade);

        final EditText subjetc = dialog.findViewById(R.id.editTextSubject);
        final EditText teacher = dialog.findViewById(R.id.editTextTeacher);
        final EditText credits = dialog.findViewById(R.id.editTextCredits);
        Button save = dialog.findViewById(R.id.btnSaveAddSubject);
        Button cancel = dialog.findViewById(R.id.btnCancelAddSubject);
        final CheckBox aux[] = new CheckBox[6];
        aux[0] = dialog.findViewById(R.id.checkBoxMonday);
        aux[1] = dialog.findViewById(R.id.checkBoxTuesday);
        aux[2] = dialog.findViewById(R.id.checkBoxWednesday);
        aux[3] = dialog.findViewById(R.id.checkBoxThursday);
        aux[4] = dialog.findViewById(R.id.checkBoxFriday);
        aux[5] = dialog.findViewById(R.id.checkBoxSaturday);
        final Switch switchSchedule = dialog.findViewById(R.id.switchSchedule);
        final LinearLayout layoutDays = dialog.findViewById(R.id.linearLayoutDays);
        final LinearLayout layoutTeacher = dialog.findViewById(R.id.linearLayoutTeacherCredits);
        rvDays = dialog.findViewById(R.id.rvDaysSchedule);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvDays.setLayoutManager(linearLayoutManager);

        //Anonymous class to use funtions many times
        class Foo{
            private void setSchedule(){
                controlDays[6] = switchSchedule.isChecked();
                if(controlDays[6]){
                    layoutDays.setVisibility(View.VISIBLE);
                    layoutTeacher.setVisibility(View.GONE);
                    rvDays.setVisibility(View.VISIBLE);
                }
                else{
                    //restarCheckBox(aux);
                    //days.clear();
                    //adapter.removeAll();
                    layoutDays.setVisibility(View.GONE);
                    layoutTeacher.setVisibility(View.VISIBLE);
                    rvDays.setVisibility(View.GONE);
                }
            }

            //This is because the user starts with the
            //schedule layout active
            private void activeScheduleCreation(){
                layoutDays.setVisibility(View.VISIBLE);
                rvDays.setVisibility(View.VISIBLE);
            }

            private void saveSubject(){
                SubjectModel subjectModel = new SubjectModel();
                subjectModel.setName(subjetc.getText().toString().trim());
                subjectModel.setTeacher_name(teacher.getText().toString().trim());
                subjectModel.setCant_credits(Integer.parseInt(credits.getText().toString().trim()));
                //needed grade = max_grade because there are no any grades
                subjectModel.setGrade_needed(max_grade);
                subjectModel.setPriority(1.0f);

                //set the amount of periods and init it with 0.0
                SparseArray<Float> auxGrades = new SparseArray<>();
                for(int i = 0; i < amount_periods; i++){
                    auxGrades.put(i, 0.0f);
                }

                subjectModel.setPeriod_grade(auxGrades);
                subjectModel.setId((int) (Math.random() * Integer.MAX_VALUE + 1));

                //the key will be the index of the day
                //the value will be the hout
                SparseArray<String> auxDays = new SparseArray<>();
                for(PropertySchedule p: days){
                    auxDays.put(p.getIndex() + 1, p.getHour());
                }

                ScheduleModel scheduleModel = new ScheduleModel();
                scheduleModel.setId(subjectModel.getId());
                scheduleModel.setName(subjectModel.getName());
                scheduleModel.setTeacher_name(subjectModel.getTeacher_name());
                scheduleModel.setDay(auxDays);

                //insert it
                gradeViewModel.insertSubject(subjectModel, scheduleModel);

                //close dialog
                dialog.dismiss();
                Toast.makeText(Grade.this, Grade.this.getResources().getString(R.string.subject_added_successfull),
                        Toast.LENGTH_SHORT).show();
            }

            private void checkBoxControl(CheckBox c, int pos, boolean [] controlDays, String day){
                controlDays[pos] = c.isChecked();
                if(controlDays[pos]){
                    days.add(new PropertySchedule(day, pos));
                    Collections.sort(days);
                    adapter = new AdapterSchedule(new WeakReference<>(Grade.this), days);
                    rvDays.setAdapter(adapter);
                }
                else{
                    int index = getIndex(pos);
                    adapter.remove(index);
                }
            }
        }
        Foo foo = new Foo();
        //Show the dialog
        dialog.show();

        //set the stored settings
        switchSchedule.setChecked(createSchedule);
        if(createSchedule){
            //if the user already defined to create scedule
            //activate the layout
            foo.activeScheduleCreation();
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subjetc.getText().toString().equals("") || subjetc.getText().length() == 0){
                    Toast.makeText(activity.get(), "Insert the name of the subject", Toast.LENGTH_SHORT).show();
                }
                else{
                    foo.saveSubject();
                }
            }
        });


        aux[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foo.checkBoxControl(aux[0], 0, controlDays,
                        getResources().getString(R.string.monday));
            }
        });

        aux[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foo.checkBoxControl(aux[1], 1, controlDays, getResources().getString(R.string.tuesday));
            }
        });

        aux[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foo.checkBoxControl(aux[2], 2, controlDays, getResources().getString(R.string.wednesday));
            }
        });

        aux[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foo.checkBoxControl(aux[3], 3, controlDays, getResources().getString(R.string.thursday));
            }
        });

        aux[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foo.checkBoxControl(aux[4], 4, controlDays, getResources().getString(R.string.friday));
            }
        });

        aux[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foo.checkBoxControl(aux[5], 5, controlDays, getResources().getString(R.string.saturday));
            }
        });

        switchSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foo.setSchedule();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });
    }

    void restarCheckBox(CheckBox[] a){
        for(int i = 0; i < 6; i++){
            controlDays[i] = false;
            a[i].setChecked(false);
        }
    }

    int getIndex(int pos){
        for(int i = 0; i < days.size(); i++){
            if(days.get(i).getIndex() == pos) return i;
        }
        return -1;
    }

    void dialogSimulate(final Activity activity){

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_simulate_cant_subjects);

        final NumberPicker numberPicker = dialog.findViewById(R.id.numberPickerCantSubjects);
        Button cancel = dialog.findViewById(R.id.btnCancelNumberSubjectSimulation);
        final Button start = dialog.findViewById(R.id.btnStartSimulation);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(6);
        numberPicker.setWrapSelectorWheel(true);

        dialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();;
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Grade.this, Simulation.class);
                i.putExtra("subjects", numberPicker.getValue());
                dialog.hide();
                startActivity(i);
            }
        });
    }

}
