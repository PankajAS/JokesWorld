package com.plusonesoftwares.plusonesoftwares.jokesworld;

import com.plusonesoftwares.plusonesoftwares.jokesworld.sqliteDatabase.ContentRepo;
import com.plusonesoftwares.plusonesoftwares.jokesworld.sqliteDatabase.FavouriteContent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashoksharma on 27/02/17.
 */

public class DetailsViewActivity extends AppCompatActivity {

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    TextView selectedItem;
    ImageButton ImButtonShare;
    ImageButton ImButtonCopy;
    ImageButton ImButtonShareOnWhatsapp;
    ImageButton ImButtonFavourite;
    Intent intent;
    String jsonArray;
    JSONArray array;
    JSONObject jobject;
    String pageTitle;
    ContentRepo FavContentOperation;
    String DataIndex;
    String contentID;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FavContentOperation = new ContentRepo(getApplicationContext());

        mPager = (ViewPager) findViewById(R.id.pager);
        selectedItem = (TextView)findViewById(R.id.txtcontent);
        ImButtonShare = (ImageButton) findViewById(R.id.ImButtonShare);
        ImButtonCopy = (ImageButton) findViewById(R.id.ImButtonCopy);
        ImButtonShareOnWhatsapp = (ImageButton) findViewById(R.id.ImButtonShareOnWhatsapp);
        ImButtonFavourite = (ImageButton) findViewById(R.id.imButtonFavourite);

        intent = getIntent();
        pageTitle = intent.getStringExtra("Category");

        jsonArray = intent.getStringExtra("Content");
        try {
            array = new JSONArray(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPagerAdapter = new ScreenSlidePagerAdapter(this);
        mPager.setAdapter(mPagerAdapter);
        DataIndex = intent.getStringExtra("SelectedIndex");
        mPager.setCurrentItem(Integer.parseInt(DataIndex));//selecting the selected tab as CHAT TAB
        setTitle(pageTitle);

        setFavouriteButtonState();

        ImButtonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, getSelectedContent(mPager.getCurrentItem()));
                    shareIntent.setType("text/plain");
                    startActivity(Intent.createChooser(shareIntent, "Choose sharing method"));
                } catch (JSONException e) {
                     e.printStackTrace();
                }
            }
        });
        ImButtonCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    setClipboard(getSelectedContent(mPager.getCurrentItem()));
                    Toast toast = Toast.makeText(getApplicationContext(), "Copied to clipboard" , Toast.LENGTH_SHORT);
                    toast.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        ImButtonShareOnWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                try{
                    whatsappIntent.putExtra(Intent.EXTRA_TEXT, getSelectedContent(mPager.getCurrentItem()));
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Whatsapp have not been installed." , Toast.LENGTH_SHORT);
                    toast.show();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        ImButtonFavourite.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
               // {"ispopular":"true","creationdate":"2017-03-04T08:34:31.247","content":"हम दिलफेक आशिक़ है, हर काम में कमाल कर दे,\r\nजो वादा करे वो पूरा हर हाल में कर दे,\r\nक्या जरुरत है जानू को लिपस्टिक लगाने की,\r\nहम चूम-चूम के ही होंठ उसके लाल कर दे !!","categoryid":"1","id":"60"}
                if(FavContentOperation.isAlreadyFavourite(Integer.parseInt(contentID)))
                {
                    FavContentOperation.un_Favourite(Integer.parseInt(contentID));
                    ImButtonFavourite.setBackground(getResources().getDrawable(R.drawable.list_radious));
                    Toast toast = Toast.makeText(getApplicationContext(), "Removed from your favourite list", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    List<FavouriteContent> favouriteContentList = new ArrayList<>();
                    FavouriteContent singleContentObj;
                    try {
                        singleContentObj = new FavouriteContent();
                        singleContentObj.content_ID = array.getJSONObject(mPager.getCurrentItem()).getString("id");
                        singleContentObj.category_ID = array.getJSONObject(mPager.getCurrentItem()).getString("categoryid");
                        singleContentObj.Content = array.getJSONObject(mPager.getCurrentItem()).getString("content");
                        singleContentObj.CreatedDate = array.getJSONObject(mPager.getCurrentItem()).getString("creationdate");
                        singleContentObj.IsPopular = array.getJSONObject(mPager.getCurrentItem()).getString("ispopular");
                        favouriteContentList.add(singleContentObj);
                        FavContentOperation.insert_FavouriteContent(favouriteContentList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ImButtonFavourite.setBackgroundColor(Color.GREEN);
                    Toast toast = Toast.makeText(getApplicationContext(), "Added to your favourite list", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    private void setFavouriteButtonState()
    {
        try {
            jobject = array.getJSONObject(Integer.parseInt(DataIndex));
            contentID = (String) jobject.get("id");

            if(FavContentOperation.isAlreadyFavourite(Integer.parseInt(contentID)))
            {
                ImButtonFavourite.setBackgroundColor(Color.GREEN);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getSelectedContent(int index) throws JSONException {
        jobject = array.getJSONObject(index);
        return jobject.getString("content");
    }

    private void setClipboard(String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
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
            View viewContent = mLayoutInflater.inflate(R.layout.fragment_screen_slide_page,container,false);
            TextView txtFullContent = (TextView)viewContent.findViewById(R.id.txtcontent);
            try {

                txtFullContent.setText(getSelectedContent(position));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            container.addView(viewContent);

            return viewContent;
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

