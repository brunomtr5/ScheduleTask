package com.example.bruno.scheduletask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddSchedule extends Activity {

    protected EditText editTextNome;
    protected EditText editTextData;
    protected EditText editTextHora;
    protected EditText editTextMorada;
    protected EditText editTextDescricao;
    protected Button btnAddSchedule;
    protected DbAdaptor dbAdaptor;

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
        setContentView(R.layout.activity_add_schedule);

        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextData = (EditText) findViewById(R.id.editTextData);
        editTextHora = (EditText) findViewById(R.id.editTextHora);
        editTextMorada = (EditText) findViewById(R.id.editTextMorada);
        editTextDescricao = (EditText) findViewById(R.id.editTextDescricao);
        btnAddSchedule = (Button) findViewById(R.id.btnAddSchedule);

        btnAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextNome.getText().toString().equals("") ||
                        editTextData.getText().toString().equals("") ||
                        editTextHora.getText().toString().equals("") ||
                        editTextMorada.getText().toString().equals("") ||
                        editTextDescricao.getText().toString().equals("")){
                    Toast toast = Toast.makeText(AddSchedule.this, "Espaços não preenchidos", Toast.LENGTH_LONG);
                }
                else{
                    Boolean checkError = dbAdaptor.putSchedule(editTextNome.getText().toString(),
                            editTextData.getText().toString(),
                            editTextHora.getText().toString(),
                            editTextMorada.getText().toString(),
                            editTextDescricao.getText().toString());

                    if (checkError){
                        Toast toast = Toast.makeText(AddSchedule.this, "Sucesso", Toast.LENGTH_LONG);
                        toast.show();

                        Intent intent = new Intent(AddSchedule.this, MainActivity.class);
                        startActivity(intent);
                    }

                    else {
                        Toast toast = Toast.makeText(AddSchedule.this, "Erro detetado", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddSchedule.this, MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
