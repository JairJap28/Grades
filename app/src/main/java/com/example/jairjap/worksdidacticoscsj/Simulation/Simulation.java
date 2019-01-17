package com.example.jairjap.worksdidacticoscsj.Simulation;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jairjap.worksdidacticoscsj.R;

import java.util.ArrayList;

public class Simulation extends AppCompatActivity {
    private Button cancel;
    private Button addSubject;
    private Button calulate;
    private RecyclerView rv;
    private AdapterSimulation adapter;
    private Switch aSwitch;
    private TextView finalGrade;
    private ImageView result;
    private TextView messageResult;
    private int addAllowed;

    private int amoutnSubjects;
    private ArrayList<PropertyGrade> data;

    private boolean switchState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);

        Bundle info = this.getIntent().getExtras();
        if(info != null){
            amoutnSubjects = info.getInt("subjects");
        }

        addAllowed = 0;

        rv = findViewById(R.id.rvSubjectsSimultaion);
        calulate = findViewById(R.id.btnCalculate);
        cancel = findViewById(R.id.btnCancelSimulation);
        addSubject = findViewById(R.id.btnAddSubjectSimulation);
        aSwitch = findViewById(R.id.switchPercentage);
        finalGrade = findViewById(R.id.textViewFinalGrade);
        result = findViewById(R.id.imageViewResult);
        messageResult = findViewById(R.id.textViewMessageResult);

        restarResult();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);

        data = new ArrayList<>();

        if(amoutnSubjects > 0) addSubjects(amoutnSubjects);

        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addAllowed < 2){
                    adapter.addSubject(getResources().getString(R.string.period));
                    addAllowed++;
                }
                if(addAllowed >= 2){
                    addSubject.setEnabled(false);
                    Drawable[] drawables = addSubject.getCompoundDrawables();
                    Drawable wrapDrawable = DrawableCompat.wrap(drawables[0]);
                    DrawableCompat.setTint(wrapDrawable, getResources().getColor(R.color.colorRed));
                }
            }
        });

        calulate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int counter = rv.getAdapter().getItemCount();
                float sum = 0;
                
                if(checkGrades()){
                    if(aSwitch.isChecked()){
                        int percentage = 100 / counter;
                        for (int i = 0; i < counter; i++){
                            float grade = Float.parseFloat(adapter.data.get(i).getGrade());
                            sum += grade;
                        }
                        sum /= counter;
                        finalGrade.setText(sum + "");
                        if(sum >= 3) {
                            hightResult();
                        }
                        else{
                            lowResult();
                        }
                    }
                    else{
                        int percentage = 0;
                        boolean flag = true;
                        for(int i = 0; i < counter; i++){
                            float grade = Float.parseFloat(adapter.data.get(i).getGrade());
                            sum += grade * (Integer.parseInt(adapter.data.get(i).getCredits()) / 100.0);
                            if(!adapter.data.get(i).equals("")) percentage += Integer.parseInt(adapter.data.get(i).getCredits());
                            else{
                                flag = false;
                                break;
                            }
                        }

                        if(percentage == 100 && flag && checkPercentage()){
                            finalGrade.setText(sum + "");
                            if(sum >= 3) {
                                hightResult();
                            }
                            else{
                                lowResult();
                            }
                        }
                        else{
                            Toast.makeText(Simulation.this, "Verify the percentage of the period", Toast.LENGTH_SHORT).show();
                            restarResult();
                        }
                    }
                }
                else{
                    Toast.makeText(Simulation.this, "Verify your grades", Toast.LENGTH_SHORT).show();
                    restarResult();
                }
                
                
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchState = aSwitch.isChecked();
                adapter.percentageOOpc(switchState);
            }
        });
    }

    void restarResult(){
        finalGrade.setText("-.-");
        messageResult.setText("");
        finalGrade.setTextColor(getResources().getColor(R.color.colorText));
        result.setImageResource(R.drawable.any_question);
    }

    void hightResult(){
        finalGrade.setTextColor(getResources().getColor(R.color.colorGreen));
        messageResult.setTextColor(getResources().getColor(R.color.colorGreen));
        messageResult.setText(getResources().getString(R.string.congratulations));
        int opc = (int) (Math.random() * 3) + 1;
        switch (opc){
            case 1: result.setImageResource(R.drawable.very_goood); break;
            case 2: result.setImageResource(R.drawable.good_job); break;
            default: result.setImageResource(R.drawable.good3); break;
        }
    }

    void lowResult(){
        finalGrade.setTextColor(getResources().getColor(R.color.colorRed));
        messageResult.setText(getResources().getString(R.string.need_improve));
        messageResult.setTextColor(getResources().getColor(R.color.colorRed));
        int opc = (int) (Math.random() * 3) + 1;
        switch (opc){
            case 1: result.setImageResource(R.drawable.pay_attention); break;
            case 2: result.setImageResource(R.drawable.pay_attention2); break;
            default: result.setImageResource(R.drawable.pay_attention3); break;
        }
    }

    boolean checkGrades(){
        int counter = rv.getAdapter().getItemCount();
        for(int i = 0; i < counter; i++){
            try {
                float grade = Float.parseFloat(adapter.data.get(i).getGrade());
                if (grade < 0 || grade > 5) return false;
            }catch (Exception e){
                return false;
            }
        }
        return true;
    }

    boolean checkPercentage(){
        int counter = rv.getAdapter().getItemCount();
        for(int i = 0; i < counter; i++){
            try {
                int percentage = Integer.parseInt(adapter.data.get(i).getCredits());
                if(percentage == 0 || percentage > 100) return false;
            }catch (Exception e){
                return false;
            }
        }
        return true;
    }
    
    void addSubjects(int amount){
        for(int i = 0; i < amount; i++){
            String a = getResources().getString(R.string.period);
            data.add(new PropertyGrade(a, "0"));
        }
        adapter = new AdapterSimulation(this, data);
        rv.setAdapter(adapter);
    }
}
