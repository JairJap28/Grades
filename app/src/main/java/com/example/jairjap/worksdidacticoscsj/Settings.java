package com.example.jairjap.worksdidacticoscsj;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.jairjap.worksdidacticoscsj.SettingsP.ResetAsyncTask;

import java.util.Calendar;

public class Settings extends AppCompatActivity {

    private EditText getAmount;
    private EditText showTime;
    private TextView textConnected;
    private Button showFeatures;
    private Button settingConnect;
    private Button reset;
    private LinearLayout[] periods = new LinearLayout[6];
    private EditText[] grades = new  EditText[6];

    private boolean state;
    private boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        connected = true;

        periods[0] = findViewById(R.id.LinearLayoutSettingP1);
        periods[1] = findViewById(R.id.LinearLayoutSettingP2);
        periods[2] = findViewById(R.id.LinearLayoutSettingP3);
        periods[3] = findViewById(R.id.LinearLayoutSettingP4);
        periods[4] = findViewById(R.id.LinearLayoutSettingP5);
        periods[5] = findViewById(R.id.LinearLayoutSettingP6);

        grades[0] = findViewById(R.id.editTextSettingPP1);
        grades[1] = findViewById(R.id.editTextSettingPP2);
        grades[2] = findViewById(R.id.editTextSettingPP3);
        grades[3] = findViewById(R.id.editTextSettingPP4);
        grades[4] = findViewById(R.id.editTextSettingPP5);
        grades[5] = findViewById(R.id.editTextSettingPP6);

        getAmount = findViewById(R.id.editTextSettingPeriods);
        showFeatures = findViewById(R.id.btnSettingShowFeatures);
        reset = findViewById(R.id.btnSettingsReset);

        //First Initialization
        String auxNumPeriods = Preferences.getString(Settings.this, Preferences.NUM_PERIODS);
        if(auxNumPeriods.equals("") || auxNumPeriods.equals("0")){
            auxNumPeriods = "3";
        }
        setVisiblePeriods(Integer.parseInt(auxNumPeriods));
        setEnableGrades(Integer.parseInt(auxNumPeriods));
        getAmount.setText(auxNumPeriods);

        ArrayAdapter<CharSequence> opcFrecuency = ArrayAdapter.createFromResource(this, R.array.frecuency,
                R.layout.spinner_item);

        opcFrecuency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        getAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if(actionId == EditorInfo.IME_ACTION_SEND){
                    try {
                        if(!getAmount.toString().trim().equals(Preferences.getString(Settings.this, Preferences.NUM_PERIODS))){
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getAmount.getWindowToken(), 0);
                            setVisiblePeriods(Integer.parseInt(getAmount.getText() + ""));
                            setEnableGrades(Integer.parseInt(getAmount.getText() + ""));
                            Preferences.saveString(Settings.this, getAmount.getText().toString(), Preferences.NUM_PERIODS);
                            handled = true;
                        }
                        else{
                            Toast.makeText(Settings.this, Settings.this.getResources().getString(R.string.insert_new_number), Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){
                        Toast.makeText(Settings.this, Settings.this.getResources().getString(R.string.insert_correct_number), Toast.LENGTH_SHORT).show();
                        Toast.makeText(Settings.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        getAmount.setText("6");
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
    }

    void setVisiblePeriods(int amount){
        for(int i = 0; i < amount; i++){
            periods[i].setVisibility(View.VISIBLE);
        }

        for(int i = amount; i < 6; i++){
            periods[i].setVisibility(View.GONE);
        }
    }

    void setEnableGrades(int amount){
        for(int i = 0; i < amount; i++){
            grades[i].setEnabled(true);
        }

        for(int i = amount; i < 6; i++){
            grades[i].setEnabled(false);
        }
    }

    void setConnectedOptions(){
        this.connected = false;
        textConnected.setText(getResources().getString(R.string.connected));
        settingConnect.setText(getResources().getString(R.string.logout));
        settingConnect.setCompoundDrawablesWithIntrinsicBounds( R.drawable.exit, 0, 0, 0);
    }

    void setCloseConnection(){
        this.connected = true;
        textConnected.setText(getResources().getString(R.string.connect_google_account));
        settingConnect.setText(getResources().getString(R.string.connect));
        settingConnect.setCompoundDrawablesWithIntrinsicBounds( R.drawable.search_google, 0, 0, 0);
    }

    void dialogTimePicker(){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(Settings.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String hourDays = selectedHour + ":" + ((selectedMinute < 10) ? "0"+selectedMinute :  selectedMinute);
                showTime.setText(hourDays);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle(Settings.this.getResources().getString(R.string.select_time));
        mTimePicker.show();
    }

    void dialogFeatures(){
        final Dialog dialog = new Dialog(Settings.this);
        dialog.setContentView(R.layout.dialog_features);
        dialog.show();
    }
}
