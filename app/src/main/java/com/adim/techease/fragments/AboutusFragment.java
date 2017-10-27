package com.adim.techease.fragments;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.adim.techease.R;


public class AboutusFragment extends Fragment {
TextView  aboutPeagant , whatisPeagant, aboutPtoject ;
    Button cookies, privacy ;
    Fragment fragment;
    Typeface typeface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_aboutus, container, false);

        aboutPeagant = (TextView)v.findViewById(R.id.aboutPegant);
        whatisPeagant = (TextView)v.findViewById(R.id.whatIsPeageant);
        aboutPtoject = (TextView)v.findViewById(R.id.aboutProject);
        cookies = (Button)v.findViewById(R.id.cookies);
        privacy = (Button)v.findViewById(R.id.privacy);
        typeface=Typeface.createFromAsset(getActivity().getAssets(),"myfont.ttf");
        aboutPeagant.setTypeface(typeface);
        whatisPeagant.setTypeface(typeface);
        aboutPtoject.setTypeface(typeface);
        cookies.setTypeface(typeface);
        privacy.setTypeface(typeface);



        aboutPeagant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new AboutDetails();
                Bundle bundle = new Bundle();
                bundle.putString("type", "aboutPeagant");
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("tag").commit();
            }
        });

        whatisPeagant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment = new AboutDetails();
                Bundle bundle = new Bundle();
                bundle.putString("type", "whatisPeagant");
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("tag").commit();

            }
        });

        aboutPtoject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new AboutDetails();
                Bundle bundle = new Bundle();
                bundle.putString("type", "aboutproject");
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("tag").commit();
            }
        });

        cookies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return v;

    }

    }
