package com.example.jairjap.worksdidacticoscsj.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context){
        super(context, Utilities.getDatabaseName(), null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilities.getCreateTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Utilities.getDropTable());
        onCreate(db);
    }

    public boolean insert(ContentValues values){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(Utilities.getNameTable(), null, values);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean updatePriority(long id, Double val){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("priority", val);
        db.update(Utilities.getNameTable(), contentValues, "id=?", new String[]{ id + "" });
        return true;
    }

    public Cursor getAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(Utilities.getSelectAll(), null);
        return res;
    }
}
