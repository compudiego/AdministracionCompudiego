package com.example.administracioncompudiego;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class Consulta extends AppCompatActivity {

    Button clientes, equipos, presupuestos, ventas, mensual;
    FirebaseFirestore db;
    String hola;
    String num;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        db = FirebaseFirestore.getInstance();

        clientes = (Button)findViewById(R.id.clientes);
        equipos = (Button)findViewById(R.id.equipos);
        presupuestos = (Button)findViewById(R.id.presupuestos);
        ventas = (Button)findViewById(R.id.ventas);
        mensual = (Button)findViewById(R.id.mensual);

        clientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent (v.getContext(), MainActivity.class);
                startActivityForResult(intent1, 1);
            }
        });
        equipos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent (v.getContext(), Equipos.class);
                startActivityForResult(intent1, 1);
            }
        });
        presupuestos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent (v.getContext(), Presupuestos.class);
                startActivityForResult(intent1, 1);
            }
        });
        ventas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent (v.getContext(), Ventas.class);
                startActivityForResult(intent1, 1);
            }
        });
        mensual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FirebaseFirestore db;
                db = FirebaseFirestore.getInstance();
                db.collection("ID").document("acumulado").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            num = documentSnapshot.getString("num");
                            Toast.makeText(getApplicationContext(), num, Toast.LENGTH_SHORT).show();

                        }
                        else{

                        }
                    }
                });

            }
        });



    }



}
