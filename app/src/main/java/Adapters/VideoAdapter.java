package Adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diplom_examle.R;

import Data_classes.Video;

import java.util.ArrayList;

/**
 * Created by Григорий Кисляков on 20.05.2018.
 */

public class VideoAdapter extends  RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private ArrayList<Video> dataSet;

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
// отдельного пункта списка
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView first_text;
        private Video  item_category;


        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            first_text=(TextView) itemView.findViewById(R.id.video_text);


        }
        public void bind(Video lang1){
            item_category=lang1;
        }


       //Метод, осуществляющий обработк нажатия
        @Override
        public void onClick(View v) {
            //При нажатии открывается соответсвующая ссылка на видеохостинге YouTube
             String url = item_category.getUrl();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            v.getContext().startActivity(i);


        }
    }


    // Конструктор
    public VideoAdapter(ArrayList<Video> data) {
        this.dataSet = data;
    }
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video, parent, false);

        final VideoAdapter.ViewHolder vh = new VideoAdapter.ViewHolder(view);




        return vh;
    }




    @Override
    public void onBindViewHolder(VideoAdapter.ViewHolder holder, int position) {
        TextView first_text=holder.first_text;
        first_text.setText(dataSet.get(position).getName());
        Video doc=dataSet.get(position);
        holder.bind(doc);


    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
