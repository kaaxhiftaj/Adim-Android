package com.adim.techease.Adapter;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adim.techease.R;
import com.adim.techease.activities.MainActivity;
import com.adim.techease.controllers.Contestents;
import com.adim.techease.fragments.BioContestent;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by kaxhiftaj on 4/3/17.
 */

public class AllContestentsAdapter extends RecyclerView.Adapter<AllContestentsAdapter.MyViewHolder> {


    private List<Contestents> contestents;
    private Context context;
    public AllContestentsAdapter(Context context, List<Contestents> contestents) {

        this.context = context;
        this.contestents = contestents;

    }

    @Override
    public AllContestentsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_contestents, parent, false);

        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Contestents contest = contestents.get(position);
        //Pass the values of feeds object to Views
        holder.contestentname.setText(contest.getContestentName());
        Glide.with(context).load("http://adadigbomma.com/panel/images/contestant/"+contest.getContestentImage()).into(holder.contestentImage);
        holder.llItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position==0)
                {
                    String id=contest.getContestentId().toString();
                    Fragment fragment = new BioContestent();
                    Bundle bundle=new Bundle();
                    bundle.putString("id",id);
                    fragment.setArguments(bundle);
                    Activity activity = (MainActivity) context;
                    activity.getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).commit();
                }
                else
                    if (position==1)
                    {
                        String id=contest.getContestentId().toString();
                        Fragment fragment = new BioContestent();
                        Bundle bundle=new Bundle();
                        bundle.putString("id",id);
                        fragment.setArguments(bundle);
                        Activity activity = (MainActivity) context;
                        activity.getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).commit();
                    }

            }
        });

    }


    @Override
    public int getItemCount() {
        return contestents.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView contestentname;
        private  de.hdodenhof.circleimageview.CircleImageView contestentImage;
        LinearLayout llItemView;

        public MyViewHolder(View itemView) {
            super(itemView);
            contestentname = (TextView) itemView.findViewById(R.id.contestentName);
            contestentImage = (de.hdodenhof.circleimageview.CircleImageView) itemView.findViewById(R.id.contestentImage);
            llItemView = (LinearLayout)itemView.findViewById(R.id.ll_main_item);


        }
    }


}