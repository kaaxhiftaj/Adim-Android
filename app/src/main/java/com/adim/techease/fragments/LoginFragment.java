package com.adim.techease.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.adim.techease.R;
import com.adim.techease.activities.AuthOptionScreen;
import com.adim.techease.activities.MainActivity;
import com.adim.techease.utils.Alert_Utils;
import com.adim.techease.utils.Configuration;
import com.adim.techease.utils.DialogUtils;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import belka.us.androidtoggleswitch.widgets.BaseToggleSwitch;
import belka.us.androidtoggleswitch.widgets.ToggleSwitch;


public class LoginFragment extends Fragment {

    Fragment fragment;
    Button btnLogin;
    EditText etEmail, etPassword;
    String strEmail, strPassword;
    TextView tvForgetPassword, tvRegisterHere;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView ivBackToLogin;
    Typeface typefaceReg,typefaceBold;
    ImageView ivBackArrow;
    android.support.v7.app.AlertDialog alertDialog;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        sharedPreferences = getActivity().getSharedPreferences(Configuration.MY_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        etEmail = (EditText) view.findViewById(R.id.et_email_login);
        etPassword = (EditText) view.findViewById(R.id.et_password_login);
        tvForgetPassword = (TextView) view.findViewById(R.id.tv_forget_password);
        btnLogin = (Button) view.findViewById(R.id.btn_login);
        typefaceReg = Typeface.createFromAsset(getActivity().getAssets(), "raleway_reg.ttf");
        typefaceBold = Typeface.createFromAsset(getActivity().getAssets(), "raleway_bold.ttf");
        ivBackArrow=(ImageView)view.findViewById(R.id.ivBackArrowSignIn);


        etEmail.setTypeface(typefaceReg);
        etPassword.setTypeface(typefaceReg);
        tvForgetPassword.setTypeface(typefaceReg);
        btnLogin.setTypeface(typefaceBold);

        tvRegisterHere = (TextView) view.findViewById(R.id.tv_register_here);
        tvRegisterHere.setTypeface(typefaceReg);
        tvRegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new RegistrationFragment();
                getFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, fragment).addToBackStack("tag").commit();
            }
        });
        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new EmailVerificationFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });

        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),AuthOptionScreen.class));
                getActivity().finish();
            }
        });

        ToggleSwitch toggleSwitch=(ToggleSwitch)view.findViewById(R.id.toglebtnLoginFrag);

        toggleSwitch.setOnToggleSwitchChangeListener(new BaseToggleSwitch.OnToggleSwitchChangeListener() {
            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {

                if (position==0)
                {
                    Fragment fragment=new RegistrationFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack("abc").commit();
                }
                else if (position==1)
                {
                    Fragment fragment=new LoginFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack("abc").commit();
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDataInput();
            }
        });
        return view;
    }

    public void onDataInput() {
        strEmail = etEmail.getText().toString();
        strPassword = etPassword.getText().toString();
        if ((!android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches())) {
            etEmail.setError("Please enter valid email id");
        } else if (strPassword.equals("")) {
            etPassword.setError("Please enter your password");
        } else {

            if (alertDialog==null)
            {
                alertDialog= Alert_Utils.createProgressDialog(getActivity());
                alertDialog.show();
            }
            apiCall();
        }
    }

    public void apiCall() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configuration.USER_URL + "Signup/login"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("true")) {
                    try {
                        Log.d("zma log inner ", response);
                        JSONObject jsonObject = new JSONObject(response).getJSONObject("user");
                        String strApiToken = jsonObject.getString("token_id");
                        String Logged_In_User_Id=jsonObject.getString("user_id");
                        String fullname = jsonObject.getString("fullname");
                        String email = jsonObject.getString("email");
                        editor.putString("api_token", strApiToken);
                        editor.putString("user_Id",Logged_In_User_Id);
                        editor.putString("fullname" , fullname);
                        editor.putString("email" , email);
                        editor.commit();
                        if (alertDialog!=null)
                            alertDialog.dismiss();
                        Log.d("zma user id", Logged_In_User_Id);
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();


                    } catch (JSONException e) {
                        if (alertDialog!=null)
                            alertDialog.dismiss();
                        e.printStackTrace();
                        Log.d("error", String.valueOf(e.getMessage()));
                    }
                  //  pDialog.dismiss();
                    editor.putString("api_token", "abc").commit();


                } else {
                    if (alertDialog!=null)
                        alertDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.getString("message");
                        DialogUtils.showErrorDialog(getActivity(), message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (alertDialog!=null)
                    alertDialog.dismiss();
                DialogUtils.sweetAlertDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    DialogUtils.showWarningAlertDialog(getActivity(), "Network Error");
                } else if (error instanceof AuthFailureError) {
                    DialogUtils.showWarningAlertDialog(getActivity(), "Email or Password Error");
                } else if (error instanceof ServerError) {
                    DialogUtils.showWarningAlertDialog(getActivity(), "Server Error");
                } else if (error instanceof NetworkError) {
                    DialogUtils.showWarningAlertDialog(getActivity(), "Network Error");
                } else if (error instanceof ParseError) {
                    DialogUtils.showWarningAlertDialog(getActivity(), "Parsing Error");
                }


            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", strEmail);
                params.put("password", strPassword);
                return params;
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(200000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
    }

}
