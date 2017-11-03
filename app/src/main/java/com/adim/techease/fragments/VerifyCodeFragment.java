package com.adim.techease.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adim.techease.R;
import com.adim.techease.utils.Configuration;
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

public class VerifyCodeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText etCode;
    Button btnSendCode;
    String strVerifyCode, strEmail;
    Fragment fragment;
    ImageView ivBackToEmailVerification;
    TextView tvSkipLogin;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public VerifyCodeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static VerifyCodeFragment newInstance(String param1, String param2) {
        VerifyCodeFragment fragment = new VerifyCodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.verify_code_fragment, container, false);
        sharedPreferences = getActivity().getSharedPreferences(Configuration.MY_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Bundle args = getArguments();
        strEmail = args.getString("email");
        etCode = (EditText) view.findViewById(R.id.et_code_forget);
        btnSendCode = (Button) view.findViewById(R.id.btn_verify_code);
        btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDataInput();
            }
        });
        ivBackToEmailVerification = (ImageView)view.findViewById(R.id.iv_back_login);
        tvSkipLogin = (TextView)view.findViewById(R.id.tv_skip_login);
        tvSkipLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new LoginFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

            }
        });

//        ivBackToEmailVerification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fragment = new EmailVerificationFragment();
//                getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
//            }
//        });
        return view;
    }

    public void onDataInput() {
        strVerifyCode = etCode.getText().toString();
        if (strVerifyCode.equals("") || strVerifyCode.length() < 6) {
            etCode.setError("Please enter a valid code");
        } else {
            apiCall();
        }
    }

    public void apiCall() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#7DB3D2"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://adadigbomma.com/Signup/CheckCode/",
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("true")){

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response).getJSONObject("message");
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    pDialog.dismiss();
                    fragment = new ChangePasswordFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                etCode.setText("");
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Response Error")
                        .show();


            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("code", strVerifyCode);
                params.put("Accept", "application/json");
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
