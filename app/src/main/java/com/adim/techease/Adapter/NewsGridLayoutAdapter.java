package com.adim.techease.Adapter;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adim.techease.R;
import com.adim.techease.controllers.NewsModel;
import com.adim.techease.fragments.NewsDetailsWebviewFragment;
import com.bumptech.glide.Glide;
import com.thefinestartist.Base;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import retrofit2.http.Url;

import static com.thefinestartist.utils.content.ContextUtil.getContentResolver;
import static com.thefinestartist.utils.content.ContextUtil.startActivity;

/**
 * Created by Adam Noor on 10-Jan-18.
 */

public class NewsGridLayoutAdapter extends RecyclerView.Adapter<NewsGridLayoutAdapter.MyViewHolder> {

    Context context;
    List<NewsModel> newsModels;
    String id,newsLink;
    String img;


    public NewsGridLayoutAdapter(Context context, List<NewsModel> newsModelList) {
        this.newsModels=newsModelList;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_news_rv, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final  MyViewHolder holder, int position) {
        final NewsModel model=newsModels.get(position);
        holder.textView.setText(model.getNewsTitle());
        Glide.with(context).load("http://adadigbomma.com/panel/images/"+model.getNewsImage()).into(holder.imageView);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsLink=model.getNewsLink();
                Bundle bundle=new Bundle();
                bundle.putString("title", model.getNewsTitle());
                bundle.putString("link",newsLink);
                Fragment fragment=new NewsDetailsWebviewFragment();
                fragment.setArguments(bundle);
                ((AppCompatActivity)context).getFragmentManager().beginTransaction().replace(R.id.mainFrame , fragment).addToBackStack("abc").commit();
            }
        });
        holder.btnNewsShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // 1. Create the dynamic link as usual
                String packageName = context.getPackageName();
                String deepLink = newsLink ;
                Uri.Builder builder = new Uri.Builder()
                        .scheme("https")
                        .authority("fp2v3.app.goo.gl")
                        .path("/")
                        .appendQueryParameter("link", deepLink)
                        .appendQueryParameter("apn", packageName);

                final Uri uri = builder.build();


                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Adim");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, model.getNewsTitle() + "\n"+ String.valueOf(uri));
               context.startActivity(Intent.createChooser(sharingIntent, "Choose"));
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        Typeface typefaceBold,typeface;
        ImageView btnNewsShare;
        LinearLayout linearLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.llayoutCustomNews);
            imageView=(ImageView)itemView.findViewById(R.id.ivNewsImage);
            textView=(TextView)itemView.findViewById(R.id.tvNewsTitle);
            btnNewsShare=(ImageView) itemView.findViewById(R.id.sharebtnNews);
            typefaceBold=Typeface.createFromAsset(context.getAssets(), "raleway_semibold.ttf");
            typeface=Typeface.createFromAsset(context.getAssets(),"raleway_reg.ttf");
            textView.setTypeface(typefaceBold);


        }
    }
}
