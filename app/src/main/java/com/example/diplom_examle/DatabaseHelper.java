package com.example.diplom_examle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
/**
 * Created by Григорий Кисляков on 05.05.2018.
 */
    public class DatabaseHelper extends SQLiteOpenHelper {


        private static String DB_PATH = "";

        private static String DB_NAME = "acikt.db";

        private SQLiteDatabase myDataBase;

        private final Context myContext;

        public DatabaseHelper(Context context) {

            super(context, DB_NAME, null, 1);
            this.myContext = context;
            DB_PATH= myContext.getDatabasePath(DB_NAME).toString();
        }

        /**
         * Создание пустой базы данных.
         * */
        public void createDataBase() throws IOException {

            boolean dbExist = checkDataBase();

            if(dbExist){
                //база данных уже созадана
            }else{

                //Создание базы данных.
                this.getWritableDatabase();

                try {

                    copyDataBase();

                } catch (IOException e) {

                    throw new Error("Error copying database");

                }

            }

        }

        /**
         * Проверка базы данных на создание/пустоту
         */
        private boolean checkDataBase(){

            SQLiteDatabase checkDB = null;

            try{
                String myPath = DB_PATH ;
                checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

            }catch(SQLiteException e){



            }

            if(checkDB != null){

                checkDB.close();

            }

            return checkDB != null ? true : false;
        }

        /**
         * Копирование контента в базу данных из файла
         * */

        private void copyDataBase() throws IOException {

            //Открытие файла базы данных
            InputStream myInput = myContext.getAssets().open(DB_NAME);

            String outFileName = DB_PATH ;

            //Открытие потока для заполения базы данных
            OutputStream myOutput = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer))>0){
                myOutput.write(buffer, 0, length);
            }

            //закрытие потока
            myOutput.flush();
            myOutput.close();
            myInput.close();

        }

        public void openDataBase() throws SQLException {

            //Открытие базы данных
            String myPath = DB_PATH ;
            myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }

        @Override
        public synchronized void close() {

            if(myDataBase != null)
                myDataBase.close();

            super.close();

        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }


}

