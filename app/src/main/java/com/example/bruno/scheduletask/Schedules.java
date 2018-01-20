package com.example.bruno.scheduletask;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Schedules extends Activity {

    protected TextView textViewNome;
    protected TextView textViewData;
    protected TextView textViewHora;
    protected TextView textViewMorada;
    protected TextView textViewDescricao;
    protected Button btnEditSchedule;
    protected Button btnDeleteSchedule;
    protected DbAdaptor dbAdaptor;
    protected Intent oIntent;
    protected int id;
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
        setContentView(R.layout.activity_schedules);

        textViewNome = (TextView) findViewById(R.id.textViewNome);
        textViewData = (TextView) findViewById(R.id.textViewData);
        textViewHora = (TextView) findViewById(R.id.textViewHora);
        textViewMorada = (TextView) findViewById(R.id.textViewMorada);
        textViewDescricao = (TextView) findViewById(R.id.textViewDescricao);

        dbAdaptor = new DbAdaptor(this).open();
        oIntent = getIntent();
        id = oIntent.getExtras().getInt("_id");
        String t = oIntent.getExtras().getString("_id");
        Log.v("Teste", ""+id);
        Log.v("Teste2", ""+t);
        cursor = dbAdaptor.getScheduleId(id);

        textViewMorada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://maps.google.co.in/maps?q=" + textViewMorada.getText().toString()));
                startActivity(intent);
            }
        });

        if (cursor.moveToFirst()) {
            textViewNome.setText(cursor.getString(1));
            textViewData.setText(cursor.getString(2));
            textViewHora.setText(cursor.getString(3));
            textViewMorada.setText(cursor.getString(4));
            textViewDescricao.setText(cursor.getString(5));
        }

        btnEditSchedule = (Button) findViewById(R.id.btnEditSchedule);
        btnEditSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int _id = cursor.getInt(0);
                Intent intent = new Intent(Schedules.this, EditSchedule.class);
                intent.putExtra("_id", _id);
                startActivity(intent);
            }
        });

        btnDeleteSchedule = (Button) findViewById(R.id.btnDeleteSchedule);
        btnDeleteSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbAdaptor.deleteSchedule(id);
                Toast toast = Toast.makeText(Schedules.this, "Apagada com sucesso", Toast.LENGTH_LONG);
                toast.show();

                Intent intent = new Intent(Schedules.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Schedules.this, MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
