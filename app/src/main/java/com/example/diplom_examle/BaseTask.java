package com.example.diplom_examle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

/**
 * Created by Григорий Кисляков on 05.05.2018.
 */

public class BaseTask extends AsyncTask<Void, Void, String> {


    DatabaseHelper db;
    public BaseTask(Context context){

        db = new DatabaseHelper(context);
        try {
            db.createDataBase();
            db.openDataBase();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(Void... voids) {
        return null;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);


    }
}
