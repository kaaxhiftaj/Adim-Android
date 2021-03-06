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
import android.widget.Toast;

import com.adim.techease.Adapter.NewsGridLayoutAdapter;
import com.adim.techease.Adapter.TeamAdapter;
import com.adim.techease.R;
import com.adim.techease.controllers.TeamModel;
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
import java.util.Map;


public class OurTeamFragment extends Fragment {

    GridView gridView;
    ArrayList<TeamModel> teamModel;
    TeamAdapter teamAdapter;
    android.support.v7.app.AlertDialog alertDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_our_team, container, false);

        gridView=(GridView) view.findViewById(R.id.gridViewAdimTeam);
        teamModel=new ArrayList<>();
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
        return view;
    }

    private void apicall() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Configuration.USER_URL+"App/team"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("zma  reg response", response);
                //   DialogUtils.sweetAlertDialog.dismiss();
                if (response.contains("true")) {
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        JSONArray jsonArr=jsonObject.getJSONArray("user ");
                        for (int i=0; i<jsonArr.length(); i++)
                        {
                            JSONObject temp = jsonArr.getJSONObject(i);
                            TeamModel teammodel=new TeamModel();
                            teammodel.setTeamTitle(temp.getString("title"));
                            teammodel.setTeamImage(temp.getString("image"));
                            teammodel.setTeamDescription(temp.getString("description"));
                            teammodel.setTeamDesignation(temp.getString("designation"));
                            teamModel.add(teammodel);

                            if (alertDialog!=null)
                                alertDialog.dismiss();
                        }
                        teamAdapter=new TeamAdapter(getActivity(),teamModel);
                        gridView.setAdapter(teamAdapter);

                    } catch (JSONException e) {
                        if (alertDialog!=null)
                            alertDialog.dismiss();
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
