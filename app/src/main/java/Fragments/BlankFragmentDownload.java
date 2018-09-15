package Fragments;


import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diplom_examle.DatabaseHelper;
import com.example.diplom_examle.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import Adapters.Documents_Adapter;
import Data_classes.Document;
import Data_classes.FuncClass;
import Fragments.Fragment_documents_list;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragmentDownload extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "parent_id";
    private static final String ARG_PARAM2 = "price";
    private static final String ARG_PARAM3 = "deliver_time";
    private static final String ARG_PARAM4 = "deliver_type";
    private static final String ARG_PARAM5 = "name";
    private static final String ARG_PARAM6 = "title";
    private static final String ARG_PARAM7 = "code";
    private int parent_id;
    private String img_url;
    private double price;
    private String deliver;
    private String title_text;
    private String name;
    private String code;
    private int del_id;
    private TextView price_text;
    private TextView doc_text;
    private TextView pages_text;
    private TextView deliver_text;
    private TextView name_text;
    private TextView language_text;
    private TextView size_text;
    private ImageView img_load;
    private String file;
    TextView title;
    private String desc;
    Button button_down_load;
    DatabaseHelper db;
    SQLiteDatabase sd;
//Метод получения заполнения страницы данными
    public void getData() {
        int size, format,logo, lang, numb_part, pages;
        String selection = "product_id = ?";
        String[] selection_Args = {Integer.toString(parent_id)};
        Cursor cursor1 = FuncClass.get_cursor(sd ,selection,selection_Args,"qfr3h_jshopping_products2");
        size = cursor1.getColumnIndex("extra_field_1");
        pages = cursor1.getColumnIndex("extra_field_6");
        logo = cursor1.getColumnIndex("product_name_image");
        format=cursor1.getColumnIndex("description_ru-RU");
        cursor1.moveToNext();
        desc=cursor1.getString(format);
        doc_text.setText("Формат: " + FuncClass.logCursor(sd, "extra_field_2", parent_id));
        language_text.setText("Язык: " + FuncClass.logCursor(sd, "extra_field_3", parent_id));
        pages_text.setText("Кол-во страниц: " + cursor1.getString(pages));
        price_text.setText("Цена:" + String.format(Double.toString(price), "0.0") + " Руб.");
        deliver_text.setText("Срок поставки: " + deliver);
        String image=cursor1.getString(logo);
        FuncClass.setImage(getContext(),img_url+image,img_load);
        name_text.setText(check());
        if (del_id != 5) {
            button_down_load.setText("Скачать");
             file = FuncClass.curText(sd, Integer.toString(parent_id), "qfr3h_jshopping_products_files", "demo");
            size_text.setText("Размер архива, Мб: " + cursor1.getString(size));

        } else {
            size_text.setVisibility(View.GONE);
            button_down_load.setText("Посмотреть документ на сайте");
        }
        //title.setText(Html.fromHtml(cursor1.getString(name)));
        db.close();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getContext());
        sd=db.getReadableDatabase();
        img_url=getContext().getResources().getString(R.string.Url_product);
        if (getArguments() != null) {
            parent_id = getArguments().getInt(ARG_PARAM1);
            price = getArguments().getDouble(ARG_PARAM2);
            deliver = getArguments().getString(ARG_PARAM3);
            del_id = getArguments().getInt(ARG_PARAM4);
            name = getArguments().getString(ARG_PARAM5);
            title_text = getArguments().getString(ARG_PARAM6);
            code = getArguments().getString(ARG_PARAM7);
        } else {
            parent_id = 1;
            price = 0.0;
            deliver = "";
            del_id = 0;
            name = "Example";
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
            v = inflater.inflate(R.layout.fragment_blank_fragment_download, container, false);
            price_text = (TextView) v.findViewById(R.id.price_text);
            deliver_text = (TextView) v.findViewById(R.id.post_text);
            button_down_load = (Button) v.findViewById(R.id.button_down_load);
            button_down_load.setOnClickListener(this);
            pages_text = (TextView) v.findViewById(R.id.pages_text);
            name_text = (TextView) v.findViewById(R.id.start_text);
            doc_text = (TextView) v.findViewById(R.id.doc_text);
            size_text = (TextView) v.findViewById(R.id.size_text);
            language_text = (TextView) v.findViewById(R.id.language_text);
            img_load = (ImageView) v.findViewById(R.id.image_download);
            getData();


        return v;
    }


   //Действия осуществляемы после нажатия на кнопку
    @Override
    public void onClick(View v) {
        if (del_id != 5) {
            if (!file.equals("")) {
            String url = "http://a0198994.xsph.ru/components/com_jshopping/files/demo_products/"+file;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
               // Toast.makeText(getContext(), "Starting download", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Uncorrect file", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            String url = "http://a0198994.xsph.ru/"+FuncClass.get_href(desc);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

            Toast.makeText(getContext(), "Show lection", Toast.LENGTH_SHORT).show();
        }
    }
//Пороверка на пустую строку
    private  String check(){
        String st="";
        if(!name.equals("")){
            return name;
        }
        return title_text;
    }
}
