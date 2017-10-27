package com.adim.techease.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.adim.techease.activities.AuthOptionScreen;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationFragment extends Fragment {

    Button btnNextSignUp;
    TextView tv_login ;
    Fragment fragment;
    EditText etUserName, etEmail, etPassword, etConfirmPassword;
    String  strUserName, strDob,  strEmail, strPassword, strConfirmPassword;
    Bundle bundle;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView ivBackToLogin;

    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        sharedPreferences = getActivity().getSharedPreferences(Configuration.MY_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        ivBackToLogin = (ImageView) view.findViewById(R.id.iv_back_login);

        ivBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AuthOptionScreen.class));
            }
        });

        etUserName = (EditText) view.findViewById(R.id.et_username);
        etEmail = (EditText) view.findViewById(R.id.et_email_signup);
        etPassword = (EditText) view.findViewById(R.id.et_password_signup);
        etConfirmPassword = (EditText) view.findViewById(R.id.et_confirm_password);
        tv_login = (TextView)view.findViewById(R.id.tv_login_here);
        btnNextSignUp = (Button) view.findViewById(R.id.btn_next_signup);
        btnNextSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDataInput();

            }
        });


        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new LoginFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
        return view;
    }


    public void onDataInput() {
        strUserName = etUserName.getText().toString();
        strEmail = etEmail.getText().toString();
        strPassword = etPassword.getText().toString();
        strConfirmPassword = etConfirmPassword.getText().toString();

        if (strUserName.equals("") || strEmail.length() < 3) {
            etUserName.setError("Enter a valid First name");
        } else if ((!android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches())) {
            etEmail.setError("Please enter valid email id");
        } else if (!strPassword.equals(strConfirmPassword)) {
            etConfirmPassword.setError("Password doesn't match");
        } else {
            Log.d("zma data", strUserName+"\n"+strEmail+"\n"+strDob+"\n"+strConfirmPassword);
            DialogUtils.showProgressSweetDialog(getActivity(), "Getting registered");
            apiCall();


        }

    }
//

    public void apiCall() {
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Configuration.USER_URL+"Signup/register", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("zma  reg response", response);
                DialogUtils.sweetAlertDialog.dismiss();
                if (response.contains("true")) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("user");
                        for (int i = 0; i<jsonArray.length(); i++){
                            JSONObject temp = jsonArray.getJSONObject(i);
                            String strApiToken = temp.getString("token_id");
                            editor.putString("api_token", strApiToken);
                            editor.putString("name",strUserName);
                            editor.putString("email",strEmail);
                            editor.commit();


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity(), "Registration Successful", Toast.LENGTH_SHORT).show();
                    fragment = new LoginFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();


                } else {
                   DialogUtils.showWarningAlertDialog(getActivity(), "Something went wrong");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("zma error", String.valueOf(error));
                DialogUtils.sweetAlertDialog.dismiss();
                DialogUtils.showWarningAlertDialog(getActivity(), String.valueOf(error.getCause()));

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fullname", strUserName);
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
