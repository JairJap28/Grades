package com.example.jairjap.worksdidacticoscsj.SettingsPack;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jairjap.worksdidacticoscsj.Preferences;
import com.example.jairjap.worksdidacticoscsj.R;
import com.example.jairjap.worksdidacticoscsj.Room.SettingsRoom.SettingsModel;

import java.util.List;


public class Settings extends AppCompatActivity {

    private EditText getAmountPeriods;
    private EditText edTxt_max_grade;
    private EditText editText_max_credits;
    private Switch switchSettingSchedule;
    private LinearLayout[] periods_layouts = new LinearLayout[6];
    private EditText[] percentage_periods = new  EditText[6];

    private SettingsViewModel settingsViewModel;
    private SettingsModel settingsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        periods_layouts[0] = findViewById(R.id.LinearLayoutSettingP1);
        periods_layouts[1] = findViewById(R.id.LinearLayoutSettingP2);
        periods_layouts[2] = findViewById(R.id.LinearLayoutSettingP3);
        periods_layouts[3] = findViewById(R.id.LinearLayoutSettingP4);
        periods_layouts[4] = findViewById(R.id.LinearLayoutSettingP5);
        periods_layouts[5] = findViewById(R.id.LinearLayoutSettingP6);

        percentage_periods[0] = findViewById(R.id.editTextSettingPP1);
        percentage_periods[1] = findViewById(R.id.editTextSettingPP2);
        percentage_periods[2] = findViewById(R.id.editTextSettingPP3);
        percentage_periods[3] = findViewById(R.id.editTextSettingPP4);
        percentage_periods[4] = findViewById(R.id.editTextSettingPP5);
        percentage_periods[5] = findViewById(R.id.editTextSettingPP6);

        //This is for live Data
        settingsViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);

        getAmountPeriods = findViewById(R.id.editTextSettingPeriods);
        edTxt_max_grade = findViewById(R.id.editTextSetting_max_grade);
        editText_max_credits = findViewById(R.id.editTextSetting_max_credits);
        switchSettingSchedule = findViewById(R.id.switchSettingSchedule);

        Button showFeatures = findViewById(R.id.btnSettingShowFeatures);
        Button reset = findViewById(R.id.btnSettingsReset);

        //First Initialization
        String auxNumPeriods = Preferences.getString(Settings.this, Preferences.NUM_PERIODS);
        if(auxNumPeriods.equals("") || auxNumPeriods.equals("0")) {
            auxNumPeriods = "3";
        }

        getAmountPeriods.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if(actionId == EditorInfo.IME_ACTION_SEND){
                    try {
                        if(!getAmountPeriods.toString().trim().equals(Preferences.getString(Settings.this, Preferences.NUM_PERIODS))){
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getAmountPeriods.getWindowToken(), 0);
                            int amount_periods = Integer.parseInt(getAmountPeriods.getText() + "");
                            setVisiblePeriods(amount_periods);
                            setEnablePercentagePeriods(amount_periods + 1);
                            Preferences.saveString(Settings.this, getAmountPeriods.getText().toString(), Preferences.NUM_PERIODS);
                            handled = true;
                        }
                        else{
                            Toast.makeText(Settings.this, Settings.this.getResources().getString(R.string.insert_new_number), Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){
                        Toast.makeText(Settings.this, Settings.this.getResources().getString(R.string.insert_correct_number), Toast.LENGTH_SHORT).show();
                        Toast.makeText(Settings.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        getAmountPeriods.setText("6");
                        Preferences.saveString(Settings.this,   "6", Preferences.NUM_PERIODS);
                    }
                }
                return handled;
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        new ResetAsyncTask(Settings.this, Settings.this.getResources().getString(R.string.reset_app),
                                Settings.this.getResources().getString(R.string.please_wait)).execute();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(Settings.this, Settings.this.getResources().getString(R.string.be_careful_you_could_lose_your_info), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        showFeatures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFeatures();
            }
        });


        settingsViewModel.getSettings().observe(this, new Observer<List<SettingsModel>>() {
            @Override
            public void onChanged(@Nullable List<SettingsModel> settingsModels) {
                if(settingsModels != null && settingsModels.size() > 0){
                    settingsModel = settingsModels.get(0);
                    SparseIntArray periods_percentage = settingsModels.get(0).getPeriods_percentage();

                    getAmountPeriods.setText(String.valueOf(periods_percentage.size()));
                    setVisiblePeriods(periods_percentage.size());
                    setPercentagePeriods(periods_percentage);

                    edTxt_max_grade.setText(String.valueOf(settingsModels.get(0).getMax_grade()));
                    editText_max_credits.setText(String.valueOf(settingsModels.get(0).getMax_credits()));
                    switchSettingSchedule.setChecked(settingsModels.get(0).isCreate_scedule());
                }
                else{
                    setVisiblePeriods(3);
                    setEnablePercentagePeriods(3);
                    edTxt_max_grade.setText(String.valueOf(5.0));
                    editText_max_credits.setText(String.valueOf(4));
                    switchSettingSchedule.setChecked(false);
                    settingsViewModel.InsertSettings(getSettings());
                }
            }
        });
    }

    void setVisiblePeriods(int amount){
        for(int i = 0; i < amount; i++){
            periods_layouts[i].setVisibility(View.VISIBLE);
        }

        for(int i = amount; i < 6; i++){
            periods_layouts[i].setVisibility(View.GONE);
        }
    }

    void setEnablePercentagePeriods(int amount){
        for(int i = 0; i < amount; i++){
            percentage_periods[i].setEnabled(true);
        }

        for(int i = amount; i < 6; i++){
            percentage_periods[i].setEnabled(false);
        }
    }

    void setPercentagePeriods(SparseIntArray in){
        for(int i = 0; i < in.size(); i++){
            percentage_periods[i].setText(String.valueOf(in.valueAt(i)));
        }
    }

    void dialogFeatures(){
        final Dialog dialog = new Dialog(Settings.this);
        dialog.setContentView(R.layout.dialog_features);
        dialog.show();
    }

    SettingsModel getSettings(){
        SettingsModel auxSettingModel = new SettingsModel();

        auxSettingModel.setId("setting_1");
        auxSettingModel.setMax_grade(Float.parseFloat(edTxt_max_grade.getText().toString()));
        auxSettingModel.setMax_credits(Integer.parseInt(editText_max_credits.getText().toString()));
        auxSettingModel.setCreate_scedule(switchSettingSchedule.isChecked());
        auxSettingModel.setPeriods_percentage(getPercentagePeriods(Integer.parseInt(getAmountPeriods.getText().toString())));

        return auxSettingModel;
    }

    SparseIntArray getPercentagePeriods(int amount){
        SparseIntArray out = new SparseIntArray();
        for(int i = 0; i < amount; i++){
            out.put(i, Integer.parseInt(percentage_periods[i].getText().toString()));
        }
        return out;
    }

    @Override
    protected void onDestroy() {
        settingsViewModel.setSettingsModel(getSettings());
        super.onDestroy();
    }
}
