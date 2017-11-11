package com.adim.techease.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.adim.techease.R;
import com.adim.techease.fragments.AboutusFragment;
import com.adim.techease.fragments.AdimTvFragment;
import com.adim.techease.fragments.AuditionsFragment;
import com.adim.techease.fragments.GalleryFragment;
import com.adim.techease.fragments.HomeFragment;
import com.adim.techease.fragments.NewsFragment;
import com.adim.techease.fragments.OurTeamFragment;
import com.adim.techease.fragments.SponsorsFragment;
import com.adim.techease.fragments.VoteFragment;
import com.adim.techease.utils.Configuration;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentTransaction fragmentTransaction;
    Fragment fragment;
    FragmentManager fragmentManager;
    private static final int MENU_ITEMS = 6;
    private final ArrayList<View> mMenuItems = new ArrayList<>(MENU_ITEMS);
    Typeface typeface;
    String name,email;
    TextView Name,Email;
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

        Name=(TextView)headerLayout.findViewById(R.id.tvNavHeaderName);
        Email=(TextView)headerLayout.findViewById(R.id.tvNavHeaderEmail);

        sharedprefs = this.getSharedPreferences(Configuration.MY_PREF, Context.MODE_PRIVATE);
        editor = sharedprefs.edit();
        name=sharedprefs.getString("fullname","");
        email=sharedprefs.getString("email","");
        typeface=Typeface.createFromAsset(getAssets(),"myfont.ttf");
        if (name!=null)
        {
            getHeader();
        }
        fragment = new HomeFragment();
        getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).commit();
//        fragmentManager.popBackStack();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
//        if(getFragmentManager().getBackStackEntryCount() == 0) {
//            super.onBackPressed();
//        }
//        else {
//            getFragmentManager().popBackStack();
//        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();

        return super.onOptionsItemSelected(item);


    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_home) {
            // Handle the camera action
            fragment = new HomeFragment();
            getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).addToBackStack("tag").commit();
            item.setChecked(true);
            setTitle(item.getTitle());


        } else if (id == R.id.nav_gallery) {

            fragment = new GalleryFragment();
            getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).addToBackStack("tag").commit();
            item.setChecked(true);
            setTitle(item.getTitle());


        } else if (id == R.id.nav_sponsors) {

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
        else if(id==R.id.nav_News){
                fragment=new NewsFragment();
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
                    startActivity(new Intent(MainActivity.this,SplashScreen.class));
                    getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).addToBackStack("tag").commit();
                    item.setChecked(true);
                    setTitle(item.getTitle());
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

        Name.setText(name);
        Email.setText(email);
        fragment = new HomeFragment();
        getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).commit();

    }
}
