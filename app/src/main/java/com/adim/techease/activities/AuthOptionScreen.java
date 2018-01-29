package com.adim.techease.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.adim.techease.R;
import com.adim.techease.utils.Alert_Utils;
import com.adim.techease.utils.Configuration;
import com.adim.techease.utils.DialogUtils;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class AuthOptionScreen extends AppCompatActivity {

    Button guest;
    Typeface typefaceReg,typefaceBold;
    Button email_button;
    LoginButton fb_button;
    CallbackManager callbackManager;
    String fullname , email ,  provider, provider_id;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor ;
    String strApiToken;
    Button Facebookbtn;
    android.support.v7.app.AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_auth_option_screen);
        sharedPreferences = this.getSharedPreferences(Configuration.MY_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Facebookbtn=(Button)findViewById(R.id.fbBtn);
        typefaceReg = Typeface.createFromAsset(this.getAssets(), "raleway_reg.ttf");
        typefaceBold = Typeface.createFromAsset(this.getAssets(), "raleway_bold.ttf");

        //get sharedprefs for checking
        String prefs= sharedPreferences.getString("api_token","");
        Log.d("zma Pref",prefs);
        if (! prefs.equals(""))
        {
            startActivity(new Intent(AuthOptionScreen.this,MainActivity.class));
            finish();
        }

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();


        guest = (Button) findViewById(R.id.guest);
        guest.setTypeface(typefaceBold);
        fb_button = (LoginButton) findViewById(R.id.login_button);
        email_button = (Button) findViewById(R.id.email_button);

        Facebookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fb_button.performClick();
                fb_button.setReadPermissions("email");

                // Other app specific specialization

                // Callback registration
                fb_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        // App code

                        String accessToken = loginResult.getAccessToken().getToken();
                        provider_id = accessToken ;

                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.i("LoginActivity", response.toString());
                                // Get facebook data from login
                                Bundle bFacebookData = getFacebookData(object);
                                apiCall();


                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                        request.setParameters(parameters);

                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        System.out.println("onCancel");
                    }

                    @Override
                    public void onError(FacebookException e) {

                    }



                });


            }
        });

        email_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AuthOptionScreen.this, FullscreenActivity.class));
                finish();
            }
        });



        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (alertDialog==null)
                {
                    alertDialog= Alert_Utils.createProgressDialog(AuthOptionScreen.this);
                    alertDialog.show();
                }
                startActivity(new Intent(AuthOptionScreen.this, MainActivity.class));
                if (alertDialog!=null)
                    alertDialog.dismiss();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         callbackManager.onActivityResult(requestCode, resultCode, data);

    }




    public void apiCall() {
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Configuration.USER_URL + "Signup/sociallogin", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("zma  reg response", response);
                if (response.contains("true")) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject jsonArray = jsonObject.getJSONObject("user");
                             strApiToken = jsonArray.getString("token_id");
                           String user_id = jsonArray.getString("token_id");
                            editor.putString("api_token", strApiToken).commit();
                        editor.putString("user_Id", user_id).commit();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(AuthOptionScreen.this, MainActivity.class));

                } else {
                    DialogUtils.showWarningAlertDialog(AuthOptionScreen.this, "Something went wrong");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("zma error", String.valueOf(error));
                DialogUtils.showWarningAlertDialog(AuthOptionScreen.this, String.valueOf(error.getCause()));

            }
        })
        {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fullname", fullname);
                params.put("email", email);
                params.put("provider", provider);
                params.put("provider_id", provider_id);
                return params;
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(AuthOptionScreen.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(200000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        profileTracker.stopTracking();
    }

    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
             bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));

            fullname = object.getString("first_name") + object.getString("last_name");
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
                email = object.getString("email");
                provider = "facebook";
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));

            return bundle;
        }
        catch(JSONException e) {
            Log.d("Error","Error parsing JSON");
        }
        return null;
    }
}