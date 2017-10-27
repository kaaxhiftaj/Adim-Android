package com.adim.techease.Adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adim.techease.R;
import com.adim.techease.controllers.NewsModel;
import com.adim.techease.fragments.NewsDetailsFragment;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Adam Noor on 26-Oct-17.
 */

public class NewsAdapter extends RecyclerView.Adapter <NewsAdapter.MyViewHolder>{
    List<NewsModel> model;
    Context context;
    String id;

    public NewsAdapter(Context context, List<NewsModel> newsModels) {
        this.context=context;
        this.model=newsModels;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customnews, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final NewsModel nModel=model.get(position);
        id=String.valueOf(nModel.getNewsid());
        holder.textView.setText(nModel.getNewsTitle());
        Glide.with(context).load("http://adadigbomma.com/panel/images/"+nModel.getNewsImage()).into(holder.imageView);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment  fragment = new NewsDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                fragment.setArguments(bundle);
                ((Activity)context).getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).addToBackStack("tag").commit();
            }
        });

    }



    @Override
    public int getItemCount() {
        return model.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        Typeface typeface;
        LinearLayout linearLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            typeface=Typeface.createFromAsset(context.getAssets(),"myfont.ttf");
            imageView=(ImageView)itemView.findViewById(R.id.ivCustomNews);
            textView=(TextView)itemView.findViewById(R.id.customNewsTitle);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.LinearNews);
            textView.setTypeface(typeface);
 }


    }
}
