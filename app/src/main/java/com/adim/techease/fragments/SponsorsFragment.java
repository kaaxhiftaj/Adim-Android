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

    ImageView sponser1,sponser2,sponser3,sponser4,sponser5,sponser6,sponser7,sponser8,sponser9,sponser10,sponser11;
    WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sponsors, container, false);

        webView=(WebView)view.findViewById(R.id.wvSponser);

        sponser1=(ImageView) view.findViewById(R.id.ivSponser1);
        sponser2=(ImageView)view.findViewById(R.id.ivSponser2);
        sponser3=(ImageView)view.findViewById(R.id.ivSponser3);
        sponser4=(ImageView)view.findViewById(R.id.ivSponser4);
    //    sponser5=(ImageView)view.findViewById(R.id.ivSponser5);
        sponser6=(ImageView)view.findViewById(R.id.ivSponser6);
        sponser7=(ImageView)view.findViewById(R.id.ivSponser7);
      //  sponser8=(ImageView)view.findViewById(R.id.ivSponser8);
        sponser9=(ImageView)view.findViewById(R.id.ivSponser9);
        sponser10=(ImageView)view.findViewById(R.id.ivSponser10);
        sponser11=(ImageView)view.findViewById(R.id.ivsponser11);

        sponser1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.eventdiaryint.com"));
                startActivity(myIntent);
            }
        });
        sponser2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sunmark.co.uk/laser_oil.htm"));
                startActivity(myIntent);

            }
        });
        sponser3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://employme.ng/"));
                startActivity(myIntent);
            }
        });

//        sponser5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.swisstradezu.com/"));
//                startActivity(myIntent);
//            }
//        });
//

        sponser10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sweethomebathrooms.com/home/"));
                startActivity(myIntent);
            }
        });
//        sponser8.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sunmark.co.uk/laser_oil.htm"));
//                startActivity(myIntent);
//            }
//        });
        sponser9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://utecgreenworld.com/"));
                startActivity(myIntent);
            }
        });

        sponser11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sweethomebathrooms.com/home/index.php"));
                startActivity(myIntent);
            }
        });

        return view;
    }
}
