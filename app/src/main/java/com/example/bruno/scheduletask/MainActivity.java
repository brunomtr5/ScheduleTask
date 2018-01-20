package com.example.bruno.scheduletask;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    protected Button btnAddSchedule;
    protected ListView listViewSchedules;
    protected List<String> schedules;
    protected DbAdaptor dbAdaptor;
    protected Cursor cursor;

    @Override
    protected void onStart() {
        super.onStart();
        dbAdaptor = new DbAdaptor(this).open();
    }

    protected void onPause(){super.onPause();}

    @Override
    protected void onStop(){
        super.onStop();
        dbAdaptor.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cursor = null;
        schedules = new ArrayList<>();
        dbAdaptor = new DbAdaptor(this).open();

        cursor = dbAdaptor.getSchedule();

        if (cursor.moveToFirst()){
            do {
                schedules.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }

        listViewSchedules = (ListView)findViewById(R.id.listViewSchedules);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, schedules);

        listViewSchedules.setAdapter(adapter);

        listViewSchedules.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                int _id = cursor.getInt(0);

                Intent intent = new Intent(MainActivity.this, Schedules.class);
                intent.putExtra("_id", id);
                startActivity(intent);
            }
        });

        btnAddSchedule = (Button) findViewById(R.id.btnAddSchedule);
        btnAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddSchedule.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                moveTaskToBack(true);
                return true;
        }
        return false;
    }
}

