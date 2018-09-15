package Data_classes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.diplom_examle.DatabaseHelper;
import com.example.diplom_examle.R;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Григорий Кисляков on 15.05.2018.
 */

public class FuncClass {

//Метод  удаления лишних символов из тегов HTML
    public static String checkStr(String html, String[] st) {
        String t = "";

        Boolean a = (html.equals("\\r\\n")) || (html.equals("\\r\\n\\r\\n"));
        Boolean k = false;
        if (a) {
            return t;
        }
        for (int i = 0; i < st.length; i++) {
            if (st[i].length() < html.length()) {
                k = html.substring(0, st[i].length()).equals(st[i]);
            }
            if (k) {
                continue;
            } else {
                t = html;
                k = false;
            }

        }


        return t;
    }
    //Метод  получения ссылки на 1-но изображение из тегов HTML
    public static String getImage(String html){
        if(!html.contains("<img")){
            return "";
        }
        Document doc=Jsoup.parse(html);
        Element imageElement = doc.select("img").first();
        String srcValue = imageElement.attr("src").equals(null)? " ":imageElement.attr("src");
        return srcValue;


    }
    //Метод  получения ссылки на изображения из тегов HTML
    public static ArrayList<String> getmage(String html){
        if(!html.contains("<img")){
            return new ArrayList<String>();
        }
        Document doc=Jsoup.parse(html);
        ArrayList<String> st= new ArrayList<>();
        Elements imageElement = doc.select("img");
        for(Element el:imageElement) {
            st.add(el.attr("src").equals(null) ? " " : el.attr("src"));

        }
        return st;

    }
    //Метод  получения ссылки на видео из тегов HTML
    public static String get_href(String html){
        Document doc=Jsoup.parse(html);
        Elements el2 = doc.getElementsByTag("a");
        for (Element el:el2){
            String [] st =el.attr("href").split("/");
            if(st[0].equals("content")){
                return  el.attr("href");
            }
        }
        return  "";
    }
    //Метод  получения ссылки на изображения из тегов HTML
    public static String first_href(String html){
        Document doc=Jsoup.parse(html);
        Elements el2 = doc.getElementsByTag("a");
        try {
            return el2.first().attr("href");
        }
        catch (Exception e){
            return "";
        }

        }


    //Метод  получения ссылки на комментарии из тегов HTML
    public static ArrayList<Comments> getComments(String html){
        ArrayList<Comments> list=new ArrayList<Comments>();
        Document doc=Jsoup.parse(html);
        Elements el = doc.select("p");
        Elements el2 = doc.getElementsByTag("a");
        int i=0;
        String st;
        Boolean k=false;
        for(Element el1:el) {
            st=el2.get(i).attr("href");
            if(st.substring(st.length()-3,st.length()).equals("jpg")) {
                k=true;
            }
            list.add(new Comments(i,el1.text(),st,k));
            i++;
            k=false;

        }


        return list;


    }
    //Метод  получения id родителя
    public static int getParent(Context context,int id){
       DatabaseHelper db = new DatabaseHelper(context);
        SQLiteDatabase sd = db.getReadableDatabase();
        String selection = "category_id = ?";
        String[] selection_Args = {Integer.toString(id)};
        Cursor cursor1 = sd.query("qfr3h_jshopping_categories2", null, selection, selection_Args, null, null, null);
       int id_parent = cursor1.getColumnIndex("category_parent_id");
        cursor1.moveToNext();

        return cursor1.getInt(id_parent);
    }
    //Метод  получения текста
    public static  String  get_text(String st) {
        String promStr="";
        Document doc=Jsoup.parse(st);
        Elements element=doc.select("p");

        for(Element el:element){
            if(checkTel(el.text())){
                return promStr;
            }

            promStr=promStr+"  "+el.text()+"\n"+"\n";

        }

        return promStr;
    }
    //Метод  получения Cursor категории
    public static  Cursor getCategories_cursor(SQLiteDatabase sd  ,String selection,String [] selection_Args){
        Cursor cursor = sd.query("qfr3h_jshopping_categories2", null, selection, selection_Args, null, null, null);
         return cursor;
    }
    //Метод  получения Cursor из различных таблиц
    public static  Cursor get_cursor(SQLiteDatabase sd ,String selection,String [] selection_Args,String table_name){
        Cursor cursor = sd.query(table_name, null, selection, selection_Args, null, null, null);
        return cursor;
    }
    //Метод  получения текста и проверка на существование подтегов текста
    public static String check1(String st, Context context) {
        String[] base = context.getResources().getStringArray(R.array.array);
        StringBuffer buffer = new StringBuffer();
        org.jsoup.nodes.Document doc = Jsoup.parse(st);
        Elements el = doc.select("p");
        Elements t1 = doc.select("li");
        String k = "";
        Boolean b = false;
        Boolean check_b=false;
        StringBuffer st1 = new StringBuffer();
        if (!t1.isEmpty()) {
            b = true;
            st1.append("\n");
            for (Element e1 : t1) {

                st1.append("-" + e1.text() + "\n");
            }
            st1.append("\n");
        }

        {
            for (Element e1 : el) {
                k = checkStr(e1.text(), base);
                char[] c = k.toCharArray();
                if (c.length != 0) {
                    if ((c[c.length - 1] == ':') && b) {
                        buffer.append(k + "\n" + st1);


                    } else {

                        buffer.append(k + "\n"+"\n");
                    }

                }
            }
        }


        return buffer.toString();
    }
    //Метод  получения Cursor из связанных таблиц
    public static String logCursor(SQLiteDatabase sd, String k, int id) {

        String table = "qfr3h_jshopping_products2 as PL inner join qfr3h_jshopping_products_extra_field_values as PS on PL." + k + " = PS.id";
        String columns[] = {"PS.name as product"};
        String selection = "product_id = ?";
        String[] selectionArgs = {Integer.toString(id)};
        Cursor c = sd.query(table, columns, selection, selectionArgs, null, null, null);
        String str = "";
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    str = "";
                    for (String cn : c.getColumnNames()) {
                        str = str.concat(c.getString(c.getColumnIndex(cn)));
                    }
                } while (c.moveToNext());
            }
        } else

            c.close();
        return str;
    }
    //Метод  получения данных из одного из полей таблицы products
    public static String curText(SQLiteDatabase sd, String parent_id, String table, String getColumn) {
        String stroc = "";
        String selection = "product_id = ?";
        String[] selection_Args = {parent_id};
        Cursor cursor2 = sd.query(table, null, selection, selection_Args, null, null, null);
        int t = cursor2.getColumnIndex(getColumn);
        cursor2.moveToNext();
        if (cursor2 != null) {

            stroc = cursor2.getString(t);
        }

        cursor2.close();
        return stroc;
    }
