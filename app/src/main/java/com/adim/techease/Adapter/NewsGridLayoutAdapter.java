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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adim.techease.R;
import com.adim.techease.controllers.NewsModel;
import com.adim.techease.fragments.NewsDetailsWebviewFragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Adam Noor on 10-Jan-18.
 */

public class NewsGridLayoutAdapter extends BaseAdapter {

    Context context;
    ArrayList<NewsModel> newsModels;
    private LayoutInflater layoutInflater;
    String id,newsLink;
    public NewsGridLayoutAdapter(Context context, ArrayList<NewsModel> arrayList) {

        this.context=context;
        this.newsModels=arrayList;
        if (context!=null)
        {
            this.layoutInflater=LayoutInflater.from(context);

        }
    }

    @Override
    public int getCount() {
        if(newsModels != null) return  newsModels.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if(newsModels != null && newsModels.size() > i) return  newsModels.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        final  NewsModel model=newsModels.get(i);
        if(newsModels != null && newsModels.size() > i) return  newsModels.size();
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final  NewsModel model=newsModels.get(i);
        ViewHolder viewHolder = null;
        viewHolder=new ViewHolder();
        view=layoutInflater.inflate(R.layout.customnews,viewGroup,false);
        viewHolder.imageView=(ImageView)view.findViewById(R.id.ivCustomNews);
        viewHolder.textView=(TextView) view.findViewById(R.id.customNewsTitle);
        viewHolder.linearLayout=(LinearLayout)view.findViewById(R.id.LinearNews);
        viewHolder.typefaceBold=Typeface.createFromAsset(context.getAssets(),"raleway_bold.ttf");
        viewHolder.textView.setTypeface(viewHolder.typefaceBold);
        viewHolder.textView.setText(model.getNewsTitle());
        Glide.with(context).load("http://adadigbomma.com/panel/images/"+model.getNewsImage()).into(viewHolder.imageView);
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new NewsDetailsWebviewFragment();
                Bundle bundle = new Bundle();
                newsLink=model.getNewsLink();
                bundle.putString("link", newsLink);
                bundle.putString("title",model.getNewsTitle());
                bundle.putString("des",model.getNewsDescription());
                bundle.putString("img",model.getNewsImage());
                fragment.setArguments(bundle);
                ((Activity)context).getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).addToBackStack("tag").commit();
            }
        });
        view.setTag(viewHolder);

        return view;
    }
    private class ViewHolder
    {
        ImageView imageView;
        TextView textView;
        Typeface typefaceBold;
        LinearLayout linearLayout;

    }
}
