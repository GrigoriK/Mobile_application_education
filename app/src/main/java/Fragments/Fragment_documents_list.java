package Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.diplom_examle.DatabaseHelper;
import com.example.diplom_examle.R;

import java.util.ArrayList;

import Adapters.Categories_Adapter;
import Adapters.Documents_Adapter;
import Adapters.Themes_Adapter;
import Data_classes.Categories_class;
import Data_classes.Document;
import Data_classes.FuncClass;
import Interfaces.OnFragmentInteractionListener;

import static org.jsoup.nodes.Document.OutputSettings.Syntax.html;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_documents_list extends Fragment {


    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private OnFragmentInteractionListener frag_listener;
    private DatabaseHelper db;
    private  SQLiteDatabase sd;
    private SearchView searchView;
    private ArrayList<Document> doc;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "parent_id";
    private static final String ARG_PARAM2 = "price";
    private static final String ARG_PARAM3 = "deliver_time";
    private int parent_id;
    private double price;
    private String deliver;

   //Передача необходимой информации из базы данных соответсвующему адаптеру
    public void getData() {
        int ii, id, desc;
        doc = new ArrayList<>();
        String selection = "category_id = ?";
        String[] selection_Args = {Integer.toString(parent_id)};
        Cursor cursor1 = sd.query("qfr3h_jshopping_products_to_categories", null, selection, selection_Args, null, null, null);
        id = cursor1.getColumnIndex("product_id");
        String st="";

      while (cursor1.moveToNext()){
           doc.add(getProducts(cursor1.getInt(id), sd));

        }

        adapter = new Documents_Adapter(doc, frag_listener,getContext());
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getContext());
       sd = db.getReadableDatabase();
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
       /* Методы соответствующие интерфейсу SearchView.OnQueryTextListener()
        необходимы для работы поисковой строки
        */
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                newText = newText.toLowerCase();

                final ArrayList<Document> filteredList = new ArrayList<Document>();

                for (int i = 0; i < doc.size(); i++) {

                    final String text = (doc.get(i)).getName().toLowerCase();
                    if (text.contains(newText)) {

                        filteredList.add(doc.get(i));
                    }
                }
                adapter = new Documents_Adapter(filteredList, frag_listener,getContext());
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


    //Метод для получения соответсвующего продукта по id
    public Document getProducts(int id, SQLiteDatabase sd) {
        Document doc;
        String selection = "product_id = ?";
        String[] selection_Args = {Integer.toString(id)};
        Cursor cursor1= FuncClass.get_cursor(sd,selection,selection_Args,"qfr3h_jshopping_products2");
        int product_ean = cursor1.getColumnIndex("product_ean");
        int name_ru = cursor1.getColumnIndex("name_ru-RU");
        int price = cursor1.getColumnIndex("product_price");
        //Дополнительные параметры,которые берутся из другой таблицы.
        int author_id = cursor1.getColumnIndex("product_manufacturer_id");
        int logo = cursor1.getColumnIndex("product_thumb_image");
        int deliver_id = cursor1.getColumnIndex("delivery_times_id");
        int description_id = cursor1.getColumnIndex("description_ru-RU");
        int cont_type = cursor1.getColumnIndex("extra_field_2");

        try {
            cursor1.moveToNext();
        } catch (CursorIndexOutOfBoundsException e) {

        }
    Log.i("My_Log",cursor1.getString(name_ru));
        author_id = cursor1.getInt(author_id);
        String auth="";
        String del="";
        if (author_id > 0) {
            selection = "manufacturer_id = ?";
            selection_Args = new String[]{Integer.toString(author_id)};
            Cursor cursorA= FuncClass.get_cursor(sd,selection,selection_Args,"qfr3h_jshopping_manufacturers");
            int auth_id1 = cursorA.getColumnIndex("name_ru-RU");
            cursorA.moveToNext();
             auth = cursorA.getString(auth_id1);

        }
            deliver_id = cursor1.getInt(deliver_id);
            if (deliver_id > 0) {
                selection = "id = ?";
                selection_Args = new String[]{Integer.toString(deliver_id)};
                Cursor cursorD= FuncClass.get_cursor(sd,selection,selection_Args,"qfr3h_jshopping_delivery_times");
                int deliver_id1 = cursorD.getColumnIndex("name_ru-RU");
                cursorD.moveToNext();
                 del = cursorD.getString(deliver_id1);

            }

        doc=new Document(id,cursor1.getString(name_ru),FuncClass.check1(cursor1.getString(description_id),getContext()),cursor1.getString(product_ean)
                ,cursor1.getDouble(price),
                auth,cursor1.getString(logo),del,deliver_id,cursor1.getString(cont_type));
      return  doc;

        }


    }

