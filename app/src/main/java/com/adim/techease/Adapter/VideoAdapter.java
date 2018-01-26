package com.adim.techease.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adim.techease.R;
import com.adim.techease.controllers.VideoModel;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.List;

/**
 * Created by Adam Noor on 19-Oct-17.
 */

public class VideoAdapter  extends RecyclerView.Adapter<VideoAdapter.MyViewHolder>{

    List<VideoModel> videoModel;
    Context context;
    public static final String Key="AIzaSyDnTSqXDRyGbksm4xd2HUuwXRKjUHvBygw";
    public static String Id_s;

    public VideoAdapter(Context context, List<VideoModel> model){
        this.context=context;
        this.videoModel=model;


    }


    @Override
    public VideoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customvideo, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final VideoAdapter.MyViewHolder holder, final int position) {

        final VideoModel model=videoModel.get(position);
        holder.text.setText(model.getTitle());

       final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener= new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                youTubeThumbnailView.setVisibility(View.VISIBLE);
                holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
                holder.playButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }
        };
        holder.youTubeThumbnailView.initialize(Key, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

                Id_s = model.getId();
                youTubeThumbnailLoader.setVideo(model.getId());
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
            }
        });

        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(context).equals(YouTubeInitializationResult.SUCCESS)){

                        Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) context, Key,model.getId());
                        context.startActivity(intent);


                    }else{
                        Toast.makeText(context, "Please download youtube app", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.youtube")));

                    }

                }

        });

    }


    @Override
    public int getItemCount() {
        return videoModel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        protected RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        YouTubeThumbnailView youTubeThumbnailView;
        protected ImageView playButton;
        Typeface typeface;
        public MyViewHolder(View itemView) {
            super(itemView);
            typeface=Typeface.createFromAsset(context.getAssets(),"myfont.ttf");
            text=(TextView)itemView.findViewById(R.id.tvVideoTitle);
            text.setTypeface(typeface);
            playButton=(ImageView)itemView.findViewById(R.id.btnYoutube_player);
         //   playButton.setOnClickListener(this);
            relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
            youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);
        }


    }
}
