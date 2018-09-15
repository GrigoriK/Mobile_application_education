package Fragments;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;


import Adapters.Categories_Adapter;
import com.example.diplom_examle.DatabaseHelper;
import com.example.diplom_examle.R;

import java.util.ArrayList;

import Data_classes.Categories_class;
import Data_classes.FuncClass;
import Interfaces.OnFragmentInteractionListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link //Categories.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Categories#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Categories extends Fragment {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;

    private SQLiteDatabase sd;
    private OnFragmentInteractionListener frag_listener;
    private DatabaseHelper db;
    private ArrayList<Categories_class> wordcombimelist;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Метод передачи данных из базы данных соответсвующему адаптеру RecyclerView
    public void getData() {
       int name, id;
        //Условие выборки данных из БД
        String selection = "category_parent_id = ? AND category_publish = ?"; //NAME = ? AND DESCRIPTION = ?
        String[] selection_Args = {"0","1"};
        Cursor cursor = FuncClass.getCategories_cursor(sd,selection,selection_Args);
        name = cursor.getColumnIndex("name_ru-RU");
        id = cursor.getColumnIndex("category_id");
        wordcombimelist = new ArrayList<Categories_class>();

        while (cursor.moveToNext()) {
            //Categories_class - объект класса Categories_class хранит в себе данные для отображения определённой категории
            wordcombimelist.add(new Categories_class(cursor.getInt(id), cursor.getString(name)));
        }
      //Происходит передача данных для отображения
        adapter = new Categories_Adapter(wordcombimelist,frag_listener);
        recyclerView.setAdapter(adapter);
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // private OnFragmentInteractionListener mListener;


    // TODO: Rename and change types and number of parameters
    public static Categories newInstance(String param1, String param2) {
        Categories fragment = new Categories();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getContext());
        sd=db.getReadableDatabase();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_categories, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        db = new DatabaseHelper(getContext());


        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getData();


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


