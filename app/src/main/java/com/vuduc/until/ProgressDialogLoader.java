package com.vuduc.until;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.vuduc.tluiot.DeviceNodeUpdateActivity;

/**
 * Created by Jaison
 */
public class ProgressDialogLoader {
    private static ProgressDialog progressDialog;
    Context context;
    Activity activity;

    public ProgressDialogLoader(Context context) {
        this.context = context;
        this.activity = (Activity) context;
    }

    public static void progressdialog_creation(Context context, String title) {
        try {
            progressDialog = ProgressDialog.show(context, "", title);
        } catch (Exception e) {

        }
    }

    public static void progressdialog_dismiss() {
        if ((progressDialog != null) && progressDialog.isShowing())
            //Logger.d(DeviceNodeUpdateActivity.TAG, "progressdialog_dismiss");
            progressDialog.dismiss();
        progressDialog = null;
    }
}
