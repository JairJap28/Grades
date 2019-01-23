package com.example.jairjap.worksdidacticoscsj.Subjects;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.jairjap.worksdidacticoscsj.GradesDB.CustomItemClickListener;
import com.example.jairjap.worksdidacticoscsj.GradesDB.PropertySubject;
import com.example.jairjap.worksdidacticoscsj.R;
import com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom.SubjectModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Subject extends AppCompatActivity {

    private RecyclerView rv;
    private AdapterSubject adapter;
    private ArrayList<PropertySubject> data;

    private SubjectViewModel subjectViewModel;

    public boolean btnDoneUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        btnDoneUpdate = false;

        rv = findViewById(R.id.rvSubjectsDB);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);

        data = new ArrayList<>();

        subjectViewModel = ViewModelProviders.of(this).get(SubjectViewModel.class);

        subjectViewModel.getAllSubjects().observe(this, new Observer<List<SubjectModel>>() {
            @Override
            public void onChanged(@Nullable List<SubjectModel> subjectModels) {
                if (subjectModels != null) {
                    //Sort the subjects
                    Collections.sort(subjectModels);
                    adapter.setData(subjectModels);
                }
            }
        });

        //Be carefull, I don't know why this is for
        CustomItemClickListener itemClickListener = new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(Subject.this, data.get(position).getSubject(), Toast.LENGTH_SHORT).show();
            }
        };

        adapter = new AdapterSubject(this);
        adapter.setListener(itemClickListener);
        rv.setAdapter(adapter);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return btnDoneUpdate;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_cancel:
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_done:
                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }

    }
}