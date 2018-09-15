package Fragments;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.diplom_examle.DatabaseHelper;
import com.example.diplom_examle.R;

import Data_classes.FuncClass;
import Interfaces.OnFragmentInteractionListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_about_theme extends Fragment implements View.OnClickListener {

     DatabaseHelper db;
    private OnFragmentInteractionListener frag_listener;
    private TextView content;
    private ImageView next_list;
    private ImageView icons;
    private String url;
    private SQLiteDatabase sd;
    private static final String ARG_PARAM1 = "categorie_id";
    private static final String ARG_PARAM2 = "param2";
    private int parent_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getContext());
        sd=db.getReadableDatabase();
        this.url= getContext().getResources().getString(R.string.Url_about_Theme);
        if (getArguments() != null) {
            parent_id = getArguments().getInt(ARG_PARAM1);
        } else {
            parent_id = 1;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_theme, container, false);
        TextView content = (TextView) view.findViewById(R.id.content);
        ImageView next_list = (ImageView) view.findViewById(R.id.next_list);
        ImageView icons = (ImageView) view.findViewById(R.id.image_cat_theme);
        next_list.setOnClickListener(this);
        setData(content,icons);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.next_list): {
                frag_listener.onMassageLang(3, parent_id);
                     break;
            }

        }

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

    //Наполниение данными страницы подробной информации о теме
    public void setData(TextView text,ImageView image) {
         String selection = "category_id = ?";
        String[] selection_Args = {Integer.toString(parent_id)};
        Cursor cursor = FuncClass.getCategories_cursor(sd,selection,selection_Args);
        int desc = cursor.getColumnIndex("description_ru-RU");
        int image2 = cursor.getColumnIndex("category_image");
        int desc2 = cursor.getColumnIndex("short_description_ru-RU");
        cursor.moveToNext();
        if(!(cursor.getString(desc).isEmpty())){
            text.setText(FuncClass.check1(cursor.getString(desc),getContext()));
     Log.i("TestLog",cursor.getString(desc));
            String image_Url=FuncClass.getImage(cursor.getString(desc));
                     if(image_Url.equals("")) {
                    FuncClass.setImage(getContext(), url + image_Url, image);
                }
                else {

                    FuncClass.setImage(getContext(), getContext().getResources().getString(R.string.Url_Categories) + cursor.getString(image2), image);

                }

        }
        else {

            text.setText(cursor.getString(desc2));
            FuncClass.setImage(getContext(), getContext().getResources().getString(R.string.Url_Categories) + cursor.getString(image2), image);
        }




    }
}
