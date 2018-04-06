package com.myrules.melez.resms3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbManagement extends SQLiteOpenHelper {

    public  DbManagement(Context context){
        super(context, "database.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(
                "create table wiadomosci (" +
                        "id integer primary key autoincrement," +
                        "numer text," +
                        "data DATETIME DEFAULT CURRENT_TIMESTAMP)" +
                        "");
        db.execSQL(
                "create table ustawienia (" +
                        "aktywacja int," +
                        "wizytowka text," +
                        "opoznienie int)" +
                        "");
        db.execSQL(
                "insert into ustawienia (aktywacja, wizytowka,opoznienie) values (" +
                        "0," +
                        "'Dziękuję za kontakt'" +
                        ",10) " +
                        "");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int NewVersion){

    }

    public void wpisz(String numer){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("numer", numer);
        // wartosci.put("data","NOW()");
        db.insertOrThrow("wiadomosci",null, wartosci);
    }

    public Cursor odczytaj(){
        String[] kolumny={"id","numer","data"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor =db.query("wiadomosci",kolumny,null,null,null,null,null);
        return kursor;
    }

    public Cursor odczytaj_numer(String numer){
        String[] args = {numer};
        String[] kol = {"numer"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor =db.query("wiadomosci",kol,"numer=?",args,null,null,null);


        //int count = kursor.getCount();
        //   kursor.close();
        return kursor;
    }

    public Cursor odczytaj_ustwienia(){
        String[] kolumny={"aktywacja","wizytowka","opoznienie"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor =db.query("ustawienia",kolumny,null,null,null,null,null);
        return kursor;
    }

    public void zmien_ustwienia(String kolumna, String wartosc){
        ContentValues cv = new ContentValues();
        cv.put(kolumna, wartosc);
        SQLiteDatabase db = getWritableDatabase();
        db.update("ustawienia",cv,null,null);

    }

}
