package com.adim.techease.fragments;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adim.techease.R;
import com.adim.techease.utils.Alert_Utils;
import com.bumptech.glide.Glide;

public class TeamDetailFragment extends Fragment {

    TextView tvDescriptionTitle,tvPersonName,tvDescription,tvDesignation,tvDesignationTitle;
    ImageView ivPersonImage;
    Typeface typefaceReg,typefaceBold;
    String strPersonName,strDescription,strDesignation,strPersonImage;
    android.support.v7.app.AlertDialog alertDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_team_detail, container, false);

        typefaceBold=Typeface.createFromAsset(getActivity().getAssets(),"raleway_bold.ttf");
        typefaceReg=Typeface.createFromAsset(getActivity().getAssets(),"raleway_reg.ttf");
        tvDescription=(TextView)view.findViewById(R.id.tvPersonDes);
        tvPersonName=(TextView)view.findViewById(R.id.tvPersonName);
        tvDescriptionTitle=(TextView)view.findViewById(R.id.tvDescriptionTitle);
        tvDesignation=(TextView)view.findViewById(R.id.tvPersonDesig);
        tvDesignationTitle=(TextView)view.findViewById(R.id.tvDesignationTitle);
        ivPersonImage=(ImageView)view.findViewById(R.id.ivPersonImage);

        strPersonName=getArguments().getString("name");
        strDescription=getArguments().getString("des");
        strDesignation=getArguments().getString("desig");
        strPersonImage=getArguments().getString("image");

        tvDesignation.setTypeface(typefaceReg);
        tvDesignation.setTypeface(typefaceReg);
        tvDesignationTitle.setTypeface(typefaceBold);
        tvDescriptionTitle.setTypeface(typefaceBold);
        tvPersonName.setTypeface(typefaceBold);

        tvPersonName.setText(strPersonName);
        if (strDescription!=null)
        {
            tvDescription.setText(strDescription);
        }

        tvDescription.setText("No details available right now will be uploaded soon");
        tvDesignation.setText(strDesignation);
        if (alertDialog==null)
        {
            alertDialog= Alert_Utils.createProgressDialog(getActivity());
            alertDialog.show();
        }
        Glide.with(getActivity()).load("http://adadigbomma.com/panel/images/"+strPersonImage).into(ivPersonImage);
        if (alertDialog!=null)
            alertDialog.dismiss();
        return view;
    }
}
