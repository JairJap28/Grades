package com.example.jairjap.worksdidacticoscsj.GradesDB;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class UpdateAsyncTask extends AsyncTask<Void, Integer, Boolean> {

    private int ITERATIONS = 5;
    private String title;
    private String message;

    private ProgressDialog progressDialog;
    private Context c;

    UpdateAsyncTask(Context c, String title, String message){
        this.c = c;
        this.title = title;
        this.message = message;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(c);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        progressDialog.dismiss();
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
