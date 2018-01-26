package com.adim.techease.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.adim.techease.Adapter.PhotoAdapter;
import com.adim.techease.R;
import com.adim.techease.controllers.PhotoModel;
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
import java.util.Map;

public class FragmentTab2Photos extends Fragment {

    GridView gridView;
    ArrayList<PhotoModel> models;
    String getId,strContestentName;
    PhotoAdapter photoAdapter;
    Typeface typefaceReg,typefaceBold;
    android.support.v7.app.AlertDialog alertDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_tab2_photos, container, false);

        typefaceReg=Typeface.createFromAsset(getActivity().getAssets(),"raleway_reg.ttf");
        typefaceBold=Typeface.createFromAsset(getActivity().getAssets(),"raleway_bold.ttf");
        gridView=(GridView) v.findViewById(R.id.gridViewTabPhoto);
        getId=getArguments().getString("id");
        strContestentName=getArguments().getString("name");
        if (alertDialog==null)
        {
            alertDialog= Alert_Utils.createProgressDialog(getActivity());
            alertDialog.show();
        }
        apicall();
        return v;
    }

    private void apicall() {
//        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
//        pDialog.getProgressHelper().setBarColor(Color.parseColor("#7DB3D2"));
//        pDialog.setTitleText("Loading");
//        pDialog.setCancelable(false);
//        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Configuration.USER_URL+"App/getphotos/"+getId
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("zma  reg response", response);
                //   DialogUtils.sweetAlertDialog.dismiss();
                if (response.contains("true")) {
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        JSONArray jsonArr=jsonObject.getJSONArray("user");
                        models=new ArrayList<>();
                        for (int i=0; i<jsonArr.length(); i++)
                        {
                            JSONObject temp = jsonArr.getJSONObject(i);
                            PhotoModel photoModel=new PhotoModel();
                            photoModel.setFile(temp.getString("file"));
                            photoModel.setType(temp.getString("type"));
                            photoModel.setId(temp.getString("id"));
                            photoModel.setName(strContestentName);
                            models.add(photoModel);
                            //pDialog.dismiss();
                        }
                        if (getActivity()!=null)
                        {
                            photoAdapter=new PhotoAdapter(getActivity(),models);
                            gridView.setAdapter(photoAdapter);
                            if (alertDialog!=null)
                            alertDialog.dismiss();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (alertDialog!=null)
                            alertDialog.dismiss();
                       // pDialog.dismiss();
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
