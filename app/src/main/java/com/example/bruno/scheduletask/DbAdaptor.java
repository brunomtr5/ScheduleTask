package com.example.bruno.scheduletask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by bruno on 20/01/2018.
 */

public class DbAdaptor {
    private DbHelper dbHelper;
    private SQLiteDatabase database;

    public DbAdaptor(Context context){
        dbHelper = new DbHelper(context);
    }

    public DbAdaptor open() {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public Cursor getSchedule(){
        Cursor cursor = database.rawQuery(
                "select _id, nome, data, hora, morada, descricao from schedule", null);
        return cursor;
    }

    public Cursor getScheduleId(Integer id){
        Cursor cursor = database.rawQuery(
              "select _id, nome, data, hora, morada, descricao from schedule where _id = ?", new String[] { id.toString() });
        return cursor;
    }

    public boolean putSchedule(String nome, String data, String hora, String morada, String descricao){
        ContentValues values;
        long result;

        database = dbHelper.getWritableDatabase();
        values = new ContentValues();
        values.put("nome", nome);
        values.put("data", data);
        values.put("hora", hora);
        values.put("morada", morada);
        values.put("descricao", descricao);

        result = database.insert("schedule", null, values);
        database.close();

        if (result ==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean editSchedule(Integer id, String nome, String data, String hora, String morada, String descricao){
        long result;

        String whereClause = "_id = ?";
        String[] whereArgs = new String[1];
        whereArgs[0] = new Integer(id).toString();

        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("data", data);
        values.put("hora", hora);
        values.put("morada", morada);
        values.put("descricao", descricao);

        result = database.update("schedule", values, whereClause, whereArgs);

        if (result ==-1){
            return false;
        }
        else {
            return true;
        }
    }

    public int deleteSchedule(Integer id) {
        String whereClause = "_id = ?";
        String[] whereArgs = new String[1];
        whereArgs[0] = ""+id;
        return database.delete("schedule", whereClause, whereArgs);
    }
}
