package Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diplom_examle.DatabaseHelper;
import com.example.diplom_examle.R;

import java.util.ArrayList;

import Adapters.Categories_Adapter;
import Adapters.Themes_Adapter;
import Data_classes.Categories_class;
import Interfaces.OnFragmentInteractionListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThemeFragment extends Fragment {


    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private OnFragmentInteractionListener frag_listener;
    DatabaseHelper db;
    SearchView searchView;
    ArrayList<Categories_class> wordcombimelist;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "parent_id";
    private static final String ARG_PARAM2 = "param2";

    //Передача ифнормации из базы данных соответсвующему адаптеру
    public void getData() {
        db = new DatabaseHelper(getContext());

        int ii, id,image, desc;
        SQLiteDatabase sd = db.getReadableDatabase();
        String selection = "category_parent_id = ?";
        String[] selection_Args = {Integer.toString(parent_id)};

        Cursor cursor = sd.query("qfr3h_jshopping_categories2", null, selection, selection_Args, null, null, null);
        ii = cursor.getColumnIndex("name_ru-RU");
        id = cursor.getColumnIndex("category_id");
        image = cursor.getColumnIndex("category_image");
        desc = cursor.getColumnIndex("short_description_ru-RU");
        wordcombimelist = new ArrayList<Categories_class>();

        while (cursor.moveToNext()) {
            wordcombimelist.add(new Categories_class(cursor.getInt(id), cursor.getString(ii), cursor.getString(image), cursor.getString(desc)));
        }


        adapter = new Themes_Adapter(wordcombimelist, frag_listener,getContext());
        recyclerView.setAdapter(adapter);
    }

    // TODO: Rename and change types of parameters
    private int parent_id;
    private String mParam2;

    // private OnFragmentInteractionListener mListener;


    // TODO: Rename and change types and number of parameters
    public static Categories newInstance(int param1, String param2) {
        Categories fragment = new Categories();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            parent_id = getArguments().getInt(ARG_PARAM1);
        } else {
            parent_id = 1;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_theme, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_themes);
        recyclerView.setHasFixedSize(true);
        db = new DatabaseHelper(getContext());
        searchView = (SearchView) v.findViewById(R.id.search_theme);
        searchView.setQueryHint("Search Here");
        searchView.setQueryRefinementEnabled(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        getData();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                newText = newText.toLowerCase();

                final ArrayList<Categories_class> filteredList = new ArrayList<Categories_class>();

                for (int i = 0; i < wordcombimelist.size(); i++) {

                    final String text = (wordcombimelist.get(i)).getName().toLowerCase();
                    if (text.contains(newText)) {

                        filteredList.add(wordcombimelist.get(i));
                    }
                }
                adapter = new Themes_Adapter(filteredList,frag_listener,getContext());
                recyclerView.setAdapter(adapter);


                return true;
            }
        });

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            Activity activity = (Activity) context;
            frag_listener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
}