// Установка изображения с помощью бибилиотеки Picasso.
    public static void setImage(Context context, String url, ImageView image){
        Picasso.with(context)
                .load(url)
                .into(image);
    }
    // Получения телефона из HTML файла
    public static String getTel(String tel,String clas_name){
        String p="";
        Document doc=Jsoup.parse(tel);
        Elements element=doc.getElementsByClass(clas_name);

        for(Element el:element){
            if(checkTel(el.text())) {

                p=p+"  "+el.text()+"\n";
            }

        }
        return p;


    }
    // Получения ссылки на видео
    public static ArrayList<Video> get_video_url(String st) {
        ArrayList<Video> arr=new ArrayList<Video>();
        String promStr="";
        int k=0;
        int p=0;
        int i=0;
        while(st.length()!=0) {
            i++;
            k=st.indexOf("{");
            if(!((k<0))){
                p=st.indexOf("{/", k);

                k+=9;
                promStr=st.substring(k,p);
                arr.add(new Video("Видео "+i,i,promStr.trim()));
                st= st.substring(p+10,st.length());
            }
            else {
                break;
            }
        }

        return arr;
    }
    // Изменение необходимой части текста на жирный
    public static SpannableStringBuilder makeSectionOfTextBold(String text, String textToBold){

        SpannableStringBuilder builder=new SpannableStringBuilder();

        if(textToBold.length() > 0 && !textToBold.trim().equals("")){

            //for counting start/end indexes
            String testText = text.toLowerCase(Locale.US);
            String testTextToBold = textToBold.toLowerCase(Locale.US);
            int startingIndex = testText.indexOf(testTextToBold);
            int endingIndex = startingIndex + testTextToBold.length();
            //for counting start/end indexes

            if(startingIndex < 0 || endingIndex <0){
                return builder.append(text);
            }
            else if(startingIndex >= 0 && endingIndex >=0){

                builder.append(text);
                builder.setSpan(new StyleSpan(Typeface.BOLD), startingIndex, endingIndex, 0);
            }
        }else{
            return builder.append(text);
        }

        return builder;
    }
    // Проверка строки на наличие телефона
    private static Boolean checkTel(String st){
        Boolean b=false;
        if((!(st.length()==0)&&(st.toCharArray()[0]=='+'))){
            b=true;
        }
        return b;
    }

}

