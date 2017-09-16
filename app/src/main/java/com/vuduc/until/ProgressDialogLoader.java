package com.vuduc.until;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

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

    public static void progressdialog_creation(Activity activity, String title) {
        try {
            if (progressDialog == null)
                progressDialog = ProgressDialog.show(activity, "", title, true);
        } catch (Exception e) {

        }
    }

    public static void progressdialog_dismiss() {
        if ((progressDialog != null) && progressDialog.isShowing())
            progressDialog.dismiss();

        progressDialog = null;
    }
}
