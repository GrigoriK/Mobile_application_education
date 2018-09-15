package Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.diplom_examle.DatabaseHelper;
import com.example.diplom_examle.R;

import Data_classes.FuncClass;

/**
 * Created by Григорий Кисляков on 21.05.2018.
 */

public class Contakts extends Fragment  implements View.OnClickListener{
    private TextView text_Adress;
    private TextView text_tel;
    private TextView text_fax;
    private ImageView image;
    DatabaseHelper db;
    SQLiteDatabase sd;
      public String getText(){
        String selection="id = ?";
        String [] selection_Args ={"5"};
          Cursor cursor= FuncClass.get_cursor(sd,selection,selection_Args,"qfr3h_chronoforms");
         int  li=cursor.getColumnIndex("wizardcode");
        cursor.moveToNext();
        return cursor.getString(li).trim();

    }

    private void setText (TextView t1,TextView t2,TextView t3){
       String stroc=getText();
        t1.setText(FuncClass.get_text(stroc));
        t2.setText(FuncClass.getTel(stroc,"contact-telephone"));
        t3.setText(FuncClass.getTel(stroc,"contact-fax"));
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
         View v= inflater.inflate(R.layout.contakts, container, false);
        text_Adress= (TextView) v.findViewById(R.id.text_about);
        text_tel= (TextView) v.findViewById(R.id.text_tel);
        text_fax= (TextView) v.findViewById(R.id.text_fax);
        image=(ImageView) v.findViewById(R.id.call);
        image.setOnClickListener(this);
        setText(text_Adress,text_tel,text_fax);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getContext());
        sd = db.getReadableDatabase();
    }

   //Методы при нажатии на иконку телефона
    @Override
    public void onClick(View v) {
        Intent intent;
       String telephone_number=text_tel.getText().toString();

        switch (v.getId()){
            case(R.id.call):{
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+telephone_number));
                startActivity(intent);
                break;
            }
        }

    }
}