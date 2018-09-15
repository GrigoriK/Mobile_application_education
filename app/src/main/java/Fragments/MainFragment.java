package Fragments;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diplom_examle.R;

import Data_classes.FuncClass;

/**
 * Created by Григорий Кисляков on 21.05.2018.
 */

public class MainFragment extends Fragment {
  private TextView text_about;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.main_fragment, container, false);
        text_about= (TextView) v.findViewById(R.id.text_about);
        text_about.setText(FuncClass.makeSectionOfTextBold(text_about.getText().toString(),"«Академия Современных ИнфоКоммуникационных Технологий»"));

    return v;
    }
}
