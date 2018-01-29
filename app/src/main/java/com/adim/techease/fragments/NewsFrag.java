package com.adim.techease.fragments;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.adim.techease.Adapter.NewsGridLayoutAdapter;
import com.adim.techease.Adapter.VoteAdapter;
import com.adim.techease.R;
import com.adim.techease.controllers.NewsModel;
import com.adim.techease.utils.Alert_Utils;
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

public class NewsFrag extends Fragment {

    RecyclerView recyclerView;
    NewsGridLayoutAdapter newsAdapter;
    Typeface typefaceBold;
    List<NewsModel> newsModelList;
    android.support.v7.app.AlertDialog alertDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_news2, container, false);

        recyclerView=(RecyclerView)view.findViewById(R.id.rvAllNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        typefaceBold=Typeface.createFromAsset(getActivity().getAssets(),"raleway_bold.ttf");

        if (alertDialog==null)
        {
            alertDialog= Alert_Utils.createProgressDialog(getActivity());
            alertDialog.show();
        }
        apicall();
        newsModelList = new ArrayList<>();
        newsAdapter=new NewsGridLayoutAdapter(getActivity(),newsModelList);
        recyclerView.setAdapter(newsAdapter);

        return view;
    }

    private  void apicall()
    {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Configuration.USER_URL+"App/news"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("zma  reg response", response);
                //   DialogUtils.sweetAlertDialog.dismiss();
                if (response.contains("true")) {

                    try {
                        newsModelList.clear();
                        JSONObject jsonObject=new JSONObject(response);
                        JSONArray jsonArr=jsonObject.getJSONArray("user ");
                        for (int i=0; i<jsonArr.length(); i++)
                        {
                            JSONObject temp = jsonArr.getJSONObject(i);
                            NewsModel newsModel=new NewsModel();
                            newsModel.setNewsImage(temp.getString("image"));
                            newsModel.setNewsLink(temp.getString("link"));
                            newsModel.setNewsTitle(temp.getString("title"));
                            newsModel.setNewsDescription(temp.getString("description"));
                            newsModel.setNewsid(temp.getString("id"));
                            newsModelList.add(newsModel);
                            if (alertDialog!=null)
                                alertDialog.dismiss();

                        }
                        newsAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        if (alertDialog!=null)
                            alertDialog.dismiss();
                        e.printStackTrace();
                    }


                } else {
                    if (alertDialog!=null)
                        alertDialog.dismiss();
                   // pDialog.dismiss();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (alertDialog!=null)
                    alertDialog.dismiss();
               // pDialog.dismiss();
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

    @Override
    public void onResume() {
        super.onResume();
        apicall();
        newsAdapter =new NewsGridLayoutAdapter(getActivity(),newsModelList);
        recyclerView.setAdapter(newsAdapter);
    }
}
