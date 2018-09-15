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

import com.example.diplom_examle.DatabaseHelper;
import com.example.diplom_examle.R;

import Data_classes.FuncClass;

/**
 * Created by Григорий Кисляков on 22.05.2018.
 */

public class Com_image extends Fragment {
    private  DatabaseHelper db;
    private  SQLiteDatabase sd;
    private TextView text_title;
    private ImageView image;
    private static final String ARG_PARAM1 = "name";
    private static final String ARG_PARAM2 = "image_url";
    private String name;
    private String URL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
            URL= getArguments().getString(ARG_PARAM2);
        } else {
            name="";
            URL="";
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.comm_image, container, false);
        text_title=(TextView) v.findViewById(R.id.main_text);
        image=(ImageView) v.findViewById(R.id.image_com);
        String st= getContext().getResources().getString(R.string.Url_about_Theme);
        text_title.setText(name);
        FuncClass.setImage(getContext(),st+URL,image);
        return v;
    }

}