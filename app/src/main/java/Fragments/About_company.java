package Fragments;


import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diplom_examle.DatabaseHelper;
import com.example.diplom_examle.R;

import Data_classes.FuncClass;

/**
 * Created by Григорий Кисляков on 21.05.2018.
 */

public class About_company extends Fragment {
      private TextView text_about;
    DatabaseHelper db;
    SQLiteDatabase sd;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.about_company, container, false);
        text_about= (TextView) v.findViewById(R.id.text_academi);
        text_about.setText(FuncClass.makeSectionOfTextBold(getText(),"Дмитрий Федоров Руководитель дирекции Академии современных инфокоммуникационных технологий") );

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         db = new DatabaseHelper(getContext());
        sd = db.getReadableDatabase();
    }
//Получения информации о компании из БД
    public String getText(){
        String selection="id= ?";
        String [] selection_Args ={"4"};
         String returnS;
        Cursor cursor = FuncClass.get_cursor(sd,selection,selection_Args,"qfr3h_content");
        int  li=cursor.getColumnIndex("introtext");
cursor.moveToNext();
        returnS=FuncClass.get_text(cursor.getString(li));
        db.close();
        cursor.close();
return returnS;


    }
}

