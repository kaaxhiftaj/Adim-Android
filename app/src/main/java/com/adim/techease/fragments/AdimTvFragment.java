package com.adim.techease.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adim.techease.Adapter.AdimTvAdapter;
import com.adim.techease.R;
import com.adim.techease.controllers.TvModel;
import com.adim.techease.utils.Alert_Utils;
import com.adim.techease.utils.Configuration;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AdimTvFragment extends Fragment  {
    RecyclerView recyclerView;
    List<TvModel> tvModels;
    AdimTvAdapter adimTvAdapter;
    RequestQueue requestQueue;
    android.support.v7.app.AlertDialog alertDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_adim_tv, container, false);

        recyclerView=(RecyclerView) view.findViewById(R.id.recycler_AdimTv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        requestQueue = Volley.newRequestQueue(getActivity());
        tvModels = new ArrayList<>();
        if (alertDialog==null)
        {
            alertDialog= Alert_Utils.createProgressDialog(getActivity());
            alertDialog.show();
        }
        apicall();
        adimTvAdapter=new AdimTvAdapter(getActivity(),tvModels);
        recyclerView.setAdapter(adimTvAdapter);
        return view;
    }

    private void apicall() {
//        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
//        pDialog.getProgressHelper().setBarColor(Color.parseColor("#7DB3D2"));
//        pDialog.setTitleText("Loading");
//        pDialog.setCancelable(true);
//        pDialog.show();
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, Configuration.USER_URL+"App/adimtv",
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("zma respo", String.valueOf(response));

                        try{
                            JSONArray jsonArray = response.getJSONArray("user ");
                            for(int i=0; i<jsonArray.length(); i++){

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                TvModel modelTv = new TvModel();
                                modelTv.setTypeTv(jsonObject.getString("type"));
                                modelTv.setLinkTv(jsonObject.getString("link"));
                                modelTv.setTitleTv(jsonObject.getString("title"));
                                Log.d("zmaTitle",jsonObject.getString("title"));
                                modelTv.setId(jsonObject.getString("id"));
                                tvModels.add(modelTv);

                            }
                            if (alertDialog!=null)
                                alertDialog.dismiss();
                            adimTvAdapter.notifyDataSetChanged();
                            //pDialog.dismiss();
                        }catch(JSONException e){
                            if (alertDialog!=null)
                                alertDialog.dismiss();
                           // pDialog.dismiss();
                            e.printStackTrace();}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (alertDialog!=null)
                            alertDialog.dismiss();
                       // pDialog.dismiss();
                        Log.e("Volley", String.valueOf(error.getCause()));

                    }
                }
        );
        requestQueue.add(jor);

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
