package com.sayan.sample.paytmpgsdksample;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class ViewUtil {
    public static void showAlertDialog(Context context, final String message) {
        new AlertDialog.Builder(context).setMessage(message)
                .setTitle("Paytm")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

}
