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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.diplom_examle.DatabaseHelper;
import com.example.diplom_examle.R;

import java.util.ArrayList;

import Adapters.Comment_Adapter;
import Adapters.Themes_Adapter;
import Data_classes.Categories_class;
import Data_classes.Comments;
import Data_classes.FuncClass;
import Interfaces.OnFragmentInteractionListener;

/**
 * Created by Григорий Кисляков on 22.05.2018.
 */

public class Fragment_comments extends Fragment {


    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private OnFragmentInteractionListener frag_listener;
    private  DatabaseHelper db;
   private  SQLiteDatabase sd;
   private  SearchView searchView;
   private  ArrayList<Comments> wordcombimelist;
   private  TextView text;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "categorie_id";
    private int parent_id;


    // private OnFragmentInteractionListener mListener;


    // TODO: Rename and change types and number of parameters
    public static Categories newInstance(int param1, String param2) {
        Categories fragment = new Categories();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
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
        View v = inflater.inflate(R.layout.comments_standarts, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_com);
        recyclerView.setHasFixedSize(true);
        db = new DatabaseHelper(getContext());
        text=(TextView) v.findViewById(R.id.title_comments);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        setData(text);
        adapter = new Comment_Adapter(wordcombimelist,frag_listener,getContext());
        recyclerView.setAdapter(adapter);
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
//Наполенение данными  страницы
    private void setData(TextView text) {
        int desc,title;
        String selection = "category_id = ?";
        String[] selection_Args = {Integer.toString(parent_id)};

        Cursor cursor =FuncClass.getCategories_cursor(sd,selection,selection_Args);
        desc = cursor.getColumnIndex("description_ru-RU");
        title = cursor.getColumnIndex("name_ru-RU");
          cursor.moveToNext();

        text.setText(cursor.getString(title));

        wordcombimelist= FuncClass.getComments(cursor.getString(desc));
  db.close();


    }
}