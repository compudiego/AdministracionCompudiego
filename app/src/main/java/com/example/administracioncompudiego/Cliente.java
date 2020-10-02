package com.example.administracioncompudiego;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class Cliente extends AppCompatActivity {
    EditText nameT, phoneT, dniT;
    String name, phone, dni;
    Button guardar;
    AlertDialog mDialog;
    FirebaseFirestore db;







    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_cliente);

        db = FirebaseFirestore.getInstance();
        nameT = (EditText)findViewById(R.id.name);
        phoneT = (EditText)findViewById(R.id.phone);
        dniT = (EditText)findViewById(R.id.dniCliente);
        mDialog = new SpotsDialog.Builder().setContext(Cliente.this).setMessage("Registrando Cliente").setCancelable(false).build();
        guardar = (Button)findViewById(R.id.guardar);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                name = nameT.getText().toString();
                phone = phoneT.getText().toString();
                dni = dniT.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debe ingresar un nombre", Toast.LENGTH_SHORT).show();

                } else if (dni.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debe ingresar un DNI", Toast.LENGTH_SHORT).show();
                }
                else {
                    mDialog.show();
                    Map<String, Object> map = new HashMap<>();
                    map.put("Nombre", name);
                    map.put("telefono", phone);
                    map.put("dni", dni);

                    db.collection("Clientes").document(dni).set(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "DocumentSnapshot successfully written!");
                                    mDialog.dismiss();

                                    AlertDialog alertDialog = new AlertDialog.Builder(Cliente.this).create();
                                    alertDialog.setTitle("NuevO Cliente");
                                    alertDialog.setMessage("Cliente Registrado con el DNI: " + dni);
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                                                    startActivityForResult(intent1, 1);
                                                    dialog.dismiss();
                                                }
                                            });
                                    alertDialog.show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    mDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                    Log.w("TAG", "Error writing document", e);
                                }
                            });
            }
            }
        });


    }
}
