package com.plusonesoftwares.plusonesoftwares.jokesworld.TabFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.plusonesoftwares.plusonesoftwares.jokesworld.CategoryDetailsAdapter;
import com.plusonesoftwares.plusonesoftwares.jokesworld.CategoryListAdapter;
import com.plusonesoftwares.plusonesoftwares.jokesworld.FavouriteListAdapter;
import com.plusonesoftwares.plusonesoftwares.jokesworld.R;
import com.plusonesoftwares.plusonesoftwares.jokesworld.sqliteDatabase.ContentRepo;

import java.util.ArrayList;
import java.util.HashMap;

public class FavouriteFragment extends Fragment {

    ListView favouriteList;
    FavouriteListAdapter adapter;
    ContentRepo favListOperation;
    ArrayList<HashMap<String, String>> favListObj;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View favouriteView  = inflater.inflate(R.layout.fragment_favourite, container, false);
        favouriteList = (ListView)favouriteView.findViewById(R.id.Stored_data);

        favListOperation = new ContentRepo(getContext());
        favListObj = favListOperation.getFavouriteContent();

        adapter = new FavouriteListAdapter(getContext(), R.layout.category_list_items, favListObj, favListObj);
        favouriteList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return favouriteView;
    }
}
