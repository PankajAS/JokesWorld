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

        categoryListObj = categoryOperation.getCategoriesList(1);
        reloadListView(categoryListObj);

        /*try {
            if(utils.haveNetworkConnection(getContext())) {
                new getCategoryList().execute(new URL(getAllCategoryUrl));//start async task to get all categories
            }
            else
            {
                utils.showNetworkConnectionMsg(getActivity());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }*/

        spinnerLang.setSelection(1 ,false);
        spinnerLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
               // if(utils.haveNetworkConnection(getContext())) {
                    if(!spinnerLang.getSelectedItem().equals("All Language Categories")) {
                        int langId = spinnerLang.getSelectedItem().equals("Hindi") ? 1 : 2;
                        categoryListObj = categoryOperation.getCategoriesList(langId);
                        reloadListView(categoryListObj);
                        //new getCategoryList().execute(new URL(getCategoryByLangUrl + langId));//sending request to fetch category data here by language
                    }
                    else if(spinnerLang.getSelectedItem().equals("All Language Categories"))
                    {
                        categoryListObj = categoryOperation.getCategoriesList(0);
                        reloadListView(categoryListObj);
                        //new getCategoryList().execute(new URL(getAllCategoryUrl));//start async task to get all categories
                    }
//                }
//                else {
//                    utils.showNetworkConnectionMsg(getActivity());
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //JSONObject jobject = array.getJSONObject(i);

                Intent intent = new Intent(getContext(),Category_DetailsView.class);

//              intent.putExtra("Name",jobject.getString("Category"));
//              intent.putExtra("ID",jobject.getString("ID"));

                intent.putExtra("Name",categoryListObj.get(i).get("category"));
                intent.putExtra("ID",categoryListObj.get(i).get("id"));

                startActivity(intent);
            }
        });
        return categoryView;
    }

    private void reloadListView(ArrayList<HashMap<String, String>> categoryList) {
        adapter = new CategoryListAdapter(getContext(), R.layout.category_list_items, categoryList, string);
        data.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /*private HttpURLConnection urlConnection;
    StringBuilder strbuilderObj = null;

    public class getCategoryList extends AsyncTask<URL, Context, JSONArray> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(getContext(), "", "Please wait loading your data...", true);
            super.onPreExecute();
        }

        @Override
        protected JSONArray doInBackground(URL... urls) {

            URL url = urls[0];
            String line;
            strbuilderObj = new StringBuilder();
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                while ((line = in.readLine()) != null) {
                    strbuilderObj.append(line);
                }
                array = new JSONArray(strbuilderObj.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return array;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);

            try {
                if (jsonArray != null && jsonArray.length() > 0) {
                    string.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        string.add(jsonArray.getJSONObject(i).getString("ContentCount"));
                    }
                } else {
                    Toast.makeText(getContext(), "No data found", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter = new CategoryListAdapter(getContext(), R.layout.category_list_items, categoryOperation.getCategoriesList(0), string);
            data.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            dialog.dismiss();//closing the loading dialog here
        }
    }*/
}