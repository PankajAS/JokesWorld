package com.plusonesoftwares.plusonesoftwares.jokesworld;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainTabViewActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab_view);
        utils = new Utils();

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager)findViewById(R.id.pager);

        String[] tabBarTitles = new String[]{
                getString(R.string.TabCategory),
                getString(R.string.TabLatest),
                getString(R.string.TabPopular),
                getString(R.string.TabFavourite)
        };
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.TabCategory)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.TabLatest)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.TabPopular)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.TabFavourite)));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);//to enable the scrolling of pager
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),tabBarTitles);
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
