package Fragments;

import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.diplom_examle.DatabaseHelper;
import com.example.diplom_examle.R;

import Adapters.VideoAdapter;
import Data_classes.FuncClass;

/**
 * Created by Григорий Кисляков on 20.05.2018.
 */

public class Video_Class extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "parent_id";
    private static final String ARG_PARAM2 = "price";
    private static final String ARG_PARAM3 = "deliver_time";
    private static final String ARG_PARAM4 = "deliver_type";
    private static final String ARG_PARAM5 = "name";
    private static final String ARG_PARAM6 = "title";
    private double price;
    private ImageView img_load;
    private String img_url;
    private String deliver;
    private String title_text;
    private String name;
    private int del_id;
     private String file;
    private TextView start_text;
    DatabaseHelper db;
    SQLiteDatabase sd;
        int parent_id;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private TextView price_text;
    private TextView doc_text;
    private TextView deliver_text;
    private TextView author_text;

  //Передача необходимой информации из БД адаптеру для последующего отображения
    public void getData() {

        int size,author_id, format, lang, numb_part, logo,pages;
        String selection = "product_id = ?";
        String[] selection_Args = {Integer.toString(parent_id)};
        Cursor cursor1 = FuncClass.get_cursor(sd,selection,selection_Args,"qfr3h_jshopping_products2");
        size = cursor1.getColumnIndex("extra_field_1");
        pages = cursor1.getColumnIndex("extra_field_6");
        logo = cursor1.getColumnIndex("product_name_image");
        author_id=cursor1.getColumnIndex("product_manufacturer_id");
        int name=cursor1.getColumnIndex("description_ru-RU");
        cursor1.moveToNext();
        author_id=cursor1.getInt(author_id);
        String auth="";
        if (author_id > 0) {
            selection = "manufacturer_id = ?";
            selection_Args = new String[]{Integer.toString(author_id)};
            Cursor cursorA = FuncClass.get_cursor(sd,selection,selection_Args,"qfr3h_jshopping_manufacturers");
            int auth_id1 = cursorA.getColumnIndex("name_ru-RU");
            cursorA.moveToNext();
           auth = cursorA.getString(auth_id1);

        }
        author_text.setText("Авторы: "+auth);
        doc_text.setText("Формат: " + FuncClass.logCursor(sd, "extra_field_2", parent_id));
         price_text.setText("Цена:" + String.format(Double.toString(price), "0.0") + " Руб.");
        deliver_text.setText("Срок поставки: " +deliver);
        String image=cursor1.getString(logo);
        FuncClass.setImage(getContext(),img_url+image,img_load);
        start_text.setText(title_text);


       adapter = new VideoAdapter(FuncClass.get_video_url(cursor1.getString(name)));
       recyclerView.setAdapter(adapter);
        cursor1.close();
        db.close();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       View v;
        v = inflater.inflate(R.layout.video_adapter, container, false);
        price_text = (TextView) v.findViewById(R.id.price_text);
        deliver_text = (TextView) v.findViewById(R.id.post_text);
        doc_text = (TextView) v.findViewById(R.id.doc_text);
        start_text=(TextView) v.findViewById(R.id.start_text);
        author_text = (TextView) v.findViewById(R.id.author_text);
        recyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_themes);
        layoutManager = new LinearLayoutManager(getContext());
        img_load = (ImageView) v.findViewById(R.id.image_video);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        getData();
        return  v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getContext());
        img_url=getContext().getResources().getString(R.string.Url_product);
        sd=db.getReadableDatabase();
        if (getArguments() != null) {
            parent_id = getArguments().getInt(ARG_PARAM1);
            price = getArguments().getDouble(ARG_PARAM2);
            deliver = getArguments().getString(ARG_PARAM3);
            del_id = getArguments().getInt(ARG_PARAM4);
            name = getArguments().getString(ARG_PARAM5);
            title_text = getArguments().getString(ARG_PARAM6);
        } else {
            parent_id = 481;
            price = 0.0;
            deliver = "";
            del_id = 0;
            name = "Example";
            title_text="Main_Text";
        }
    }

    @Override
    public void onClick(View v) {

    }


}
