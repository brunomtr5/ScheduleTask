package com.example.bruno.scheduletask;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
/**
 * Created by bruno on 20/01/2018.
 */

public class DbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "database.db";
    private static final int VERSION = 1;

    public DbHelper(Context context){
        super (context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String schedule = "CREATE TABLE schedule(_id integer primary key autoincrement, nome varchar(50), data date, hora time, morada varchar(50), descricao text)";
        db.execSQL(schedule);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS schedule");
        onCreate(db);
    }
}
