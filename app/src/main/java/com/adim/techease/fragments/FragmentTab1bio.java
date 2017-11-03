package com.adim.techease.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.adim.techease.R;
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


public class FragmentTab1bio extends Fragment {

    TextView Description, Age, Height, Dob,textView1,textView2,textView3;
    String getId;
    Typeface typeface;
    Button btnShare;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_tab1bio, container, false);


        typeface=Typeface.createFromAsset(getActivity().getAssets(),"myfont.ttf");
        Description = (TextView) v.findViewById(R.id.tvDescription);
        Age = (TextView) v.findViewById(R.id.tvAge);
        Height = (TextView) v.findViewById(R.id.tvHeight);
        Dob = (TextView) v.findViewById(R.id.tvDob);
        textView1=(TextView)v.findViewById(R.id.txt1);
        textView2=(TextView)v.findViewById(R.id.txt2);
        textView3=(TextView)v.findViewById(R.id.txt3);
        btnShare=(Button)v.findViewById(R.id.btnShareBio);
        getId=getArguments().getString("id");
        Description.setTypeface(typeface);
        Age.setTypeface(typeface);
        Height.setTypeface(typeface);
        Dob.setTypeface(typeface);
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);
        textView3.setTypeface(typeface);
        btnShare.setTypeface(typeface);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Adim");
                    String sAux = "\nLet me recommend you this application\n\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id=com.adim.techease \n\n";
                    intent.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(intent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });
        apicall();
        return v;
    }

    private void apicall() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#7DB3D2"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Configuration.USER_URL+"App/getDetail/"+getId
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("zma  reg response", response);

                if (response.contains("true")) {
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        JSONObject jsonObj=jsonObject.getJSONObject("user");

                        String dob=jsonObj.getString("dob");
                        String id=jsonObj.getString("id");
                        String age = jsonObj.getString("age");
                        String des=jsonObj.getString("description");
                        String height=jsonObj.getString("height");
                        pDialog.dismiss();
                        Age.setText(age);
                        Description.setText(des);
                        Dob.setText(dob);
                        Height.setText(height);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    DialogUtils.sweetAlertDialog.dismiss();
                    DialogUtils.showWarningAlertDialog(getActivity(), "Something went wrong");
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //DialogUtils.sweetAlertDialog.dismiss();
                // DialogUtils.showErrorTypeAlertDialog(getActivity(), "Server error");
                Log.d("error" , String.valueOf(error.getCause()));

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
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

