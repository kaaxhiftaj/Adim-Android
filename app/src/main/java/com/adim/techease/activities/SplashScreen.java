package com.adim.techease.activities;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adim.techease.R;
import com.adim.techease.fragments.NewsDetailsWebviewFragment;
import com.adim.techease.utils.Alert_Utils;
import com.adim.techease.utils.CheckInternetConnection;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

import org.json.JSONObject;

import java.security.MessageDigest;



public class SplashScreen extends AppCompatActivity {

    ImageView ivSpalsh,ivWifi;
    TextView textView;
    Typeface typefaceBold;
    Button btnTryAgain;
    android.support.v7.app.AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        getHasKey();
        ivSpalsh=(ImageView)findViewById(R.id.ivSplash);
        ivWifi=(ImageView)findViewById(R.id.ivWifi);
        textView=(TextView)findViewById(R.id.tvNoInternet);
        typefaceBold=Typeface.createFromAsset(this.getAssets(),"raleway_bold.ttf");
        textView.setTypeface(typefaceBold);
        btnTryAgain=(Button)findViewById(R.id.btnTryAgain);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alertDialog==null)
                {
                    alertDialog= Alert_Utils.createProgressDialog(SplashScreen.this);
                    alertDialog.show();
                }
                Check();
            }
        });
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                        Check();

                }
            }
        };
        timer.start();
    }


    public  void Check()
    {
        if (CheckInternetConnection.isInternetAvailable(SplashScreen.this))
        {
            if (alertDialog!=null)
            alertDialog.dismiss();
            startActivity(new Intent(SplashScreen.this, AuthOptionScreen.class));
            finish();
        }
        else
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (alertDialog!=null)
                        alertDialog.dismiss();
                    ivSpalsh.setImageResource(R.drawable.main_app_bg);
                    ivWifi.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                    btnTryAgain.setVisibility(View.VISIBLE);

                }
            });

        }

    }




    void getHasKey()
    {
        //Get Has Key
        try
        {
            PackageInfo info = getPackageManager().getPackageInfo("techease.com.postcard", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));

            }
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
