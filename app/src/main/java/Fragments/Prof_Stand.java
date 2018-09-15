package Fragments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diplom_examle.DatabaseHelper;
import com.example.diplom_examle.R;

import java.util.ArrayList;

import Adapters.Themes_Adapter;
import Data_classes.Categories_class;
import Data_classes.FuncClass;
import Interfaces.OnFragmentInteractionListener;

/**
 * Created by Григорий Кисляков on 22.05.2018.
 */

public class Prof_Stand extends  Fragment {

   private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private TextView title;
    private TextView main_text;
    private  SQLiteDatabase sd;
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
        int ii, id,image, Sdesc,name_main,name_text;
        String selection = "category_parent_id = ?";
        String[] selection_Args = {Integer.toString(parent_id)};
        Cursor cursor = FuncClass.getCategories_cursor(sd,selection,selection_Args);
        Cursor cursor1= FuncClass.getCategories_cursor(sd,"category_id = ?",selection_Args);
        ii = cursor.getColumnIndex("name_ru-RU");
        id = cursor.getColumnIndex("category_id");
        name_main=cursor1.getColumnIndex("name_ru-RU");
        name_text=cursor1.getColumnIndex("description_ru-RU");
        image = cursor.getColumnIndex("category_image");
        Sdesc = cursor.getColumnIndex("short_description_ru-RU");
        wordcombimelist = new ArrayList<Categories_class>();
        cursor1.moveToNext();
        title.setText(cursor1.getString(name_main));
        main_text.setText(FuncClass.check1(cursor1.getString(name_text),getContext()));
        while (cursor.moveToNext()) {
            wordcombimelist.add(new Categories_class(cursor.getInt(id), cursor.getString(ii), cursor.getString(image), cursor.getString(Sdesc)));
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
        db = new DatabaseHelper(getContext());
        sd=db.getReadableDatabase();
        if (getArguments() != null) {
            parent_id = getArguments().getInt(ARG_PARAM1);
        } else {
            parent_id = 1;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.prof_stand_com, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_prof);
        recyclerView.setHasFixedSize(true);
        db = new DatabaseHelper(getContext());
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        title=(TextView)v.findViewById(R.id.title_prof) ;
        main_text=(TextView)v.findViewById(R.id.text_prof) ;

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