package com.adim.techease.fragments;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adim.techease.R;


public class AboutDetails extends Fragment {

    TextView aboutText;
    Typeface typeface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_about_details, container, false);
        aboutText = (TextView)v.findViewById(R.id.aboutText);
        typeface=Typeface.createFromAsset(getActivity().getAssets(),"myfont.ttf");
        aboutText.setTypeface(typeface);
        Bundle args = getArguments();
        String intent = args.getString("type","");

        if (intent.equals("whatisPeagant")){

            aboutText.setText("A beauty pageant or beauty contest is a competition that mainly focuses on the beauty of its contestants. In most cases such contests incorporates personality,intelligence,talent,body structure/figure as well as ability to show leadership amongst others. \n" +
                    "\n" +
                    "The phrase mainly refers to contest involving women and girls. For the men and boys,theirs is known more as a body building contest. \n" +
                    "\n" +
                    "The Ada Di Igbo Nma pageant is here to address several concerns that people have with the normal beauty contests and what they represent. There will be no exclusion of any contestant on the basis of height,size,figure,complexion etc as every contestant will be judged equally on their merits. This pageant is open only to all Igbo speaking ladies between the ages of 18-27 and the criteria for picking a winner will be down to the public as well as the judges. \n" +
                    "Good looks,intelligence,knowledge and practice of Igbo culture are some of the traits that will determine the eventual winner. Preserving the Igbo language and it's values ");
        }else if (intent.equals("aboutproject")){


            aboutText.setText("The Ada di Igbo nma projects aims to empower the Igbo family through women. This project will help to develop the confidence women need to influence the society. Ada di Igbo nma will equip our women into self-belief, nurture their motherly instinct and help preserve our Igbo culture.\n" +
                    "\n" +
                    "This pageant seeks to digress from the usual norm that only certain women with a pre-set physical features qualify to be referred to as ‘fit for pageant’. Hence the pageant is based on INTELLIGENCE, DIGNITY, POISE and KNOWLEDGE of the Igbo cultural heritage, norms and customs.\n" +
                    "\n" +
                    "The pageant seeks to digress from the Igbo way of life and the bastion rests of the mothers to preserve that. The organization and structure outlay for ‘Ada di Igbo nma’ projects is hinged on equal opportunity, transparency and professionalism.\n" +
                    "\n" +
                    "We promise to uphold the dignity of womanhood, educate and highlight ills against women, project the values of the Igbo culture and promote societal values and morals. \n");
        }

        return v;
    }

}
