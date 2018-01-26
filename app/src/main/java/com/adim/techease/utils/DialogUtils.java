package com.adim.techease.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Zahid on 9/9/17.
 */

public class DialogUtils {

    public static ProgressDialog progressDialog = null;
    public static SweetAlertDialog sweetAlertDialog = null;

    public static void showErrorDialog(Context context, String msg) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }



    public static SweetAlertDialog showProgressSweetDialog(Context context, String message) {
        sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#179e99"));
        sweetAlertDialog.setTitleText(message);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.show();
        return sweetAlertDialog;
    }

    public static SweetAlertDialog showErrorTypeAlertDialog(Context contxt, String message) {
        new SweetAlertDialog(contxt, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(message)
                .show();
        return sweetAlertDialog;
    }

    public static SweetAlertDialog showWarningAlertDialog(Context context, String message) {
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(message)
                .show();
        return sweetAlertDialog;
    }

    public static SweetAlertDialog showSuccessAlertDialog(Context context, String message) {
        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(message)
                .show();
        return sweetAlertDialog;
    }

}
