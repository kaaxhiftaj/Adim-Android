package com.adim.techease.Adapter;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adim.techease.R;
import com.adim.techease.controllers.TeamModel;
import com.adim.techease.fragments.TeamDetailFragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Adam Noor on 26-Oct-17.
 */

public class TeamAdapter extends BaseAdapter {
    ArrayList<TeamModel> model;
    Context context;
    private LayoutInflater layoutInflater;
    ViewHolder viewHolder = null;

    public TeamAdapter(Context context, ArrayList<TeamModel> teamModel) {
        this.context=context;
        this.model=teamModel;
        if (context!=null)
        {
            this.layoutInflater=LayoutInflater.from(context);
        }
    }

    @Override
    public int getCount() {
        if (model!=null) return model.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if(model != null && model.size() > i) return  model.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        final TeamModel teamModel=model.get(i);
        if(model != null && model.size() > i) return  model.size();
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final TeamModel tModel=model.get(i);
        viewHolder= new ViewHolder();
        view=layoutInflater.inflate(R.layout.customteam,viewGroup,false);
        viewHolder. typefaceBold=Typeface.createFromAsset(context.getAssets(),"raleway_bold.ttf");
        viewHolder.  typefaceReg=Typeface.createFromAsset(context.getAssets(),"raleway_reg.ttf");

        viewHolder.  imageView=(ImageView)view.findViewById(R.id.ivPicTeam);
        viewHolder.Name=(TextView)view.findViewById(R.id.tvTeamName);

        viewHolder.linearLayout=(LinearLayout)view.findViewById(R.id.parentLLTeam);

        viewHolder.Name.setTypeface(viewHolder.typefaceBold);

        viewHolder.Name.setText(tModel.getTeamTitle());
        Glide.with(context).load("http://adadigbomma.com/panel/images/"+tModel.getTeamImage()).into(viewHolder.imageView);

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new TeamDetailFragment();
                Bundle bundle=new Bundle();
                bundle.putString("name",tModel.getTeamTitle());
                bundle.putString("des",tModel.getTeamDescription());
                bundle.putString("desig",tModel.getTeamDesignation());
                bundle.putString("image",tModel.getTeamImage());
                fragment.setArguments(bundle);
                ((AppCompatActivity)context).getFragmentManager().beginTransaction().replace(R.id.mainFrame , fragment).addToBackStack("frag").commit();

            }
        });
        view.setTag(viewHolder);
        return view;
    }



    public class ViewHolder{
        ImageView imageView;
        TextView Name,Desig,Desc;
        Typeface typefaceBold,typefaceReg;
        LinearLayout linearLayout;

    }
}
