package Adapters;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diplom_examle.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Data_classes.Categories_class;
import Data_classes.FuncClass;
import Fragments.Prof_Stand;
import Interfaces.OnFragmentInteractionListener;

/**
 * Created by Григорий Кисляков on 06.05.2018.
 */

public class Themes_Adapter extends  RecyclerView.Adapter<Themes_Adapter.ViewHolder> {
    private ArrayList<Categories_class> dataSet;
 private   Boolean check=false;
 private View last_view=null;
 private Context Mycontext;
 private String image_url;


    private OnFragmentInteractionListener frag_listener;
    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
// отдельного пункта списка
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RelativeLayout slide_panel;
        TextView word;
        TextView second_text;
        ImageView image;
        TextView more_details;
        private Categories_class  item_category;


        public ViewHolder(View v) {
            super(v);
            this.image=(ImageView) itemView.findViewById(R.id.image_theme);
            v.setOnClickListener(this);
            this.more_details = (TextView) itemView.findViewById(R.id.more_details);
            this.slide_panel= (RelativeLayout)itemView.findViewById(R.id.panel_slide);
            this.second_text= (TextView)itemView.findViewById(R.id.meaningtext);
            this.more_details.setOnClickListener(this);
            this.word= (TextView)itemView.findViewById(R.id.theme_text);
        }
        public void bind(Categories_class lang1){
            item_category=lang1;
        }


        //Метод при нажатии на соответсвующий элемент
        @Override
        public void onClick(View v) {
            /* Если нажато на поле "Подробнее" необходимо открыть страницу подробного описания
            темы направления
            */
            if(v.getId()==R.id.more_details){

                frag_listener.onMassageLang(2,item_category.getId());
            }
//Если подробная информация скрыта,то необходимо её отобразить
            if (!check) {

                slide_panel.setVisibility(View.VISIBLE);
                slide_panel.animate()
                        .alpha(1.0f)
                        .setDuration(1000);
               check = true;

            } else {

                slide_panel.setVisibility(View.VISIBLE);
                slide_panel.animate()
                        .alpha(1.0f)
                        .setDuration(1000);
                slide_panel.animate()
                        .alpha(0.0f)
                        .setDuration(1000);

                slide_panel.setVisibility(View.GONE);


                    check = false;


            }
        }
    }


    // Конструктор
    public Themes_Adapter(ArrayList<Categories_class> data,OnFragmentInteractionListener frag_listener,Context context) {
        this.dataSet = data;
        this.frag_listener= frag_listener;
        this.Mycontext =context;
        this.image_url= Mycontext.getResources().getString(R.string.Url_Categories);

    }
    @Override
    public Themes_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_themes, parent, false);

       final  ViewHolder vh = new ViewHolder(view);




        return vh;
    }




    @Override
    public void onBindViewHolder(Themes_Adapter.ViewHolder holder, int position) {
        TextView word1= holder.word;
        TextView word2 = holder.second_text;
        ImageView imageV = holder.image;
        Categories_class cat=dataSet.get(position);
        FuncClass.setImage(Mycontext,image_url+cat.getImage(),imageV);

        holder.bind(cat);
        word2.setText(cat.getDescription());
        word1.setText(cat.getName());

    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}

