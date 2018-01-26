package com.adim.techease.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.adim.techease.R;
import com.thefinestartist.finestwebview.FinestWebView;

public class WebviewActivity extends AppCompatActivity {

    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        new FinestWebView.Builder(this).show(url);
    }
}
