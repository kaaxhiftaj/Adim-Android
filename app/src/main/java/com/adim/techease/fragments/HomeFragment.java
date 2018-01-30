package com.adim.techease.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.adim.techease.Adapter.AllContestentsAdapter;
import com.adim.techease.R;
import com.adim.techease.controllers.Contestents;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HomeFragment extends Fragment {

    GridView gridView;
    ArrayList<Contestents> contestentList ;
    AllContestentsAdapter adapterContestants;
    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        sharedPreferences = getActivity().getSharedPreferences(Configuration.MY_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        gridView = (GridView)v.findViewById(R.id.gridViewAllContestent);
        if (alertDialog==null)
        {
            alertDialog= Alert_Utils.createProgressDialog(getActivity());
            alertDialog.show();
        }
        apiCall();
        return  v;
    }


    public void apiCall() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Configuration.USER_URL+"App/getallContests"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("true")) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("user");
                        contestentList=new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject temp = jsonArray.getJSONObject(i);
                            Contestents contes = new Contestents();
                            contes.setContestentName(temp.getString("fullname"));
                            contes.setContestentImage(temp.getString("profile"));
                            contes.setContestentId(temp.getString("id"));
                            contestentList.add(contes);
                            if (alertDialog!=null)
                                alertDialog.dismiss();
                        }
                        if (getActivity()!=null)
                        {
                            adapterContestants=new AllContestentsAdapter(getActivity(),contestentList);
                            gridView.setAdapter(adapterContestants);
                        }


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