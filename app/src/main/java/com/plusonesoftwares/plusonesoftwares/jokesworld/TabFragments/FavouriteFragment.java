package com.plusonesoftwares.plusonesoftwares.jokesworld.TabFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.plusonesoftwares.plusonesoftwares.jokesworld.DetailsViewActivity;
import com.plusonesoftwares.plusonesoftwares.jokesworld.FavouriteListAdapter;
import com.plusonesoftwares.plusonesoftwares.jokesworld.R;
import com.plusonesoftwares.plusonesoftwares.jokesworld.Utils;
import com.plusonesoftwares.plusonesoftwares.jokesworld.sqliteDatabase.ContentRepo;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

public class FavouriteFragment extends Fragment {

    ListView favouriteDataList;
    FavouriteListAdapter adapter;
    ContentRepo favListOperation;
    ArrayList<HashMap<String, String>> favListObj;
    JSONArray array;
    Utils utils;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View favouriteView  = inflater.inflate(R.layout.fragment_favourite, container, false);
        favouriteDataList = (ListView)favouriteView.findViewById(R.id.Stored_data);

        favListOperation = new ContentRepo(getContext());
        favListObj = favListOperation.getFavouriteContent();

        adapter = new FavouriteListAdapter(getContext(), R.layout.category_list_items, favListObj, favListObj);
        favouriteDataList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        favouriteDataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                utils = new Utils();
                array = utils.convertHashMapArrayListToJsonArray(favListObj);
                Intent intent = new Intent(getContext(), DetailsViewActivity.class);
                intent.putExtra("Content", array.toString());
                intent.putExtra("Category", getString(R.string.app_name));
                intent.putExtra("SelectedIndex", String.valueOf(i));
                startActivity(intent);
            }
        });

        return favouriteView;
    }
}
