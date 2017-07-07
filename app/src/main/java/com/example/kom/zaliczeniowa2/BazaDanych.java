package com.example.kom.zaliczeniowa2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kom on 2017-07-06.
 */

public class BazaDanych extends SQLiteOpenHelper
{


    public BazaDanych(Context context) {
        super(context, "zdjecia.db", null, 1);
    }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL("create table zdjecia("+
                    "nr integer primary key autoincrement,"+
                    "nazwa text,"+
                    "gps text,"+
                    "lokalizacja text);"+
                    "");
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {

        }


    public void dodajElemnt(String nazwa,String gps,String lokalizacja)
    {
        SQLiteDatabase db=getWritableDatabase();

        ContentValues wartosc =new ContentValues();

        wartosc.put("nazwa",nazwa);
        wartosc.put("gps",gps);
        wartosc.put("lokalizacja", lokalizacja);

        db.insertOrThrow("zdjecia",null,wartosc);
    }

    public List<Zdjecie> dajWszyskie()
    {
       List<Zdjecie> listaZdjec=new ArrayList<Zdjecie>();
        String selectQuery="SELECT * FROM "+"zdjecia";
        SQLiteDatabase bd=this.getWritableDatabase();
        Cursor kursor =bd.rawQuery(selectQuery,null);

        while(kursor.moveToNext())
        {
            Zdjecie x=new Zdjecie();

            x.setNr(Long.valueOf(kursor.getLong(0)));
            x.setNazwa(kursor.getString(1));
            x.setGps(kursor.getString(2));
            x.setLokalizacja(kursor.getString(3));
           listaZdjec.add(x);
        }
        return listaZdjec;

    }

    public Cursor cursorDajWszytkie()
    {
        String [] kolumny={"nr","nazwa","gps","lokalizacja"};
        SQLiteDatabase bd=getReadableDatabase();
        Cursor kursor =bd.query("zdjecia",kolumny,null,null,null,null,null);

        return kursor;
    }


}
