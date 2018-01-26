package com.adim.techease.Adapter;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adim.techease.R;
import com.adim.techease.activities.MainActivity;
import com.adim.techease.controllers.Contestents;
import com.adim.techease.fragments.BioContestent;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by kaxhiftaj on 4/3/17.
 */

public class AllContestentsAdapter extends BaseAdapter{

    ArrayList<Contestents> contestents;
    Context context;
    private LayoutInflater layoutInflater;

    public AllContestentsAdapter(Context context,ArrayList<Contestents> contestents)
    {
        this.context=context;
        this.contestents=contestents;
        this.layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (contestents!=null) return contestents.size();

        return 0;
    }

    @Override
    public Object getItem(int i) {
        if(contestents != null && contestents.size() > i) return  contestents.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        final Contestents model=contestents.get(i);
        if(contestents != null && contestents.size() > i) return  contestents.size();
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Contestents model=contestents.get(i);
        MyViewHolder viewHolder = null;
        viewHolder=new MyViewHolder() ;
        view=layoutInflater.inflate(R.layout.custom_contestents,viewGroup,false);
        viewHolder.typefaceBold = Typeface.createFromAsset(context.getAssets(), "raleway_bold.ttf");
        viewHolder.contestentname = (TextView) view.findViewById(R.id.contestentName);
        viewHolder.contestentname.setTypeface(viewHolder.typefaceBold);
        viewHolder.contestentImage = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.contestentImage);
        viewHolder.llItemView = (LinearLayout)view.findViewById(R.id.ll_main_item);

        viewHolder.contestentname.setText(model.getContestentName());
        Glide.with(context).load("http://adadigbomma.com/panel/images/contestant/"+model.getContestentImage()).into(viewHolder.contestentImage);
        viewHolder.llItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=model.getContestentId().toString();
                Fragment fragment = new BioContestent();
                Bundle bundle=new Bundle();
                bundle.putString("id",id);
                fragment.setArguments(bundle);
                Activity activity = (MainActivity) context;
                activity.getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).addToBackStack("abc").commit();
            }
        });
        view.setTag(viewHolder);
        return view;
    }

    private class MyViewHolder  {

        private TextView contestentname;
        private  de.hdodenhof.circleimageview.CircleImageView contestentImage;
        LinearLayout llItemView;
        Typeface typefaceBold;

    }


}