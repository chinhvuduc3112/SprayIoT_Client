package com.vuduc.until;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by VuDuc on 9/7/2017.
 */

public class SharedPref {
    private static final String SHARED_PREF_KEY = "SHARED_PREF_KEY";
    private static SharedPref instance;
    private SharedPreferences preferences;

    public static void clearAllData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
    }

    private SharedPref(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPref getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPref(context);
        }

        return instance;
    }

    public SharedPref putInt(String key, int value) {
        preferences.edit().putInt(key, value).apply();
        return instance;
    }

    public int getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    public SharedPref putString(String key, String value) {
        preferences.edit().putString(key, value).apply();
        return instance;
    }

    public SharedPref putBoolean(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
        return instance;
    }

    public boolean getBoolean(String key){
        return preferences.getBoolean(key,false);
    }


    public String getString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    public void clear() {
        preferences.edit().clear().apply();
    }
}
