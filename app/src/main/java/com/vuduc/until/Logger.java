package com.vuduc.until;

import android.util.Log;

/**
 * Created by VuDuc on 9/5/2017.
 */

public class Logger {
    private final static boolean DEBUG = true;

    public static void d(String tag, String msg){
        if(DEBUG)
            Log.d(tag,msg);
    }
}
