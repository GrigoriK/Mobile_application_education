package Adapters;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diplom_examle.R;

import java.util.ArrayList;

import Data_classes.Categories_class;
import Data_classes.Document;
import Data_classes.FuncClass;
import Interfaces.OnFragmentInteractionListener;

/**
 * Created by Григорий Кисляков on 08.05.2018.
 */

public class Documents_Adapter extends  RecyclerView.Adapter<Documents_Adapter.ViewHolder> {
    private ArrayList<Document> dataSet;
    private   Boolean check=false;
    private Context context;
    private View last_view=null;
    private OnFragmentInteractionListener frag_listener;
    private   String  Url;
    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
// отдельного пункта списка
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView first_text;
        TextView writers_text;
        TextView price_text;
        TextView time_text;
        ImageView icon;
        private Document  item_category;


        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
             first_text=(TextView) itemView.findViewById(R.id.first_text);
             writers_text =(TextView) itemView.findViewById(R.id.writers_text);
             price_text=(TextView) itemView.findViewById(R.id.price_text);
             time_text=(TextView) itemView.findViewById(R.id.time_text);
            icon=(ImageView) itemView.findViewById(R.id.image_icon);

        }
        public void bind(Document lang1){
            item_category=lang1;
        }


       /* В данном методе,при нажатии пользователя,
        происходит проверка на платный контент.
        Если контент бесплатный,то происходит вызов страницы нужно материала через Activity
        */
        @Override
        public void onClick(View v) {
          if(item_category.getPrice()==0.0) {
              Log.i("My_Log",item_category.getPost());
              if(item_category.getPost().equals("По получении ID")){
                  Toast.makeText(v.getContext(),"Регистрация в данной версии недоступна",Toast.LENGTH_SHORT).show();

              }
              else {
                  frag_listener.CallFragment(item_category);
              }
            }
            else {

              Toast.makeText(v.getContext(),"В данной версии недоступен платный контент",Toast.LENGTH_SHORT).show();
                  }
        }
    }


    // Конструктор
    public Documents_Adapter(ArrayList<Document> data,OnFragmentInteractionListener frag_listener,Context context) {
        this.dataSet = data;
        this.frag_listener= frag_listener;
        this.context=context;
        this.Url= context.getResources().getString(R.string.Url_product);
    }
    @Override
    public Documents_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_drawer_documents, parent, false);

        final Documents_Adapter.ViewHolder vh = new Documents_Adapter.ViewHolder(view);
        return vh;
    }




    @Override
    public void onBindViewHolder(Documents_Adapter.ViewHolder holder, int position) {
        TextView first_text=holder.first_text;
        TextView writers_text=holder.writers_text;
        TextView price_text=holder.price_text;
        TextView time_text=holder.time_text;
        ImageView icon=holder.icon;
        Document doc=dataSet.get(position);
        holder.bind(doc);
        first_text.setText(doc.getName()+"(Код:"+doc.getCode()+")");
        if(!(doc.getAuthor().equals(""))) {
            writers_text.setText("Авторы:" + doc.getAuthor());
        }
        else{
            writers_text.setVisibility(View.GONE);
        }
        FuncClass.setImage(context,Url+doc.getImage(),icon);
          price_text.setText(doc.getPrice()+" Руб.");
         time_text.setText("Срок поставки: "+doc.getPost());


    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}