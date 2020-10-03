package com.example.administracioncompudiego;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Intermedio extends AppCompatActivity {

    Button nuevo, existente, consulta;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermedio_reg);

        nuevo = (Button)findViewById(R.id.clienteNuevo);
        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent (v.getContext(), NuevoRegistro.class);
                startActivityForResult(intent1, 1);
            }
        });
        existente = (Button)findViewById(R.id.clienteExistente);
        existente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent (v.getContext(), MainActivity.class);
                startActivityForResult(intent1, 1);
            }
        });
        consulta = (Button)findViewById(R.id.consultaCliente);
        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent (v.getContext(), MainActivity.class);
                startActivityForResult(intent1, 1);
            }
        });

    }
}
