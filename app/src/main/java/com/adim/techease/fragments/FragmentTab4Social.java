package com.adim.techease.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.adim.techease.R;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class FragmentTab4Social extends Fragment {

    ImageButton Fb,Tw,Linkedin,Mail;
    String getId,Face,Tweet,Link;
    WebView webiew;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_tab4_social, container, false);

        Fb=(ImageButton)v.findViewById(R.id.btnfb);
        Tw=(ImageButton)v.findViewById(R.id.btnTw);
        Linkedin=(ImageButton)v.findViewById(R.id.btnInsta);
        Mail=(ImageButton)v.findViewById(R.id.btnMail);
        getId=getArguments().getString("id");
        webiew=(WebView)v.findViewById(R.id.wv);

        apicall();
        return v;
    }

    private void apicall() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Configuration.USER_URL+"App/getsocial/9"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("zma  reg response", response);
                //   DialogUtils.sweetAlertDialog.dismiss();
                if (response.contains("true")) {
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        JSONObject jsonObj=jsonObject.getJSONObject("user");

                         Face=jsonObj.getString("facebook");
                         Tweet=jsonObj.getString("twitter");
                         Link=jsonObj.getString("linkedin");
//                        Toast.makeText(getActivity(), "Link"+Face, Toast.LENGTH_SHORT).show();

                            Fb.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    webiew.getSettings().setJavaScriptEnabled(true);
                                    webiew.getSettings().setPluginState(WebSettings.PluginState.ON);
                                    webiew.loadUrl("http://"+Face);
                                    webiew.setWebChromeClient(new WebChromeClient());
                                }
                            });
                            Tw.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    webiew.getSettings().setJavaScriptEnabled(true);
                                    webiew.getSettings().setPluginState(WebSettings.PluginState.ON);
                                    webiew.loadUrl("http://"+Tweet);
                                    webiew.setWebChromeClient(new WebChromeClient());
                                }
                            });
                            Linkedin.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    webiew.getSettings().setJavaScriptEnabled(true);
                                    webiew.getSettings().setPluginState(WebSettings.PluginState.ON);
                                    webiew.loadUrl("http://"+Link);
                                    webiew.setWebChromeClient(new WebChromeClient());
                                }
                            });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
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

