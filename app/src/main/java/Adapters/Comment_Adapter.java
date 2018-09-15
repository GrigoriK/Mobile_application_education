package Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diplom_examle.R;

import java.util.ArrayList;

import Data_classes.Categories_class;
import Data_classes.Comments;
import Interfaces.OnFragmentInteractionListener;

/**
 * Created by Григорий Кисляков on 22.05.2018.
 */

public class Comment_Adapter extends  RecyclerView.Adapter<Comment_Adapter.ViewHolder> {

    private ArrayList<Comments> dataSet;
    private OnFragmentInteractionListener frag_listener;
    private Context context;

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
// отдельного пункта списка
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView word;
        private Comments item_category;


        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            this.word = (TextView) itemView.findViewById(R.id.wordtext);
        }

        public void bind(Comments lang1) {
            item_category = lang1;
        }

        //Метод при нажатии на кнопку
        @Override
        public void onClick(View v) {
            //При нажатии на соответствующую иконку происходит загрузка соответсвующего комментария
            if(item_category.getType()) {
                frag_listener.CallFragment(item_category);
            }
            else
            {
                String st = context.getResources().getString(R.string.Url_about_Theme);
                String url = st + item_category.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }

        }
    }


    // Конструктор
    public Comment_Adapter(ArrayList<Comments> data, OnFragmentInteractionListener frag_listener,Context context) {
        this.dataSet = data;
        this.frag_listener = frag_listener;
        this.context=context;
    }

    // Создает новые views (вызывается layout manager-ом)
    @Override
    public Comment_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_relative, parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)

        Comment_Adapter.ViewHolder vh = new Comment_Adapter.ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(Comment_Adapter.ViewHolder holder, int position) {
        TextView word1 = holder.word;
        Comments com = dataSet.get(position);
        String text_com = com.getText();
        holder.bind(com);
        word1.setText(text_com);

    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}


