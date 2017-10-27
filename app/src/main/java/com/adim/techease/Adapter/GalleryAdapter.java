package com.adim.techease.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adim.techease.R;
import com.adim.techease.controllers.Gallery;
import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.List;

/**
 * Created by Adam Noor on 19-Oct-17.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {

    private List<Gallery> galleryModels;
    private Context context;
    String test;
    public static final String Key="AIzaSyDnTSqXDRyGbksm4xd2HUuwXRKjUHvBygw";
    public static final String[] PlaylistI={"BrkB3NJSO78"};
    public static final String id="ZvMJD7UQVbI";

    public GalleryAdapter(Context context,List<Gallery> models)
    {
        this.context=context;
        this.galleryModels=models;
    }


    @Override
    public GalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customitem, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final GalleryAdapter.MyViewHolder holder, final int position) {

        final Gallery model = galleryModels.get(position);

        if (model.getType().equals("image")) {
            holder.imageView.setVisibility(View.VISIBLE);
            holder.layout.setVisibility(View.GONE);
            holder.RLoverThumbView.setVisibility(View.GONE);
            holder.btnPlay.setVisibility(View.GONE);
            holder.frameLayout.setVisibility(View.GONE);
            holder.youtubeTview.setVisibility(View.GONE);
            Glide.with(context).load("http://adadigbomma.com/panel/images/gallery/"+ model.getLink()).into(holder.imageView);
            holder.textViewTitle.setText(model.getTitle());

        } else {
            final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener= new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                @Override
                public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                    holder.frameLayout.setVisibility(View.VISIBLE);
                    youTubeThumbnailView.setVisibility(View.VISIBLE);
                    holder.imageView.setVisibility(View.GONE);
                    holder.RLoverThumbView.setVisibility(View.VISIBLE);
                    holder.layout.setVisibility(View.GONE);
                    holder.btnPlay.setVisibility(View.VISIBLE);
                    holder.textViewTitle.setText(model.getTitle());

                }

                @Override
                public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                }
            };
            holder.youtubeTview.initialize(Key, new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                   // Toast.makeText(context, String.valueOf(model.getThumbnail()), Toast.LENGTH_SHORT).show();
                    youTubeThumbnailLoader.setVideo(id);
                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
                }

                @Override
                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                    Toast.makeText(context, "Thumbnail not loaded", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }
    @Override
    public int getItemCount() {
        return galleryModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        FrameLayout frameLayout;
        ImageView imageView;
        TextView textViewTitle,textViewVideoTitle;
        LinearLayout layout;
        protected RelativeLayout RLoverThumbView;
        YouTubeThumbnailView youtubeTview;
        protected ImageView btnPlay;
        Typeface typeface;
        public MyViewHolder(View itemView) {
            super(itemView);
            typeface=Typeface.createFromAsset(context.getAssets(),"myfont.ttf");
            imageView = (ImageView) itemView.findViewById(R.id.iv_photo);
            textViewTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            textViewTitle.setTypeface(typeface);
            frameLayout=(FrameLayout)itemView.findViewById(R.id.Frame);
            layout = (LinearLayout) itemView.findViewById(R.id.newsVideo);
          //  textViewVideoTitle=(TextView)itemView.findViewById(R.id.tvtitleVideo);
//            textViewVideoTitle.setTypeface(typeface);
            btnPlay = (ImageView) itemView.findViewById(R.id.btnPlay);
            btnPlay.setOnClickListener(this);
            youtubeTview = (YouTubeThumbnailView) itemView.findViewById(R.id.youtubeGallery);
            RLoverThumbView = (RelativeLayout) itemView.findViewById(R.id.Rlover);

        }

        @Override
        public void onClick(View v) {
            Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) context, Key, id);
            context.startActivity(intent);
        }
    }
}
