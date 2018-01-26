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
    Typeface typefaceReg;
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
        typefaceReg=Typeface.createFromAsset(getActivity().getAssets(), "raleway_bold.ttf");
        aboutPeagant.setTypeface(typefaceReg);
        whatisPeagant.setTypeface(typefaceReg);
        aboutPtoject.setTypeface(typefaceReg);
        cookies.setTypeface(typefaceReg);
        privacy.setTypeface(typefaceReg);

        aboutPeagant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aboutPeagant.setTextSize(17);
                aboutPeagant.setText("A beauty pageant or beauty contest is a competition that mainly focuses on the beauty of its contestants. In most cases such contests incorporates personality,intelligence,talent,body structure/figure as well as ability to show leadership amongst others. \n" +
                        "\n" +
                        "The phrase mainly refers to contest involving women and girls. For the men and boys,theirs is known more as a body building contest. \n" +
                        "\n" +
                        "The Ada Di Igbo Nma pageant is here to address several concerns that people have with the normal beauty contests and what they represent. There will be no exclusion of any contestant on the basis of height,size,figure,complexion etc as every contestant will be judged equally on their merits. This pageant is open only to all Igbo speaking ladies between the ages of 18-27 and the criteria for picking a winner will be down to the public as well as the judges. \n" +
                        "Good looks,intelligence,knowledge and practice of Igbo culture are some of the traits that will determine the eventual winner. Preserving the Igbo language and it's values ");
//                fragment = new AboutDetails();
//                Bundle bundle = new Bundle();
//                bundle.putString("type", "aboutPeagant");
//                fragment.setArguments(bundle);
//                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("tag").commit();
            }
        });

        whatisPeagant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whatisPeagant.setTextSize(17);
                whatisPeagant.setText("A beauty pageant or beauty contest is a competition that mainly focuses on the beauty of its contestants. In most cases such contests incorporates personality,intelligence,talent,body structure/figure as well as ability to show leadership amongst others. \n" +
                        "\n" +
                        "The phrase mainly refers to contest involving women and girls. For the men and boys,theirs is known more as a body building contest. \n" +
                        "\n" +
                        "The Ada Di Igbo Nma pageant is here to address several concerns that people have with the normal beauty contests and what they represent. There will be no exclusion of any contestant on the basis of height,size,figure,complexion etc as every contestant will be judged equally on their merits. This pageant is open only to all Igbo speaking ladies between the ages of 18-27 and the criteria for picking a winner will be down to the public as well as the judges. \n" +
                        "Good looks,intelligence,knowledge and practice of Igbo culture are some of the traits that will determine the eventual winner. Preserving the Igbo language and it's values ");

//                fragment = new AboutDetails();
//                Bundle bundle = new Bundle();
//                bundle.putString("type", "whatisPeagant");
//                fragment.setArguments(bundle);
//                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("tag").commit();

            }
        });

        aboutPtoject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aboutPtoject.setTextSize(17);
                aboutPtoject.setText("The Ada di Igbo nma projects aims to empower the Igbo family through women. This project will help to develop the confidence women need to influence the society. Ada di Igbo nma will equip our women into self-belief, nurture their motherly instinct and help preserve our Igbo culture.\n" +
                        "\n" +
                        "This pageant seeks to digress from the usual norm that only certain women with a pre-set physical features qualify to be referred to as ‘fit for pageant’. Hence the pageant is based on INTELLIGENCE, DIGNITY, POISE and KNOWLEDGE of the Igbo cultural heritage, norms and customs.\n" +
                        "\n" +
                        "The pageant seeks to digress from the Igbo way of life and the bastion rests of the mothers to preserve that. The organization and structure outlay for ‘Ada di Igbo nma’ projects is hinged on equal opportunity, transparency and professionalism.\n" +
                        "\n" +
                        "We promise to uphold the dignity of womanhood, educate and highlight ills against women, project the values of the Igbo culture and promote societal values and morals. \n");
//                fragment = new AboutDetails();
//                Bundle bundle = new Bundle();
//                bundle.putString("type", "aboutproject");
//                fragment.setArguments(bundle);
//                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("tag").commit();
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
