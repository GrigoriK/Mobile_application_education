package Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diplom_examle.DrawerActivity;
import com.example.diplom_examle.R;

import java.util.ArrayList;

import Data_classes.Categories_class;
import Fragments.Categories;
import Interfaces.OnFragmentInteractionListener;

/**
 * Created by Григорий Кисляков on 05.05.2018.
 */

public class Categories_Adapter extends  RecyclerView.Adapter<Categories_Adapter.ViewHolder> {

    private ArrayList<Categories_class> dataSet;
    private OnFragmentInteractionListener frag_listener;
// класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
// отдельного пункта списка
public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView word;
    private Categories_class  item_category;


    public ViewHolder(View v) {
        super(v);
        v.setOnClickListener(this);
        this.word= (TextView)itemView.findViewById(R.id.wordtext);
    }
    public void bind(Categories_class lang1){
        item_category=lang1;
    }

    //Метод при нажатии на кнопку
    @Override
    public void onClick(View v) {
        String st= Integer.toString(item_category.getId());
        //При нажатии на соответвующую иконку происходит переход на следующую страницу через связь с Activity
        frag_listener.onMassageLang(1,item_category.getId());
    }
}


    // Конструктор
    public Categories_Adapter(ArrayList<Categories_class> data,OnFragmentInteractionListener frag_listener) {
        this.dataSet = data;
        this.frag_listener= frag_listener;
    }

    // Создает новые views (вызывается layout manager-ом)
    @Override
    public Categories_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_relative, parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }




    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView word1= holder.word;
        Categories_class cat=dataSet.get(position);
    String  langu=cat.getName();
        holder.bind(cat);
        word1.setText(langu);

    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}