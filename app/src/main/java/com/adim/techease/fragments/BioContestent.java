package com.adim.techease.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.adim.techease.R;
import com.adim.techease.utils.Alert_Utils;
import com.adim.techease.utils.Configuration;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BioContestent extends Fragment {

    private RecyclerView mRecyclerView;
    String getId;
    TabLayout tabLayout;
    Typeface typefaceReg,typefaceBold;
    ImageView contestentImage;
    TextView contestentName;
    String strContestentName;
    android.support.v7.app.AlertDialog alertDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bio_contestent, container, false);

       // customActionBar();
        final Bundle bundle = new Bundle();
        bundle.getString("id");
        getId=getArguments().getString("id");
        Log.d("zmagetId",getId);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        typefaceReg=Typeface.createFromAsset(getActivity().getAssets(),"raleway_reg.ttf");
        typefaceBold=Typeface.createFromAsset(getActivity().getAssets(),"raleway_bold.ttf");

        contestentImage=(ImageView)view.findViewById(R.id.ivContestent);
        contestentName=(TextView)view.findViewById(R.id.tvContestentName);
        contestentName.setTypeface(typefaceBold);
        apicall();

        // mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        tabLayout.addTab(tabLayout.newTab().setText("Bio"));
        tabLayout.addTab(tabLayout.newTab().setText("Photos"));
        tabLayout.addTab(tabLayout.newTab().setText("Videos"));
        tabLayout.addTab(tabLayout.newTab().setText("Social"));
        viewPager.setAdapter(new PagerAdapter(((FragmentActivity)getActivity()).getSupportFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    public void customActionBar() {
        android.support.v7.app.ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(true);
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar_layout, null);
        ImageButton imageView=(ImageButton)mCustomView.findViewById(R.id.action_bar_forward);
//        TextView title=(TextView)mCustomView.findViewById(R.id.tvCustomActionTitle);
//        title.setTypeface(typefaceBold);
       // title.setText("Bio Data");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Adim");
                String sAux = "\nHi, Download this beautiful aoo to Vote your favourite beauty Queen for Adim Pageant\n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=com.adim.techease \n\n";
                intent.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(intent, "choose one"));
            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

    }
    private void apicall() {
        if (alertDialog==null)
        {
            alertDialog= Alert_Utils.createProgressDialog(getActivity());
            alertDialog.show();
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Configuration.USER_URL+"App/getDetail/"+getId
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("zma  reg response", response);

                if (response.contains("true")) {
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        JSONObject jsonObj=jsonObject.getJSONObject("user");
                      contestentName.setText(jsonObj.getString("fullname"));
                      strContestentName=jsonObj.getString("fullname");
                       Glide.with(getActivity()).load("http://adadigbomma.com/panel/images/contestant/"+jsonObj.getString("profile")).into(contestentImage);
                        if (alertDialog!=null)
                            alertDialog.dismiss();
                    } catch (JSONException e) {
                        if (alertDialog!=null)
                            alertDialog.dismiss();
                        e.printStackTrace();
                    }


                } else {
                    if (alertDialog!=null)
                        alertDialog.dismiss();
                   // DialogUtils.sweetAlertDialog.dismiss();
                   // DialogUtils.showWarningAlertDialog(getActivity(), "Something went wrong");
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //DialogUtils.sweetAlertDialog.dismiss();
                // DialogUtils.showErrorTypeAlertDialog(getActivity(), "Server error");
                if (alertDialog!=null)
                    alertDialog.dismiss();
                Log.d("error" , String.valueOf(error.getCause()));

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(200000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(android.support.v4.app.FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }



        @Override
        public android.support.v4.app.Fragment getItem(int position) {

            switch (position) {
                case 0:
                    FragmentTab1bio frag=new FragmentTab1bio();
                    Bundle bundle=new Bundle();
                    bundle.putString("id",getId);
                    frag.setArguments(bundle);
                        return frag;
                case 1:
                    FragmentTab2Photos frag2=new FragmentTab2Photos();
                    Bundle bundle2=new Bundle();
                    bundle2.putString("id",getId);
                    bundle2.putString("name",strContestentName);
                    frag2.setArguments(bundle2);
                       return frag2;
                case 2:
                    FragmentTab3Videos frag3=new FragmentTab3Videos();
                    Bundle bundle3=new Bundle();
                    bundle3.putString("id",getId);
                    frag3.setArguments(bundle3);
                    return frag3;
                case 3:
                    FragmentTab4Social frag4=new FragmentTab4Social();
                    Bundle bundle4=new Bundle();
                    bundle4.putString("id",getId);
                    frag4.setArguments(bundle4);
                    return frag4;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }
}
