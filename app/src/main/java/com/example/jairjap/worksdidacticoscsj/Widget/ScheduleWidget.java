package com.example.jairjap.worksdidacticoscsj.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.ListView;
import android.widget.RemoteViews;

import com.example.jairjap.worksdidacticoscsj.R;

/**
 * Implementation of App Widget functionality.
 */
public class ScheduleWidget extends AppWidgetProvider {

    private ListView subjects_schedule;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.schedule_widget);

        //Intent intent = new Intent(context, ScheduleRemoteViewService.class);
        //views.setRemoteAdapter(R.id.list_SubjectsWidget, intent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    /*@Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            // refresh all your widgets
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, ScheduleWidget.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.list_SubjectsWidget);
        }

        super.onReceive(context, intent);
    }

    public static void sendRefreshBroadcast(Context context){
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context, ScheduleWidget.class));
        context.sendBroadcast(intent);
    }*/
}

