package com.adim.techease.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.adim.techease.Adapter.VideoAdapter;
import com.adim.techease.R;
import com.adim.techease.controllers.VideoModel;
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
    Button btnShare;
    Typeface typeface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_tab3_videos, container, false);
        btnShare=(Button)v.findViewById(R.id.btnShareVideo);
        typeface=Typeface.createFromAsset(getActivity().getAssets(),"myfont.ttf");
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

        recyclerView=(RecyclerView)v.findViewById(R.id.rvVideos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getId=getArguments().getString("id");
        videoModels=new ArrayList<>();
        apicall();
        videoAdapter=new VideoAdapter(getActivity(),videoModels);
        recyclerView.setAdapter(videoAdapter);
        return v;
    }

    private void apicall() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#7DB3D2"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Configuration.USER_URL+"App/getvideos/8"
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
                            pDialog.dismiss();
                        }
                        videoAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
//                    DialogUtils.sweetAlertDialog.dismiss();
                    //                  DialogUtils.showWarningAlertDialog(getActivity(), "Something went wrong");
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

//    @Override
//    public void onDetach() {
//        super.onDetach();
//       FragmentTab3Videos fragmentTab3Videos=new FragmentTab3Videos();
//        getFragmentManager().beginTransaction().replace(R.id.mainFrame,fragmentTab3Videos).commit();
//
//    }
}




