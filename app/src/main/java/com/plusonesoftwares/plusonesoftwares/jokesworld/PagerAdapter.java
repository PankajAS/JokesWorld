package com.plusonesoftwares.plusonesoftwares.jokesworld;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.plusonesoftwares.plusonesoftwares.jokesworld.TabFragments.CategoryFragment;
import com.plusonesoftwares.plusonesoftwares.jokesworld.TabFragments.FavouriteFragment;
import com.plusonesoftwares.plusonesoftwares.jokesworld.TabFragments.LatestFragment;
import com.plusonesoftwares.plusonesoftwares.jokesworld.TabFragments.PopularFragment;
import com.plusonesoftwares.plusonesoftwares.jokesworld.sqliteDatabase.FavouriteContent;

/**
 * Created by Plus 3 on 01-03-2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int tabCount;
    String[] tabBarTitles;

    public PagerAdapter(FragmentManager fm, int tabCount, String[] tabBarTitles) {
        super(fm);
        this.tabCount = tabCount;
        this.tabBarTitles = tabBarTitles;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                CategoryFragment category = new CategoryFragment();
                return  category;
            case 1:
                LatestFragment latest = new LatestFragment();
                return  latest;
            case 2:
                PopularFragment popular = new PopularFragment();
                return  popular;
            case 3:
                FavouriteFragment favourite = new FavouriteFragment();
                return  favourite;
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabBarTitles[position];
    }
}

