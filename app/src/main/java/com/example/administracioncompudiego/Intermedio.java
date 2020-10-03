package com.example.administracioncompudiego;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Intermedio extends AppCompatActivity {

    Button nuevo, existente, consulta;
    EditText dniT;
    String dni;
    FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermedio_reg);

        dniT = (EditText) findViewById(R.id.queryDni);

        consulta = (Button)findViewById(R.id.consultaCliente);
        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dni = dniT.getText().toString();
                db = FirebaseFirestore.getInstance();
                db.collection("Clientes").document(dni).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            Toast.makeText(getApplicationContext(), "El cliente con dni: " + dni + " existe", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent (getApplicationContext(), NuevoRegistroExistente.class);
                            intent1.putExtra("dni", dni);
                            startActivityForResult(intent1, 1);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "No existe el cliente con dni " + dni, Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent (getApplicationContext(), NuevoRegistro.class);
                            intent1.putExtra("dni", dni);
                            startActivity(intent1);

                        }
                    }
                });

            }
        });

    }
}
