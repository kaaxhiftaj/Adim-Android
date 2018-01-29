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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adim.techease.R;
import com.adim.techease.activities.AuthOptionScreen;
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

public class RegistrationFragment extends Fragment {

    Button btnNextSignUp,btnSignIn,btnSignUp;
    TextView tv_login ;
    Fragment fragment;
    LinearLayout linearLayout;
    EditText etUserName, etEmail, etPassword, etConfirmPassword;
    String  strUserName, strDob,  strEmail, strPassword, strConfirmPassword;
    Bundle bundle;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView ivBackToLogin;
    Typeface typefaceReg,typefaceBold;
    String device_token = "" ;
    ImageView ivBackArrow;

    android.support.v7.app.AlertDialog alertDialog;

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
        device_token = sharedPreferences.getString("device_token","");

        ivBackToLogin = (ImageView) view.findViewById(R.id.ivBackArrow);
        ivBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AuthOptionScreen.class));
            }
        });

        typefaceReg = Typeface.createFromAsset(getActivity().getAssets(), "raleway_reg.ttf");
        typefaceBold = Typeface.createFromAsset(getActivity().getAssets(), "raleway_bold.ttf");
        etUserName = (EditText) view.findViewById(R.id.et_username);
        etEmail = (EditText) view.findViewById(R.id.et_email_signup);
        etPassword = (EditText) view.findViewById(R.id.et_password_signup);
        etConfirmPassword = (EditText) view.findViewById(R.id.et_confirm_password);
        tv_login = (TextView)view.findViewById(R.id.tv_login_here);
        btnNextSignUp = (Button) view.findViewById(R.id.btn_next_signup);

        linearLayout=(LinearLayout)view.findViewById(R.id.parentLayout);
        ivBackArrow=(ImageView)view.findViewById(R.id.ivBackArrow);

        etUserName.setTypeface(typefaceReg);
        etEmail.setTypeface(typefaceReg);
        etPassword.setTypeface(typefaceReg);
        etConfirmPassword.setTypeface(typefaceReg);
        tv_login.setTypeface(typefaceReg);
        btnNextSignUp.setTypeface(typefaceBold);

        ToggleSwitch toggleSwitch=(ToggleSwitch)view.findViewById(R.id.toglebtn);

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

        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),AuthOptionScreen.class));
                getActivity().finish();
            }
        });

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
            Log.d("zma data", strUserName+"\n"+strEmail+"\n"+strPassword+"\n"+strConfirmPassword);
          //  DialogUtils.showProgressSweetDialog(getActivity(), "Getting registered");
            if (alertDialog==null)
            {
                alertDialog= Alert_Utils.createProgressDialog(getActivity());
                alertDialog.show();
            }
            apiCall();


        }

    }


    public void apiCall() {
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, Configuration.USER_URL+"Signup/register", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("zma  reg response", response);
                if (alertDialog!=null)
                    alertDialog.dismiss();
                if (response.contains("true")) {

                    try {
                        JSONObject jsonObject = new JSONObject(response).getJSONObject("user");
                            String strApiToken = jsonObject.getString("token_id");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity(), "Registration Successful", Toast.LENGTH_SHORT).show();
                    fragment = new LoginFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();


                } else {

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
                params.put("fullname", strUserName);
                params.put("email", strEmail);
                params.put("password", strPassword);
                params.put("device", "android");
                params.put("device_id" , device_token);
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
