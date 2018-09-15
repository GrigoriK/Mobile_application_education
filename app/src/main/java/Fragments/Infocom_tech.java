package Fragments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
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

import Adapters.Documents_Adapter;
import Adapters.Themes_Adapter;
import Data_classes.Categories_class;
import Data_classes.Document;
import Data_classes.FuncClass;
import Interfaces.OnFragmentInteractionListener;

/**
 * Created by Григорий Кисляков on 22.05.2018.
 */

public class Infocom_tech extends Fragment {

    private static final String ARG_PARAM1 = "parent_id";
    private int categorie_id;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
     private OnFragmentInteractionListener frag_listener;
    private TextView text_main;
    private TextView title_infocom;
    private ImageView imageVsh;
    private ImageView imageMt;
   private ArrayList<Categories_class> wordcombimelist;
    private  DatabaseHelper db;
    private  SQLiteDatabase sd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getContext());
       sd = db.getReadableDatabase();
        if (getArguments() != null) {
            categorie_id = getArguments().getInt(ARG_PARAM1);
        } else {
            categorie_id = 86;

        }
    }
    //Передача данных из БД соответсвующему адаптеру
    public void getData() {
        int ii, id,image, desc;
        String selection = "category_parent_id = ?";
        String[] selection_Args = {Integer.toString(categorie_id)};

        Cursor cursor = FuncClass.getCategories_cursor(sd,selection,selection_Args);
         Cursor cursor1 =  FuncClass.getCategories_cursor(sd,selection,selection_Args);
         ii = cursor.getColumnIndex("name_ru-RU");
        id = cursor.getColumnIndex("category_id");
        image = cursor.getColumnIndex("category_image");
        desc = cursor.getColumnIndex("short_description_ru-RU");
        wordcombimelist = new ArrayList<Categories_class>();
        cursor1.moveToNext();
        title_infocom.setText(cursor1.getString(ii));
        while (cursor.moveToNext()) {
            wordcombimelist.add(new Categories_class(cursor.getInt(id), cursor.getString(ii), cursor.getString(image), cursor.getString(desc)));
        }

        adapter = new Themes_Adapter(wordcombimelist, frag_listener,getContext());
        recyclerView.setAdapter(adapter);
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.infocom, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_themes);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
       text_main=(TextView)  v.findViewById(R.id.main_text);
        title_infocom=(TextView)  v.findViewById(R.id.title_infocom);
       imageVsh=(ImageView)v.findViewById(R.id.imageVh);
       imageMt=(ImageView) v.findViewById(R.id.imageMt);
        SetData_Infocom(text_main,imageVsh,imageMt);
        getData();

        return v;
    }
//Заполение контентом заголовок и контеёнеры картинок
    public void SetData_Infocom(TextView text1, ImageView image1,ImageView image2){
        int desc;
        String imageV1,imageV2;
        String selection = "category_id = ?";
        String[] selection_Args = {Integer.toString(categorie_id)};

        Cursor cursor = FuncClass.getCategories_cursor(sd,selection,selection_Args);
        desc = cursor.getColumnIndex("description_ru-RU");
        cursor.moveToNext();
        ArrayList<String> image_arr=FuncClass.getmage(cursor.getString(desc));

        imageV1 = image_arr.get(0);
        imageV2 =image_arr.get(1);
        Log.i("My_Tag",imageV1);
        Log.i("My_Tag",imageV2);
        FuncClass.setImage(getContext(), getContext().getResources().getString(R.string.Url_about_Theme) + imageV1, image1);
        FuncClass.setImage(getContext(), getContext().getResources().getString(R.string.Url_about_Theme) + imageV2, image2);
         text1.setText(FuncClass.check1(cursor.getString(desc),getContext()));

    }


}
