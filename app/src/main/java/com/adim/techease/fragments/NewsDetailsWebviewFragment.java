package com.adim.techease.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.adim.techease.R;
import com.adim.techease.utils.CheckInternetConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.thefinestartist.Base;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import retrofit2.http.Url;

import static com.thefinestartist.utils.content.ContextUtil.getContentResolver;


public class NewsDetailsWebviewFragment extends Fragment {
    String url,title,strDescription,strImageUrl;
    WebView webView;
    Button btnSharenews;
    String data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_news_details_webview, container, false);

        if (CheckInternetConnection.isInternetAvailable(getActivity()))
        {
            url=getArguments().getString("link");
            title = getArguments().getString("title");
            btnSharenews=(Button)view.findViewById(R.id.btnShareNews);
            webView=(WebView)view.findViewById(R.id.wv);
            webView.getSettings().setPluginState(WebSettings.PluginState.ON);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            webView.setScrollbarFadingEnabled(false);
            webView.setInitialScale(120);
            webView.setWebViewClient(new WebViewClient(){

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url){
                    view.loadUrl(url);
                    return true;
                }
            });
            webView.loadUrl(url);

            btnSharenews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // 1. Create the dynamic link as usual
                    String packageName = getActivity().getPackageName();
                    String deepLink = url ;
                    Uri.Builder builder = new Uri.Builder()
                            .scheme("https")
                            .authority("fp2v3.app.goo.gl")
                            .path("/")
                            .appendQueryParameter("link", deepLink)
                            .appendQueryParameter("apn", packageName);

                    final Uri uri = builder.build();
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,  String.valueOf(uri));
                    startActivity(Intent.createChooser(sharingIntent, "Choose"));

                }
            });
        }
        else
        {
            Toast.makeText(getActivity(), "No internet Connection", Toast.LENGTH_SHORT).show();
        }


        return view;
    }

}
