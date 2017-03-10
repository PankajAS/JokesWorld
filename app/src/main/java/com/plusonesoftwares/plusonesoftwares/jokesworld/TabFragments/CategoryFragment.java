package com.plusonesoftwares.plusonesoftwares.jokesworld.TabFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.plusonesoftwares.plusonesoftwares.jokesworld.CategoryListAdapter;
import com.plusonesoftwares.plusonesoftwares.jokesworld.Category_DetailsView;
import com.plusonesoftwares.plusonesoftwares.jokesworld.R;
import com.plusonesoftwares.plusonesoftwares.jokesworld.Utils;
import com.plusonesoftwares.plusonesoftwares.jokesworld.sqliteDatabase.ContentRepo;

public class CategoryFragment extends Fragment {

    Spinner spinnerLang, category;
    List<String> string;
    ArrayAdapter<String> adapter1;
    View v;
    ProgressDialog dialog;
    ListView data;
    CategoryListAdapter adapter;
    String getAllCategoryUrl = "http://ssmasti.com/api/Category/GetAll";
    String getCategoryByLangUrl = "http://ssmasti.com/api/Category/GetCategoryByLanguageId?id=";

    Utils utils;
    //JSONArray jsonArray;
    JSONArray array;
    JSONObject jsonObj;
    ContentRepo categoryOperation;
    ArrayList<HashMap<String, String>> categoryListObj;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View categoryView = inflater.inflate(R.layout.fragment_category, container, false);

        spinnerLang  = (Spinner) categoryView.findViewById(R.id.spinnerLang);
        v = (View) categoryView.findViewById(R.id.include_data);
        data = (ListView)v.findViewById(R.id.Stored_data);
        string = new ArrayList<String>();
        utils = new Utils();
        categoryOperation = new ContentRepo(getContext());

        categoryListObj = categoryOperation.getCategoriesList("2");//Fetching data here for english category
        reloadListView(categoryListObj);

        spinnerLang.setSelection(2 ,false);
        spinnerLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                categoryListObj = categoryOperation.getCategoriesList(getLangId((String) spinnerLang.getSelectedItem()));
                reloadListView(categoryListObj);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getContext(),Category_DetailsView.class);
                intent.putExtra("Name",categoryListObj.get(i).get("category"));
                intent.putExtra("ID",categoryListObj.get(i).get("id"));

                startActivity(intent);
            }
        });
        return categoryView;
    }

    private String getLangId(String SelectedLan)
    {

       /*[
        {
            "ID": 1,
                "Language": "हिंदी"
        },
        {
            "ID": 2,
                "Language": "English"
        },
        {
            "ID": 3,
                "Language": "বাংলা"
        },
        {
            "ID": 5,
                "Language": "اُردُو"
        }
        ]*/

        if(SelectedLan.equals("हिंदी"))
           return "1";
        if(SelectedLan.equals("English"))
           return "2";
        if(SelectedLan.equals("বাংলা"))
           return "3";
        if(SelectedLan.equals("اُردُو"))
           return "5";

        return "0";
    }

    private void reloadListView(ArrayList<HashMap<String, String>> categoryList) {
        adapter = new CategoryListAdapter(getContext(), R.layout.category_list_items, categoryList, string);
        data.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}