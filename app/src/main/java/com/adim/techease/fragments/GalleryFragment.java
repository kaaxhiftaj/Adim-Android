package com.adim.techease.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.adim.techease.Adapter.GalleryAdapter;
import com.adim.techease.Adapter.VideoAdapter;
import com.adim.techease.R;
import com.adim.techease.controllers.Gallery;
import com.adim.techease.utils.Alert_Utils;
import com.adim.techease.utils.CheckInternetConnection;
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


public class GalleryFragment extends Fragment {

    GridView gridView;
   // RecyclerView recyclerView;
    ArrayList<Gallery> galleryList;
    GalleryAdapter galleryAdapter;
    String test;
    RequestQueue requestQueue;
    android.support.v7.app.AlertDialog alertDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_gallery,container,false);
        gridView=(GridView) view.findViewById(R.id.gridViewGallery);
        requestQueue = Volley.newRequestQueue(getActivity());
        galleryList=new ArrayList<>();

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

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, Configuration.USER_URL+"App/getmedia",
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("zma respo", String.valueOf(response));

                        try{

                            JSONArray jsonArray = response.getJSONArray("user ");

                            for(int i=0; i<jsonArray.length(); i++){

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Gallery gModel = new Gallery();
                                gModel.setId(jsonObject.getString("id"));
                                gModel.setType(jsonObject.getString("type"));
                                gModel.setLink(jsonObject.getString("link"));
                                gModel.setTitle(jsonObject.getString("title"));
                                gModel.setThumbnail(jsonObject.getString("thumbnail"));

                                galleryList.add(gModel);

                            }
                            galleryAdapter=new GalleryAdapter(getActivity(),galleryList);
                            gridView.setAdapter(galleryAdapter);
                            if (alertDialog!=null)
                                alertDialog.dismiss();

                        }catch(JSONException e){
                            if (alertDialog!=null)
                                alertDialog.dismiss();
                            Toast.makeText(getActivity(), "No Images added", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (alertDialog!=null)
                            alertDialog.dismiss();
                        Toast.makeText(getActivity(), "No Images added", Toast.LENGTH_SHORT).show();
                        Log.e("Volley", String.valueOf(error.getCause()));

                    }
                }
        );
        requestQueue.add(jor);

    }

    }


