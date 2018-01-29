package com.adim.techease.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adim.techease.R;
import com.adim.techease.controllers.Gallery;
import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;

/**
 * Created by Adam Noor on 19-Oct-17.
 */

public class GalleryAdapter extends BaseAdapter{

    private ArrayList<Gallery> galleryModels;
    private Context context;
    public static final String Key="AIzaSyCbuAooOEnFlWE3JFINgbktSq3Qw9PA8R4";
    public static String id;
    private LayoutInflater layoutInflater;
   MyViewHolder viewHolder = null;
     Typeface typefaceBold;
    android.support.v7.app.AlertDialog alertDialog;

    public GalleryAdapter(Context context,ArrayList<Gallery> models)
    {
        this.context=context;
        this.galleryModels=models;
        if (context!=null)
        {
            this.layoutInflater=LayoutInflater.from(context);
        }
    }

    @Override
    public int getCount() {
        if (galleryModels!=null) return galleryModels.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if(galleryModels != null && galleryModels.size() > i) return  galleryModels.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        final Gallery model=galleryModels.get(i);
        if(galleryModels != null && galleryModels.size() > i) return  galleryModels.size();
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       final Gallery model=galleryModels.get(i);
        viewHolder=new MyViewHolder() ;
        view=layoutInflater.inflate(R.layout.customitem,viewGroup,false);
        viewHolder.typeface = Typeface.createFromAsset(context.getAssets(), "raleway_bold.ttf");
        viewHolder.imageView = (ImageView) view.findViewById(R.id.iv_photo);
        viewHolder.textViewTitle = (TextView) view.findViewById(R.id.tvTitle);
        viewHolder.textViewTitle.setTypeface(viewHolder.typeface);
        viewHolder.frameLayout=(FrameLayout)view.findViewById(R.id.Frame);
        viewHolder.btnPlay = (ImageView) view.findViewById(R.id.btnPlay);
        viewHolder.youtubeTview = (YouTubeThumbnailView) view.findViewById(R.id.youtubeGallery);
        viewHolder.RLoverThumbView = (RelativeLayout) view.findViewById(R.id.Rlover);

        if (model.getType().equals("image")) {
            viewHolder.imageView.setVisibility(View.VISIBLE);
            viewHolder.RLoverThumbView.setVisibility(View.GONE);
            viewHolder.btnPlay.setVisibility(View.GONE);
            viewHolder.frameLayout.setVisibility(View.GONE);
            viewHolder.youtubeTview.setVisibility(View.GONE);
            Glide.with(context).load("http://adadigbomma.com/panel/images/gallery/"+ model.getLink()).into(viewHolder.imageView);
            viewHolder.textViewTitle.setText(model.getTitle());


            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.custom_zoom_image);
                    dialog.setCancelable(true);
                    typefaceBold=Typeface.createFromAsset(context.getAssets(),"raleway_bold.ttf");
                    ImageView img = (ImageView) dialog.findViewById(R.id.ivZoomImage);
                    Glide.with(context).load("http://adadigbomma.com/panel/images/gallery/"+model.getLink()).into(img);
                    dialog.show();
                }
            });
            viewHolder.textViewTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.custom_zoom_image);
                    dialog.setCancelable(true);
                    typefaceBold=Typeface.createFromAsset(context.getAssets(),"raleway_bold.ttf");
                    ImageView img = (ImageView) dialog.findViewById(R.id.ivZoomImage);
                    Glide.with(context).load("http://adadigbomma.com/panel/images/gallery/"+model.getLink()).into(img);
                    dialog.show();

                }
            });
            if (alertDialog!=null)
                alertDialog.dismiss();
        }
        else
        {
            final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener= new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                @Override
                public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                    viewHolder.frameLayout.setVisibility(View.VISIBLE);
                    youTubeThumbnailView.setVisibility(View.VISIBLE);
                    viewHolder.imageView.setVisibility(View.GONE);
                    viewHolder.textViewTitle.setText(model.getTitle());
                    viewHolder.RLoverThumbView.setVisibility(View.VISIBLE);
                    viewHolder.btnPlay.setVisibility(View.VISIBLE);


                }

                @Override
                public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                }
            };
            viewHolder.youtubeTview.initialize(Key, new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                    youTubeThumbnailLoader.setVideo(model.getId());
                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
                    if (alertDialog!=null)
                        alertDialog.dismiss();
                }

                @Override
                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                }
            });

            viewHolder.btnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(context).equals(YouTubeInitializationResult.SUCCESS)){
                        //This means that your device has the Youtube API Service (the app) and you are safe to launch it.
                        Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) context, Key, model.getId() );
                        context.startActivity(intent);

                    }else{
                        Toast.makeText(context, "Please download youtube app", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.youtube")));

                    }

                }

            });

        }
        view.setTag(viewHolder);
        return view;
    }


    public class MyViewHolder {

        FrameLayout frameLayout;
        ImageView imageView;
        TextView textViewTitle,textViewVideoTitle;
        protected RelativeLayout RLoverThumbView;
        YouTubeThumbnailView youtubeTview;
        protected ImageView btnPlay;
        Typeface typeface;


    }

}
