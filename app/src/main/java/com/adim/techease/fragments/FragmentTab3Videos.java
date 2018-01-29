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
import android.widget.Toast;

import com.adim.techease.Adapter.VideoAdapter;
import com.adim.techease.R;
import com.adim.techease.controllers.VideoModel;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public  class FragmentTab3Videos extends Fragment{


    RecyclerView recyclerView;
    List<VideoModel> videoModels;
    VideoAdapter videoAdapter;
    String getId;
    Typeface typefaceBold;
    String GetId;
    SweetAlertDialog pDialog;
    android.support.v7.app.AlertDialog alertDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_tab3_videos, container, false);
        GetId=getArguments().getString("id");
        typefaceBold=Typeface.createFromAsset(getActivity().getAssets(),"raleway_bold.ttf");
        recyclerView=(RecyclerView)v.findViewById(R.id.rvVideos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getId=getArguments().getString("id");
        videoModels=new ArrayList<>();

        if(CheckInternetConnection.isInternetAvailable(getActivity()))
        {
            if (alertDialog==null)
            {
                alertDialog= Alert_Utils.createProgressDialog(getActivity());
                alertDialog.show();
            }
            apicall();
            videoAdapter=new VideoAdapter(getActivity(),videoModels);
            recyclerView.setAdapter(videoAdapter);

        }
        else
        {
            Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_SHORT).show();
        }

        return v;
    }

    private void apicall() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Configuration.USER_URL+"App/getvideos/"+GetId
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("zma  reg response", response);
                if (response.contains("true")) {
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        JSONArray jsonArr=jsonObject.getJSONArray("user");
                        for (int i=0; i<jsonArr.length(); i++)
                        {
                            JSONObject temp = jsonArr.getJSONObject(i);
                            VideoModel videoModel=new VideoModel();
                            videoModel.setTitle(temp.getString("title"));
                            videoModel.setThumbnails(temp.getString("thumbnail"));
                            videoModel.setLink(temp.getString("link"));
                            videoModel.setId(temp.getString("id"));
                            videoModels.add(videoModel);
                            if (alertDialog!=null)
                                alertDialog.dismiss();

                        }
                        videoAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (alertDialog!=null)
                            alertDialog.dismiss();

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




