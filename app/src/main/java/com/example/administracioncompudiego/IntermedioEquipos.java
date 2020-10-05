package com.example.administracioncompudiego;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class IntermedioEquipos extends AppCompatActivity {

    Button porDni, porId, todos;
    EditText dniT, idT;
    String dni, id, presupuesto2;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    AlertDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_int_consulta_equipos);

        dniT = (EditText) findViewById(R.id.porDni);
        idT = (EditText) findViewById(R.id.porId);


        todos = (Button)findViewById(R.id.todosEquipos);
        todos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent (v.getContext(), Equipos.class);
                startActivityForResult(intent1, 1);
                    }
                });

        porDni = (Button)findViewById(R.id.buttonPorDni);
        porDni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dni = dniT.getText().toString();
                db.collection("Clientes").document(dni).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            Toast.makeText(getApplicationContext(), "El cliente con dni: " + dni + " existe", Toast.LENGTH_SHORT).show();
                            String name = documentSnapshot.getString("nombre");
                            Intent intent1 = new Intent (getApplicationContext(), NuevoRegistroExistente.class);
                            intent1.putExtra("dni", dni);
                            intent1.putExtra("name", name);
                            startActivity(intent1);
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

        porId = (Button)findViewById(R.id.buttonPorId);
        porId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id = idT.getText().toString();

                db.collection("Equipos").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            String nombre = documentSnapshot.getString("nombre");
                            String telefono = documentSnapshot.getString("telefono");
                            String fecha = documentSnapshot.getString("fecha");
                            String falla = documentSnapshot.getString("falla");
                            String marca = documentSnapshot.getString("marca");
                            String modelo = documentSnapshot.getString("modelo");
                            presupuesto2 = "\nID: " + id + "\nNombre: " + nombre + "\nTelefono: " + telefono + "\nFecha de ingreso: " + fecha + "\nFalla: " + falla + "\nMarca: " + marca + "\nModelo: " + modelo;
                        }
                        else{
                            presupuesto2 = "No existe equipo "+id;
                        }
                    }
                });
                AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
                if(presupuesto2 == null){

                }
                else {
                    alertDialog.setTitle("Equipo");
                    alertDialog.setMessage(presupuesto2);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });
                    alertDialog.show();
                }
            }
        });

    }

}
