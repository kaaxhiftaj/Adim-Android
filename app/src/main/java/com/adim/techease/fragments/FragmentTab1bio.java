package com.adim.techease.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.adim.techease.Adapter.NewsGridLayoutAdapter;
import com.adim.techease.R;
import com.adim.techease.utils.Alert_Utils;
import com.adim.techease.utils.CheckInternetConnection;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FragmentTab1bio extends Fragment {

    TextView Description, Age, Height, Dob,textView1,textView2,textView3;
    String getId;
    Typeface typefaceReg,typefaceBold;
    android.support.v7.app.AlertDialog alertDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_fragment_tab1bio, container, false);



        typefaceReg=Typeface.createFromAsset(getActivity().getAssets(),"raleway_reg.ttf");
        typefaceBold=Typeface.createFromAsset(getActivity().getAssets(),"raleway_bold.ttf");
        Description = (TextView) v.findViewById(R.id.tvDescription);
        Age = (TextView) v.findViewById(R.id.tvAge);
        Height = (TextView) v.findViewById(R.id.tvHeight);
        Dob = (TextView) v.findViewById(R.id.tvDob);
        textView1=(TextView)v.findViewById(R.id.txt1);
        textView2=(TextView)v.findViewById(R.id.txt2);
        textView3=(TextView)v.findViewById(R.id.txt3);

        getId=getArguments().getString("id");
        Description.setTypeface(typefaceReg);
        Age.setTypeface(typefaceReg);
        Height.setTypeface(typefaceReg);
        Dob.setTypeface(typefaceReg);
        textView1.setTypeface(typefaceBold);
        textView2.setTypeface(typefaceBold);
        textView3.setTypeface(typefaceBold);
        if (alertDialog==null)



        if(CheckInternetConnection.isInternetAvailable(getActivity()))
        {

            if (alertDialog==null)
            {
                alertDialog= Alert_Utils.createProgressDialog(getActivity());
                alertDialog.show();
            }
            apicall();


        }
        else
        {
            Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_SHORT).show();
        }

        return v;
    }



    private void apicall() {

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

                        if (alertDialog!=null)
                            alertDialog.dismiss();
                        Age.setText(age);
                        Description.setText(des);
                        Dob.setText(dob);
                        Height.setText(height);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    if (alertDialog!=null)
                        alertDialog.dismiss();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (alertDialog!=null)
                    alertDialog.dismiss();
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

