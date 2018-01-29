package com.adim.techease.activities;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.adim.techease.R;
import com.adim.techease.fragments.AboutusFragment;
import com.adim.techease.fragments.AdimTvFragment;
import com.adim.techease.fragments.AuditionsFragment;
import com.adim.techease.fragments.GalleryFragment;
import com.adim.techease.fragments.HomeFragment;
import com.adim.techease.fragments.NewsDetailsWebviewFragment;
import com.adim.techease.fragments.NewsFrag;
import com.adim.techease.fragments.OurTeamFragment;
import com.adim.techease.fragments.SponsorsFragment;
import com.adim.techease.fragments.VoteFragment;
import com.adim.techease.utils.Configuration;
import com.adim.techease.utils.CustomTypefaceSpan;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment;
    Typeface typeface;
    String name,email;
    TextView Email;
    SharedPreferences sharedprefs;
    SharedPreferences.Editor editor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
       // getHasKey();


        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();

                            fragment = new NewsDetailsWebviewFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("link", String.valueOf(deepLink));
                            fragment.setArguments(bundle);
                            getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).commit();

                        }


                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Tag", "getDynamicLink:onFailure", e);
                    }
                });

        Email=(TextView)headerLayout.findViewById(R.id.tvNavHeaderEmail);

        sharedprefs = this.getSharedPreferences(Configuration.MY_PREF, Context.MODE_PRIVATE);
        editor = sharedprefs.edit();
        name=sharedprefs.getString("fullname","");
        email=sharedprefs.getString("email","");

        typeface=Typeface.createFromAsset(getAssets(),"raleway_bold.ttf");
        if (name!=null)
        {
            getHeader();
        }
        fragment = new NewsFrag();
        getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).commit();
//        fragmentManager.popBackStack();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            applyFontToMenuItem(menuItem);
        }
        navigationView.setNavigationItemSelectedListener(this);

    }



    private void applyFontToMenuItem(MenuItem menuItem) {
        int id=menuItem.getItemId();
        Typeface font = Typeface.createFromAsset(getAssets(), "raleway_semibold.ttf");
        SpannableString mNewTitle = new SpannableString(menuItem.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        menuItem.setTitle(mNewTitle);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);


        return  true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_news) {
            // Handle the camera action
            fragment = new NewsFrag();
            getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).addToBackStack("tag").commit();
            item.setChecked(true);
            setTitle(item.getTitle());


        } else if (id == R.id.nav_gallery) {

            fragment = new GalleryFragment();
            getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).commit();
            item.setChecked(true);
            setTitle(item.getTitle());


        }else if (id==R.id.nav_ShareApp)
        {
            Intent intent;

            intent = new Intent(Intent.ACTION_SEND);

            intent.putExtra(Intent.EXTRA_TITLE,"Adim");

            intent.putExtra(Intent.EXTRA_TEXT,"Please download ADIM beauty and brains pageant app for free.\nhttps://play.google.com/store/apps/details?id=com.adim.techease&hl=en");

            intent.setType("text/plain");

            startActivity(Intent.createChooser(intent, "choose one"));
        }
        else if (id == R.id.nav_sponsors) {

            fragment = new SponsorsFragment();
            getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).addToBackStack("tag").commit();
            item.setChecked(true);
            setTitle(item.getTitle());


        } else if (id == R.id.nav_auditions) {

            fragment = new AuditionsFragment();
            getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).addToBackStack("tag").commit();
            item.setChecked(true);
            setTitle(item.getTitle());
        }
        else if (id == R.id.nav_vote) {
            fragment = new VoteFragment();
            getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).addToBackStack("tag").commit();
            item.setChecked(true);
            setTitle(item.getTitle());

        }
        else if(id==R.id.nav_contestent){
                fragment=new HomeFragment();
            getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).addToBackStack("tag").commit();
            item.setChecked(true);
            setTitle(item.getTitle());

            }
            else if(id==R.id.nav_team)
            {
                fragment=new OurTeamFragment();
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).addToBackStack("tag").commit();
                item.setChecked(true);
                setTitle(item.getTitle());
            }
            else
                if(id==R.id.nav_Tv){
                    fragment=new AdimTvFragment();
                    getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).addToBackStack("tag").commit();
                    item.setChecked(true);
                    setTitle(item.getTitle());
                }
                else
                if(id==R.id.action_Logout){
                    editor.clear().commit();
                    item.setChecked(true);
                    setTitle(item.getTitle());
                    LoginManager.getInstance().logOut();
                    startActivity(new Intent(MainActivity.this,SplashScreen.class));
                    finish();
                }else  if (id == R.id.nav_aboutus) {
                    fragment = new AboutusFragment();
                    getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).addToBackStack("tag").commit();
                    item.setChecked(true);
                    setTitle(item.getTitle());
                }
        getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).addToBackStack("tag").commit();
        item.setChecked(true);
        setTitle(item.getTitle());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void getHeader()
    {
        Email.setText(email);
        fragment = new HomeFragment();
        getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).commit();

    }

}
