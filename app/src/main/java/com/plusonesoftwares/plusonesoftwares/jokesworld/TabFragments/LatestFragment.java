package com.plusonesoftwares.plusonesoftwares.jokesworld.TabFragments;

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

public class LatestFragment extends Fragment {

    ListView latestList;
    FavouriteListAdapter adapter;
    ContentRepo latestListOperation;
    ArrayList<HashMap<String, String>> latestListObj;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View latestView = inflater.inflate(R.layout.fragment_latest, container, false);

        latestList = (ListView)latestView.findViewById(R.id.Stored_data);

        latestListOperation = new ContentRepo(getContext());
        latestListObj = latestListOperation.getLatestContent();

        adapter = new FavouriteListAdapter(getContext(), R.layout.category_list_items, latestListObj, latestListObj);
        latestList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return latestView;
    }
}
