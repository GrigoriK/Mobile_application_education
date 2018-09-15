package Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diplom_examle.DatabaseHelper;
import com.example.diplom_examle.R;

import java.util.ArrayList;

import Data_classes.Comments;
import Data_classes.FuncClass;

/**
 * Created by Григорий Кисляков on 22.05.2018.
 */

public class Only_Text extends Fragment implements View.OnClickListener {

    private  DatabaseHelper db;
    private  SQLiteDatabase sd;
    private static final String ARG_PARAM1 = "categorie_id";
    private int category_id;
    private TextView title;
    private TextView only_text;
    private ImageView image_icon;
    private String Url;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.only_text, container, false);
          title=(TextView) view.findViewById(R.id.text_title);
          only_text=(TextView) view.findViewById(R.id.text_main);
        image_icon=(ImageView) view.findViewById(R.id.image_only);
          setData(title,only_text,image_icon);
        only_text.setOnClickListener(this);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getContext());
        sd=db.getReadableDatabase();
        if (getArguments() != null) {
            category_id = getArguments().getInt(ARG_PARAM1);
        } else {
            category_id = 73;
        }
    }

    //Заполнение контентом страницы
    public void setData(TextView text1,TextView text2,ImageView imageV){
          int desc,name,image;
        String selection = "category_id = ?";
        String[] selection_Args = {Integer.toString(category_id)};

        Cursor cursor = FuncClass.getCategories_cursor(sd,selection,selection_Args);
        desc = cursor.getColumnIndex("description_ru-RU");
        name = cursor.getColumnIndex("name_ru-RU");
        image = cursor.getColumnIndex("category_image");
        cursor.moveToNext();
       FuncClass.setImage(getContext(), getContext().getResources().getString(R.string.Url_Categories) + cursor.getString(image), imageV);
   text1.setText(cursor.getString(name));
        Url=FuncClass.first_href(cursor.getString(desc));
        text2.setText(FuncClass.check1(cursor.getString(desc),getContext()));
    }

    //Метод интерфейса при нажатии на соответсвующий элемент
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.text_main):{
             if(only_text.getText().toString().toLowerCase().trim().equals("список экспертов")){
                 String st = getContext().getResources().getString(R.string.Url_about_Theme);
                 String url = st + Url;
                 Intent i = new Intent(Intent.ACTION_VIEW);
                 i.setData(Uri.parse(url));
                 startActivity(i);
             }
             }
        }
    }
}
