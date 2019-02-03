package com.example.jairjap.worksdidacticoscsj.Widget;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import com.example.jairjap.worksdidacticoscsj.R;
import com.example.jairjap.worksdidacticoscsj.Room.ScheduleRoom.ScheduleModel;

import java.lang.ref.WeakReference;
import java.util.List;

public class ScheduleRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<ScheduleModel> schedule_subjects;

    public ScheduleRemoteViewFactory(Context context, Intent intent) {
        this.context = context;
    }

    public void setSchedule_subjects(List<ScheduleModel> schedule_subjects) {
        this.schedule_subjects = schedule_subjects;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews rv = new RemoteViews(context.getPackageName(),
                R.layout.item_view_subject_widget);

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
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
