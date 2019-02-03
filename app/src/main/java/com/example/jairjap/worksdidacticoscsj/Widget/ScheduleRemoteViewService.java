package com.example.jairjap.worksdidacticoscsj.Widget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class ScheduleRemoteViewService extends RemoteViewsService {

    private static final String TAG = "DidacticosCSJ, WService";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, ": ObGetViewFactory: Service Called");
        return new ScheduleRemoteViewFactory(this.getApplicationContext(), intent);
    }
}
