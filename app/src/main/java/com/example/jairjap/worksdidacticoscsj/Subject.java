package com.example.jairjap.worksdidacticoscsj;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.jairjap.worksdidacticoscsj.GradesDB.AdapterSubject;
import com.example.jairjap.worksdidacticoscsj.GradesDB.CustomItemClickListener;
import com.example.jairjap.worksdidacticoscsj.GradesDB.PropertySubject;
import com.example.jairjap.worksdidacticoscsj.SQLite.DatabaseHelper;

import java.util.ArrayList;
import java.util.Collections;

public class Subject extends AppCompatActivity {

    private RecyclerView rv;
    private AdapterSubject adapter;
    private ArrayList<PropertySubject> data;
    private DatabaseHelper myDB;

    public boolean btnDoneUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        btnDoneUpdate = false;

        myDB = new DatabaseHelper(this);
        rv = findViewById(R.id.rvSubjectsDB);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);

        data = new ArrayList<>();

        getDataSQLite();
        Collections.sort(data);
        adapter = new AdapterSubject(data, this, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(Subject.this, data.get(position).getSubject(), Toast.LENGTH_SHORT).show();
            }
        });
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

    void getDataSQLite(){
        Cursor res = myDB.getAll();

        if(res.getCount() == 0){
            Toast.makeText(this, "Error nothing found", Toast.LENGTH_SHORT).show();
            return;
        }

        PropertySubject aux = new PropertySubject();

        while (res.moveToNext()){
            aux.setId(res.getInt(0));
            aux.setSubject(res.getString(1));
            aux.setTeacher(res.getString(2));
            aux.setGrade(res.getString(3));
            aux.setCredits(res.getLong(4) + "");
            aux.setPriority(res.getDouble(5) + "");
            aux.setFinalGrade(res.getDouble(6) + "");
            data.add(aux);
            aux = new PropertySubject();
        }

        res.close();
    }
}
