package com.adim.techease.custom;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;

import java.io.IOException;

/**
 * Created by Jarvis on 4/20/2017.
 */

public class CustomVideoPlayer extends TextureView implements TextureView.SurfaceTextureListener {

    Context context;
    String url;
    MediaPlayer mp;
    Surface surface;
    SurfaceTexture s;

    public CustomVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;


    }

    public CustomVideoPlayer(String ur, Context context) {
        super(context);
        this.setSurfaceTextureListener(this);
        this.url = ur;
        this.context = context;
    }

    @Override
    public void onSurfaceTextureAvailable(final SurfaceTexture surface, int arg1, int arg2) {

        this.s = surface;
        Log.d("url", this.url);
        startVideo(surface);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture arg0) {

        return true;
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture arg0, int arg1, int arg2) {
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture arg0) {
    }

    public void setVideo(String url) {
        this.url = url;
    }

    public void startVideo(SurfaceTexture t) {
        this.surface = new Surface(t);
        this.mp = new MediaPlayer();
        this.mp.setSurface(this.surface);
        try {
            Uri uri = Uri.parse(this.url);
            this.mp.setDataSource(url);
            this.mp.prepareAsync();

            this.mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {

                    mp.setLooping(true);
                    mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                        @Override
                        public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra) {
                            if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START)
                              Log.d("Zma URL","Buffering");
                            if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END)
                                Log.d("Zma URL","NOT Buffering");
                            return false;
                        }
                    });

                    mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener()  {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            Log.i( "Duration"  , String.valueOf(mp.getDuration()));
                        }
                    });
                    mp.start();
                    Thread timer = new Thread() {
                        public void run() {
                            try {
                                sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                changePlayState();
                            }
                        }
                    };
                    timer.start();

                }
            });

        } catch (IllegalArgumentException e1) {
            e1.printStackTrace();
        } catch (SecurityException e1) {
            e1.printStackTrace();
        } catch (IllegalStateException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        try {

        } catch (IllegalStateException e) {
            e.printStackTrace();
        }


    }

    public void changePlayState() {
        if (this.mp.isPlaying())
            this.mp.pause();
        else
            this.mp.start();
    }
}
