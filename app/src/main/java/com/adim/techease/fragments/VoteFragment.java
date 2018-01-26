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
import android.widget.Button;

import com.adim.techease.Adapter.VoteAdapter;
import com.adim.techease.R;
import com.adim.techease.controllers.VoteModel;
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


public class VoteFragment extends Fragment {

    RecyclerView recyclerViewVote;
    List<VoteModel> voteModel;
    VoteAdapter voteAdapter;
    Button btnShare;
    Typeface typefaceReg,typefaceBold;
    android.support.v7.app.AlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_vote, container, false);
        typefaceReg=Typeface.createFromAsset(getActivity().getAssets(),"myfont.ttf");
        recyclerViewVote=(RecyclerView)view.findViewById(R.id.rvVote);
        recyclerViewVote.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (alertDialog==null)
        {
            alertDialog= Alert_Utils.createProgressDialog(getActivity());
            alertDialog.show();
        }
        apicall();
        voteModel=new ArrayList<>();
        voteAdapter=new VoteAdapter(getActivity(),voteModel);
        recyclerViewVote.setAdapter(voteAdapter);
        return view;
    }

    private void apicall() {
//        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
//        pDialog.getProgressHelper().setBarColor(Color.parseColor("#7DB3D2"));
//        pDialog.setTitleText("Loading");
//        pDialog.setCancelable(false);
//        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Configuration.USER_URL+"App/vote"
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
                            VoteModel votemodel=new VoteModel();
                            votemodel.setImage(temp.getString("profile_pic"));
                            votemodel.setTitle(temp.getString("name"));
                            votemodel.setVote(temp.getString("totalVotes"));
                            votemodel.setVoteContestentID(temp.getString("id"));
                            voteModel.add(votemodel);
                            if (alertDialog!=null)
                                alertDialog.dismiss();
                            //pDialog.dismiss();
                        }
                        voteAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (alertDialog!=null)
                            alertDialog.dismiss();
                       // pDialog.dismiss();
                    }


                } else {

                   // pDialog.dismiss();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //DialogUtils.sweetAlertDialog.dismiss();
                // DialogUtils.showErrorTypeAlertDialog(getActivity(), "Server error");
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


