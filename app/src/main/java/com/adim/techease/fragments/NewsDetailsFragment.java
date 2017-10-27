package com.adim.techease.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adim.techease.R;
import com.adim.techease.utils.Configuration;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class NewsDetailsFragment extends Fragment {

    ImageView imageView;
    TextView name,description;
    Typeface typeface;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_news_details, container, false);
        imageView=(ImageView)view.findViewById(R.id.ivNewsDetails);
        name=(TextView)view.findViewById(R.id.tvImageItitleNewsDetials);
        description=(TextView)view.findViewById(R.id.tvNewsDetailsDescription);
        typeface=Typeface.createFromAsset(getActivity().getAssets(),"myfont.ttf");
        name.setTypeface(typeface);
        description.setTypeface(typeface);
        apicall();
        return view;
    }

    private void apicall() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#7DB3D2"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Configuration.USER_URL+"App/news/1"
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
                            name.setText(String.valueOf(temp.getString("title")));
                            description.setText(String.valueOf(temp.getString("description")));
                            Toast.makeText(getActivity(), String.valueOf(temp.getString("image")), Toast.LENGTH_SHORT).show();
                            Glide.with(getActivity()).load("http://adadigbomma.com/panel/images/"+temp.getString("image")).into(imageView);
                            pDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {

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
