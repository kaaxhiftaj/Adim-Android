package com.adim.techease.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import com.adim.techease.utils.Configuration;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


/**
 * Created by Asus on 10/4/2017.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG = "zma firebase";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static String DEVICE_TOKEN;

    @Override
    public void onTokenRefresh() {
        sharedPreferences = getSharedPreferences(Configuration.MY_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        DEVICE_TOKEN = refreshedToken;
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        if (refreshedToken.equals(null)){
           // onTokenRefresh();
            Toast.makeText(this, "F, ID Null", Toast.LENGTH_SHORT).show();
            Log.d("zma c Refreshed token: ", refreshedToken);
            refreshedToken = FirebaseInstanceId.getInstance().getToken();

        }else {
            editor.putString("device_token", refreshedToken).commit();
        }

        sendRegistrationToServer(refreshedToken);
    }

    private String sendRegistrationToServer(String token) {
        return token;
        // TODO: Implement this method to send token to your app server.
    }


}
