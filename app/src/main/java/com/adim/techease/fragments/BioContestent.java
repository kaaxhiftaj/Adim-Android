package com.adim.techease.fragments;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adim.techease.R;

public class BioContestent extends Fragment {

    private RecyclerView mRecyclerView;
    String getId;
    TabLayout tabLayout;
    Typeface typeface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bio_contestent, container, false);

        Bundle bundle = new Bundle();
        bundle.getString("id");
        getId=getArguments().getString("id");
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        typeface=Typeface.createFromAsset(getActivity().getAssets(),"myfont.ttf");

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
