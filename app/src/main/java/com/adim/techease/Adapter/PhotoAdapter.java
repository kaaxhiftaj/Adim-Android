package com.adim.techease.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.adim.techease.R;
import com.adim.techease.controllers.PhotoModel;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Adam Noor on 19-Oct-17.
 */

public class PhotoAdapter extends BaseAdapter {


    private List<PhotoModel> contestents;
    private Context context;
    private LayoutInflater layoutInflater;
    MyViewHolder viewHolder = null;
    Typeface typefaceBold;
    public PhotoAdapter(Context context, List<PhotoModel> contestents) {

        this.context = context;
        this.contestents = contestents;
        if (context!=null)
        {
            this.layoutInflater=LayoutInflater.from(context);
        }
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
        final PhotoModel model=contestents.get(i);
        if(contestents != null && contestents.size() > i) return  contestents.size();
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final PhotoModel model=contestents.get(i);
        viewHolder=new MyViewHolder() ;
        view=layoutInflater.inflate(R.layout.customphoto,viewGroup,false);
        viewHolder.imageView=(ImageView)view.findViewById(R.id.ivPhotofrag);
        Glide.with(context).load("http://adadigbomma.com/panel/images/contestant/"+model.getFile()).into(viewHolder.imageView);
        final Drawable drawable=viewHolder.imageView.getDrawable();
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_zoom_image);
                dialog.setCancelable(true);
                ImageView img = (ImageView) dialog.findViewById(R.id.ivZoomImage);
                Glide.with(context).load("http://adadigbomma.com/panel/images/contestant/"+model.getFile()).into(img);
                dialog.show();
            }
        });
        view.setTag(viewHolder);
        return view;
    }


    public class MyViewHolder {

        ImageView imageView;

    }


}