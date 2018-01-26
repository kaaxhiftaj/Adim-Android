package com.adim.techease.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adim.techease.R;
import com.adim.techease.controllers.TvModel;
import com.adim.techease.utils.Configuration;
import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.List;

/**
 * Created by Adam Noor on 25-Oct-17.
 */

public class AdimTvAdapter extends  RecyclerView.Adapter<AdimTvAdapter.MyViewHolder> {

    List<TvModel> tvModelsList;
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor ;
    public static final String Key="AIzaSyDnTSqXDRyGbksm4xd2HUuwXRKjUHvBygw";

    public AdimTvAdapter(Context context, List<TvModel> tvModels) {
        this.context=context;
        this.tvModelsList=tvModels;
    }

    @Override
    public AdimTvAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customtv, parent, false);

        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final TvModel model=tvModelsList.get(position);
        holder.textViewTitle.setText(model.getTitleTv());
        if (model.getTypeTv().equals("video")) {
            holder.ivShareVideos.setVisibility(View.VISIBLE);
            holder.frameLayout.setVisibility(View.VISIBLE);
            holder.imageView.setVisibility(View.GONE);
            holder.RLoverThumbView.setVisibility(View.VISIBLE);
            final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener= new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                @Override
                public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {

                    youTubeThumbnailView.setVisibility(View.VISIBLE);
                    holder.btnPlay.setVisibility(View.VISIBLE);
                    holder.ivShareVideos.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent;

                            intent = new Intent(Intent.ACTION_SEND);

                            intent.putExtra(Intent.EXTRA_TITLE,model.getTitleTv());

                            intent.putExtra(Intent.EXTRA_TEXT,"Youtube Video Link \n"+model.getLinkTv());

                            intent.setType("text/plain");

                            context.startActivity(Intent.createChooser(intent, "choose one"));
                        }
                    });


                }

                @Override
                public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                }
            };
            holder.youtubeTview.initialize(Key, new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                    youTubeThumbnailLoader.setVideo(model.getId());
                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
                }

                @Override
                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                }
            });

            holder.btnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(context).equals(YouTubeInitializationResult.SUCCESS)){
                        Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) context, Key, model.getId() );
                        context.startActivity(intent);
                        sharedPreferences = context.getSharedPreferences(Configuration.MY_PREF, Context.MODE_PRIVATE);
                        editor = sharedPreferences.edit();
//                        String jugad="back";
//                        editor.putString("back",jugad).commit();

                    }else{
                        Toast.makeText(context, "Please download youtube app", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.youtube")));

                    }

                }

            });


        }
        else
        {
            holder.imageView.setVisibility(View.VISIBLE);
            holder.RLoverThumbView.setVisibility(View.GONE);
            holder.btnPlay.setVisibility(View.GONE);
            holder.frameLayout.setVisibility(View.GONE);
            holder.youtubeTview.setVisibility(View.GONE);
            holder.ivShareVideos.setVisibility(View.INVISIBLE);
            Glide.with(context).load("http://adadigbomma.com/panel/images/gallery/"+ model.getLinkTv()).into(holder.imageView);
            //    viewHolder.textViewTitle.setText(model.getTitleTv());
        }


    }

    @Override
    public int getItemCount() {
        return tvModelsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        FrameLayout frameLayout;
        ImageView imageView,ivShareVideos;
        TextView textViewTitle;
        RelativeLayout RLoverThumbView;
        YouTubeThumbnailView youtubeTview;
        ImageView btnPlay;
        Typeface typefaceReg,typefaceBold;
        public MyViewHolder(View itemView) {
            super(itemView);
         typefaceReg = Typeface.createFromAsset(context.getAssets(), "raleway_reg.ttf");
           typefaceBold = Typeface.createFromAsset(context.getAssets(), "raleway_bold.ttf");
           imageView = (ImageView) itemView.findViewById(R.id.iv_photoTv);
            textViewTitle = (TextView) itemView.findViewById(R.id.tvTitleTv);
            textViewTitle.setTypeface(typefaceBold);
            frameLayout=(FrameLayout)itemView.findViewById(R.id.FrameTv);
            btnPlay = (ImageView) itemView.findViewById(R.id.btnPlayTv);
            ivShareVideos=(ImageView)itemView.findViewById(R.id.ivShareYoutubeVideo);
            youtubeTview = (YouTubeThumbnailView) itemView.findViewById(R.id.youtubeGalleryTv);
            RLoverThumbView = (RelativeLayout) itemView.findViewById(R.id.RloverTv);
        }
    }
}
