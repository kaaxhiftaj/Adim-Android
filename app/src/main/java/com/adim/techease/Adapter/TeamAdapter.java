package com.adim.techease.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adim.techease.R;
import com.adim.techease.controllers.TeamModel;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Adam Noor on 26-Oct-17.
 */

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.MyViewHolder>  {
    List<TeamModel> model;
    Context context;

    public TeamAdapter(Context context, List<TeamModel> teamModel) {
        this.context=context;
        this.model=teamModel;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customteam, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final TeamModel mTeam=model.get(position);
        holder.Name.setText(mTeam.getTeamTitle());
        holder.Desc.setText(mTeam.getTeamDescription());
        holder.Desig.setText(mTeam.getTeamDesignation());
        Glide.with(context).load("http://adadigbomma.com/panel/images/"+mTeam.getTeamImage()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView Name,Desig,Desc;
        Typeface typeface;
        public MyViewHolder(View itemView) {
            super(itemView);

            typeface=Typeface.createFromAsset(context.getAssets(),"myfont.ttf");

            imageView=(ImageView)itemView.findViewById(R.id.ivPicTeam);
            Name=(TextView)itemView.findViewById(R.id.tvTeamName);
            Desig=(TextView)itemView.findViewById(R.id.tvDesig);
            Desc=(TextView)itemView.findViewById(R.id.tvDesc);

            Name.setTypeface(typeface);
            Desc.setTypeface(typeface);
            Desig.setTypeface(typeface);
        }
    }
}
