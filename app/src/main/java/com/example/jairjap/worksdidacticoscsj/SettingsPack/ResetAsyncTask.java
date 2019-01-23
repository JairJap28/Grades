package com.example.jairjap.worksdidacticoscsj.SettingsPack;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class ResetAsyncTask extends AsyncTask<Void, Integer, Boolean> {

    private int ITERATIONS = 5;
    private String title;
    private String message;

    private ProgressDialog mProgress;
    private Context c;

    public ResetAsyncTask(Context c, String title, String message){
        this.c = c;
        this.title = title;
        this.message = message;
    }

    @Override
    protected void onPreExecute() {
        mProgress = new ProgressDialog(c);
        mProgress.setTitle(title);
        mProgress.setMessage(message);
        mProgress.show();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        mProgress.dismiss();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        for(int i = 0; i < ITERATIONS; i++){
            myLongRunningOperation();
        }
        return null;
    }


    private void myLongRunningOperation() {
        try {
            Thread.sleep(500);
        }catch (InterruptedException e){}
    }
}
