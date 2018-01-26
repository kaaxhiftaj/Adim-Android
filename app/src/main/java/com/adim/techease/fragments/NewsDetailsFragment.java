package com.adim.techease.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.adim.techease.R;
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
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class NewsDetailsFragment extends Fragment {

    ImageView imageView;
    Button shareBtn;
    TextView name,description;
    Typeface typefaceReg,typefaceBold;
    String id,imageUrl,strTitle,strDes;
    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_news_details, container, false);

        sharedPreferences = getActivity().getSharedPreferences(Configuration.MY_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        imageView=(ImageView)view.findViewById(R.id.ivNewsDetails);
        name=(TextView)view.findViewById(R.id.tvImageItitleNewsDetials);
        description=(TextView)view.findViewById(R.id.tvNewsDetailsDescription);
       shareBtn=(Button) view.findViewById(R.id.btnShareNews);
        typefaceReg=Typeface.createFromAsset(getActivity().getAssets(),"raleway_reg.ttf");
        typefaceBold=Typeface.createFromAsset(getActivity().getAssets(),"raleway_bold.ttf");
        name.setTypeface(typefaceBold);
        description.setTypeface(typefaceReg);
        Bundle bundle = null;
        bundle = getActivity().getIntent().getExtras();
        id = getArguments().getString("id");
        Log.d("id" , id );
        if (alertDialog==null)
        {
            alertDialog= Alert_Utils.createProgressDialog(getActivity());
            alertDialog.show();
        }
        apicall();

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_SUBJECT,strTitle);
               // intent.putExtra(Intent.EXTRA_TEXT,"News image link:\nhttp://adadigbomma.com/panel/images/" +imageUrl+"\nDescription"+ strDes);
                intent.putExtra(Intent.EXTRA_TEXT,strTitle+":\nhttp://adim.app.link/m1vtHA0jUJ");
                intent.setType("text/plain");

                startActivity(Intent.createChooser(intent, "choose one"));
            }
        });
        return view;
    }


    private void apicall() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Configuration.USER_URL+"App/newsdetail/"+id
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("zma  reg response", response);
                //   DialogUtils.sweetAlertDialog.dismiss();
                if (response.contains("true")) {
                    try {
                        JSONObject temp =new JSONObject(response).getJSONObject("user ");

                            name.setText(String.valueOf(temp.getString("title")));
                            description.setText(String.valueOf(temp.getString("description")));
                            imageUrl=temp.getString("image");
                            strDes=temp.getString("description");
                            strTitle=temp.getString("title");
                            Glide.with(getActivity()).load("http://adadigbomma.com/panel/images/"+temp.getString("image")).into(imageView);
                          //  pDialog.dismiss();
                        if (alertDialog!=null)
                            alertDialog.dismiss();
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
