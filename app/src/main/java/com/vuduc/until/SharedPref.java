package com.vuduc.until;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by VuDuc on 9/7/2017.
 */

public class SharedPref {
    private static final String AREA_ID = "AREA_ID";
    private static final String SHARED_PREF_KEY = "SHARED_PREF_KEY";
    SharedPreferences mSharedPreferences;
    private Context mContext;

    public SharedPref(Context context) {
        this.mContext = context;
        init();
    }

    private void init() {
        mSharedPreferences = mContext.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
    }

    public String getAreaId() {
        return mSharedPreferences.getString(AREA_ID, null);
    }

    public void setAreaId(String id) {
        mSharedPreferences.edit().putString(AREA_ID, id).commit();
    }
}
