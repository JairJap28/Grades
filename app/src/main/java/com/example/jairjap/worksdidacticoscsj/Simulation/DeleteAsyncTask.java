package com.example.jairjap.worksdidacticoscsj.Simulation;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.jairjap.worksdidacticoscsj.R;

public class DeleteAsyncTask extends AsyncTask<Void, Integer, Boolean> {
    private final int ITERATIONS = 5;

    private ProgressDialog mProgress = null;
    private Context c = null;

    public DeleteAsyncTask(Context c){
        this.c = c;
    }

    @Override
    protected void onPreExecute() {
        mProgress = new ProgressDialog(c);
        mProgress.setTitle(c.getResources().getString(R.string.deleting));
        mProgress.setMessage(c.getResources().getString(R.string.please_wait));
        mProgress.show();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        mProgress.dismiss();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        for (int i = 0; i < ITERATIONS; i++) {
            myLongRunningOperation();
        }

        return true;
    }

    private void myLongRunningOperation() {
        try {
            Thread.sleep(50);
        }catch (InterruptedException e){}
    }
}
