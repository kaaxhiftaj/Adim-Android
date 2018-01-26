package com.adim.techease.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.adim.techease.R;


public class SponsorsFragment extends Fragment {

    ImageView event_factory, veba,employme,parktonianhotels, laseroil, sweethomes, newage;
    WebView webView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sponsors, container, false);

        webView=(WebView)view.findViewById(R.id.wvSponser);

        event_factory =(ImageView) view.findViewById(R.id.event_factory);
        veba=(ImageView)view.findViewById(R.id.veba);
        employme =(ImageView)view.findViewById(R.id.employeme);
        parktonianhotels=(ImageView)view.findViewById(R.id.parktonianhotel);
        laseroil=(ImageView)view.findViewById(R.id.laseroil);
        sweethomes=(ImageView)view.findViewById(R.id.sweethomes);
        newage=(ImageView)view.findViewById(R.id.newage);

        event_factory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.eventdiaryint.com"));
                startActivity(myIntent);
            }
        });
        laseroil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sunmark.co.uk/laser_oil.htm"));
                startActivity(myIntent);

            }
        });
        employme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://employme.ng/"));
                startActivity(myIntent);
            }
        });


        sweethomes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sweethomebathrooms.com/home/"));
                startActivity(myIntent);
            }
        });

        veba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.veba.cz/en/"));
                startActivity(myIntent);
            }
        });

        parktonianhotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        newage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.newagechargers.com/"));
                startActivity(myIntent);
            }
        });


        return view;
    }
}
