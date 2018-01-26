package com.adim.techease.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.adim.techease.R;

/**
 * Created by Adam Noor on 16-Jan-18.
 */

public class Alert_Utils {

    public static ProgressDialog progressDialog;

    /**
     * add new patient
     *
     * @param activity
     */
    public static void showErrorDialog(Activity activity, String message) {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_dialog
                , null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
//        TextView tvError = dialogView.findViewById(R.id.tv_error);
//        tvError.setText(message);
//        Button btnOk = dialogView.findViewById(R.id.btn_ok);
//        btnOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                alertDialog.dismiss();
//            }
//        });
        alertDialog.show();
    }


    public static AlertDialog createProgressDialog(Activity activity) {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_progressbar
                , null);

        dialogBuilder.setView(dialogView);
        ProgressBar pd = dialogView.findViewById(R.id.custom_progress_bar);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_box);
        alertDialog.getWindow().setAttributes(lp);
        pd.setVisibility(View.VISIBLE);
        return alertDialog;


    }
}
