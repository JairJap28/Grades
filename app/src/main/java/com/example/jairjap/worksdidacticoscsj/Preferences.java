package com.example.jairjap.worksdidacticoscsj;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class Preferences {
    public static final String STRING_PREFERENCES = "WorksDidacticosCSJ.Settings";
    public static final String NUM_CREDITS = "CREDITS";
    public static final String NUM_PERIODS = "PERIODS";
    public static final String MAX_GRADE = "MAX_GRADE";
    public static final String VALUE_PERIODS = "VAL_PERIODS";


    public static void saveBoolean(Context c, Boolean b, String key){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, c.MODE_PRIVATE);
        preferences.edit().putBoolean(key, b).apply();
    }

    public static void saveString(Context c, String b, String key){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, c.MODE_PRIVATE);
        preferences.edit().putString(key, b).apply();
    }

    public static void saveSetString(Context c, Set<String> values, String key){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, c.MODE_PRIVATE);
        preferences.edit().putStringSet(key, values);
    }

    public static Boolean getBoolean(Context c, String key){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, c.MODE_PRIVATE);
        //If we have never stored something, it returns false
        return preferences.getBoolean(key, false);
    }

    public static String getString(Context c, String key){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, c.MODE_PRIVATE);
        //If we have never stored something, it returns ""
        return preferences.getString(key, "");
    }

    public static Set<String> getSetString(Context c, String key){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, c.MODE_PRIVATE);
        //If we have never saved something, return null
        //Be carefull with the validation
        return preferences.getStringSet(key, null);
    }

}
