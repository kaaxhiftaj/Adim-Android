package com.adim.techease.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.adim.techease.R;
import com.adim.techease.controllers.PhotoModel;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Adam Noor on 19-Oct-17.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {


    private List<PhotoModel> contestents;
    private Context context;

    public PhotoAdapter(Context context, List<PhotoModel> contestents) {

        this.context = context;
        this.contestents = contestents;

    }

    @Override
    public PhotoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customphoto, parent, false);

        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final PhotoModel contest = contestents.get(position);

        Glide.with(context).load("http://adadigbomma.com/panel/images/contestant/"+contest.getFile()).into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return contestents.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
           imageView=(ImageView)itemView.findViewById(R.id.ivPhotofrag);

        }
    }


}