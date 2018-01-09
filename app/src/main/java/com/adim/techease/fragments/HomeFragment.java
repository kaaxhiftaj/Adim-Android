package com.adim.techease.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adim.techease.Adapter.AllContestentsAdapter;
import com.adim.techease.R;
import com.adim.techease.controllers.Contestents;
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
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    List<Contestents> contestentList ;
    AllContestentsAdapter adapterContestants;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.myNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        apiCall();
        contestentList = new ArrayList<>();
        adapterContestants = new AllContestentsAdapter(getActivity(), contestentList);
        recyclerView.setAdapter(adapterContestants);
        return  v;
    }


    public void apiCall() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#7DB3D2"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Configuration.USER_URL+"App/getallContests"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("zma  reg response", response);
                //   DialogUtils.sweetAlertDialog.dismiss();
                if (response.contains("true")) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("user");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject temp = jsonArray.getJSONObject(i);
                            Contestents contes = new Contestents();
                            contes.setContestentName(temp.getString("fullname"));
                            contes.setContestentImage(temp.getString("profile"));
                            contes.setContestentId(temp.getString("id"));
                            contestentList.add(contes);
                            pDialog.dismiss();

                        }
                        adapterContestants.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    pDialog.dismiss();
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