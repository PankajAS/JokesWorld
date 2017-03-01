package com.plusonesoftwares.plusonesoftwares.jokesworld;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ashoksharma on 27/02/17.
 */

public class DetailsViewActivity extends AppCompatActivity {

    private static final int NUM_PAGES = 5;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    TextView selectedItem;
    Intent intent;
    String jsonArray;
    JSONArray array;
    JSONObject jobject;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPager = (ViewPager) findViewById(R.id.pager);
        selectedItem = (TextView)findViewById(R.id.txtcontent);
        intent = getIntent();

        jsonArray=intent.getStringExtra("Content");
        try {
            array = new JSONArray(jsonArray);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPagerAdapter = new ScreenSlidePagerAdapter(this);
        mPager.setAdapter(mPagerAdapter);
        String index = intent.getStringExtra("SelectedIndex");
        mPager.setCurrentItem(Integer.parseInt(index));//selecting the selected tab as CHAT TAB


    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
    private class ScreenSlidePagerAdapter extends PagerAdapter {
        Context mContext;
        LayoutInflater mLayoutInflater;

        public ScreenSlidePagerAdapter(Context cnt) {
            mContext = cnt;
            mLayoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v =mLayoutInflater.inflate(R.layout.fragment_screen_slide_page,container,false);
            TextView txt = (TextView)v.findViewById(R.id.txtcontent);
            try {
                jobject = array.getJSONObject(position);
                txt.setText(jobject.getString("Content"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            container.addView(v);

            return v;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view ==((ScrollView)object);
        }

        @Override
        public int getCount() {
            return array.length();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ScrollView)object);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

