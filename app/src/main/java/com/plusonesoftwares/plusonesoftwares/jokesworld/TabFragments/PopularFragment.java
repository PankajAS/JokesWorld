package com.plusonesoftwares.plusonesoftwares.jokesworld.TabFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.plusonesoftwares.plusonesoftwares.jokesworld.Category_DetailsView;
import com.plusonesoftwares.plusonesoftwares.jokesworld.DetailsViewActivity;
import com.plusonesoftwares.plusonesoftwares.jokesworld.FavouriteListAdapter;
import com.plusonesoftwares.plusonesoftwares.jokesworld.R;
import com.plusonesoftwares.plusonesoftwares.jokesworld.Utils;
import com.plusonesoftwares.plusonesoftwares.jokesworld.sqliteDatabase.ContentRepo;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

public class PopularFragment extends Fragment {

    ListView popularDataList;
    FavouriteListAdapter adapter;
    ContentRepo popularListOperation;
    ArrayList<HashMap<String, String>> popularListObj;
    JSONArray array;
    Utils utils;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_popular, container, false);

        // Inflate the layout for this fragment
        View popularView = inflater.inflate(R.layout.fragment_popular, container, false);

        popularDataList = (ListView)popularView.findViewById(R.id.Stored_data);
        utils = new Utils();
        popularListOperation = new ContentRepo(getContext());
        popularListObj = popularListOperation.getPopularContent();

        adapter = new FavouriteListAdapter(getContext(), R.layout.category_list_items, popularListObj, popularListObj);
        popularDataList.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        popularDataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                array = utils.convertHashMapArrayListToJsonArray(popularListObj);
                Intent intent = new Intent(getContext(), DetailsViewActivity.class);
                intent.putExtra("Content", array.toString());
                intent.putExtra("Category", getString(R.string.app_name));
                intent.putExtra("SelectedIndex", String.valueOf(i));
                startActivity(intent);
            }
        });
        return popularView;
    }
}
