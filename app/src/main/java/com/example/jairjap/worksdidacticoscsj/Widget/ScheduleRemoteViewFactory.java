package com.example.jairjap.worksdidacticoscsj.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jairjap.worksdidacticoscsj.R;
import com.example.jairjap.worksdidacticoscsj.Room.ScheduleRoom.ScheduleModel;
import com.example.jairjap.worksdidacticoscsj.Room.ScheduleRoom.ScheduleService;
import com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom.SubjectModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ScheduleRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<WidgetItem> schedule_subjects;
    private int mAppWidgetId;

    ScheduleService scheduleService;

    ScheduleRemoteViewFactory(Context context, Intent intent) {
        this.context = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        schedule_subjects = new ArrayList<>();
        scheduleService = new ScheduleService(new WeakReference<>(context));
    }

    @Override
    public void onCreate() {
        //getSchedule();
    }

    @Override
    public void onDataSetChanged() {
        //refresh the list
        getSchedule();
    }

    @Override
    public void onDestroy() {
        schedule_subjects.clear();
    }

    @Override
    public int getCount() {
        return (schedule_subjects != null) ? schedule_subjects.size(): 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if(position == AdapterView.INVALID_POSITION || schedule_subjects.get(position) == null){
            return null;
        }

        RemoteViews rv = new RemoteViews(context.getPackageName(),
                R.layout.item_view_subject_widget);
        rv.setTextViewText(R.id.textView_subject_widget, schedule_subjects.get(position).getSubject());
        rv.setTextViewText(R.id.textView_classroom_widget, schedule_subjects.get(position).getClass_room());
        rv.setTextViewText(R.id.textView_hour_widget, schedule_subjects.get(position).getHour());
        rv.setTextViewText(R.id.textView_teacher_widget, schedule_subjects.get(position).getTeacher());

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void getSchedule(){
        schedule_subjects = scheduleService.getDaySchedule();
    }


}
