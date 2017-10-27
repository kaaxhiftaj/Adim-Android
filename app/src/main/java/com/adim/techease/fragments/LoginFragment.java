package com.adim.techease.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adim.techease.R;
import com.adim.techease.activities.MainActivity;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class LoginFragment extends Fragment {

    Fragment fragment;
    Button btnLogin;
    EditText etEmail, etPassword;
    String strEmail, strPassword;
    TextView tvForgetPassword, tvRegisterHere;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView ivBackToLogin;


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

        tvRegisterHere = (TextView) view.findViewById(R.id.tv_register_here);
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
            DialogUtils.showProgressSweetDialog(getActivity(), "Getting Login");
            apiCall();
        }
    }

    public void apiCall() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#7DB3D2"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configuration.USER_URL + "Signup/login"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("zma log ", response);
                DialogUtils.sweetAlertDialog.dismiss();

                if (response.contains("true")) {
                    try {
                        Log.d("zma log inner ", response);
                        JSONObject jsonObject = new JSONObject(response).getJSONObject("user");
                        String strApiToken = jsonObject.getString("token_id");
                        String Logged_In_User_Id=jsonObject.getString("user_id");
                        Toast.makeText(getActivity(), Logged_In_User_Id, Toast.LENGTH_SHORT).show();
                        editor.putString("api_token", strApiToken);
                        editor.putString("user_Id",Logged_In_User_Id).commit();
                        Log.d("zma user id", Logged_In_User_Id);
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("error", String.valueOf(e.getMessage()));
                    }
                    pDialog.dismiss();
                    editor.putString("api_token", "abc").commit();


                } else {
                    DialogUtils.sweetAlertDialog.dismiss();
                    DialogUtils.showWarningAlertDialog(getActivity(), "Something went wrong");
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.sweetAlertDialog.dismiss();
                DialogUtils.showErrorTypeAlertDialog(getActivity(), "Server error");

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
                //   params.put("Accept", "application/json");
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
