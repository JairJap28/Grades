package com.example.jairjap.worksdidacticoscsj.GradesDB.Schedule;

import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.jairjap.worksdidacticoscsj.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class AdapterSchedule extends RecyclerView.Adapter<AdapterSchedule.ViewHolder> {

    private ArrayList<PropertySchedule> days;
    private WeakReference<Context> wContext;

    public AdapterSchedule(WeakReference<Context> wContext, ArrayList<PropertySchedule> days){
        this.days = days;
        this.wContext = wContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_grade_schedule, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.day.setText(days.get(position).getSubject());
        holder.showTime.setText(days.get(position).getHour());
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public void remove(int index){
        days.remove(index);
        Collections.sort(days);
        notifyItemRemoved(index);
    }

    public void removeAll(){
        days.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        Button time;
        EditText showTime;
        TextView day;

        public ViewHolder(View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.btnTimeDay);
            showTime = itemView.findViewById(R.id.editTextTimeDay);
            day = itemView.findViewById(R.id.textViewDay);

            time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogTimePicker();
                }
            });
        }

        void dialogTimePicker(){
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(wContext.get(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    String hourDays = selectedHour + ":" + ((selectedMinute < 10) ? "0"+selectedMinute :  selectedMinute);
                    showTime.setText(hourDays);
                    days.get(getAdapterPosition()).setHour(hourDays);
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle(wContext.get().getResources().getString(R.string.select_time));
            mTimePicker.show();
        }
    }


}
