package com.plusonesoftwares.plusonesoftwares.jokesworld.TabFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.plusonesoftwares.plusonesoftwares.jokesworld.FavouriteListAdapter;
import com.plusonesoftwares.plusonesoftwares.jokesworld.R;
import com.plusonesoftwares.plusonesoftwares.jokesworld.sqliteDatabase.ContentRepo;

import java.util.ArrayList;
import java.util.HashMap;

public class PopularFragment extends Fragment {

    ListView popularList;
    FavouriteListAdapter adapter;
    ContentRepo popularListOperation;
    ArrayList<HashMap<String, String>> popularListObj;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_popular, container, false);

        // Inflate the layout for this fragment
        View popularView = inflater.inflate(R.layout.fragment_popular, container, false);

        popularList = (ListView)popularView.findViewById(R.id.Stored_data);

        popularListOperation = new ContentRepo(getContext());
        popularListObj = popularListOperation.getPopularContent();

        adapter = new FavouriteListAdapter(getContext(), R.layout.category_list_items, popularListObj, popularListObj);
        popularList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return popularView;
    }
}
