package com.example.bruno.scheduletask;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditSchedule extends Activity {

    protected EditText editTextEditNome;
    protected EditText editTextEditData;
    protected EditText editTextEditHora;
    protected EditText editTextEditMorada;
    protected EditText editTextEditDescricao;
    protected Button btnEditShedule;
    protected DbAdaptor dbAdaptor;
    protected Cursor cursor;
    protected Intent oIntent;
    protected int id;

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
        setContentView(R.layout.activity_edit_schedule);

        editTextEditNome = (EditText) findViewById(R.id.editTextEditNome);
        editTextEditData = (EditText) findViewById(R.id.editTextEditData);
        editTextEditHora = (EditText) findViewById(R.id.editTextEditHora);
        editTextEditMorada = (EditText) findViewById(R.id.editTextEditMorada);
        editTextEditDescricao = (EditText) findViewById(R.id.editTextEditDescricao);

        dbAdaptor = new DbAdaptor(this).open();
        oIntent = getIntent();
        id = oIntent.getExtras().getInt("_id");
        cursor = dbAdaptor.getScheduleId(id);

        if (cursor.moveToFirst()){
            editTextEditNome.setText(cursor.getString(1));
            editTextEditData.setText(cursor.getString(2));
            editTextEditHora.setText(cursor.getString(3));
            editTextEditMorada.setText(cursor.getString(4));
            editTextEditDescricao.setText(cursor.getString(5));
        }

        btnEditShedule = (Button)findViewById(R.id.btnEditSchedule);
        btnEditShedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextEditNome.getText().toString().equals("")||
                        editTextEditData.getText().toString().equals("")||
                        editTextEditHora.getText().toString().equals("")||
                        editTextEditMorada.getText().toString().equals("")||
                        editTextEditDescricao.getText().toString().equals("")){
                    Toast toast = Toast.makeText(EditSchedule.this, "Espaços não preenchidos", Toast.LENGTH_LONG);
                    toast.show();
                }
                else{
                    Boolean result = dbAdaptor.editSchedule(id, editTextEditNome.getText().toString(),
                            editTextEditData.getText().toString(),
                            editTextEditHora.getText().toString(),
                            editTextEditMorada.getText().toString(),
                            editTextEditDescricao.getText().toString());
                    if (result) {
                        Toast toast = Toast.makeText(EditSchedule.this, "Sucesso", Toast.LENGTH_LONG);
                        toast.show();

                        Intent intent = new Intent(EditSchedule.this, Schedules.class);
                        intent.putExtra("_id", id);
                        startActivity(intent);
                    }

                    else{
                        Toast toast = Toast.makeText(EditSchedule.this, "Erro detetado", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(EditSchedule.this, Schedules.class);
        intent.putExtra("_id", id);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
