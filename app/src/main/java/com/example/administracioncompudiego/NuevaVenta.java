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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class NuevaVenta extends AppCompatActivity {

    EditText productoT, precioT, codigoT, medioT;

    String producto, precio, codigo, medio;
    AlertDialog mDialog;

    Button guardar;
    FirebaseFirestore db;
    String num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_venta);

        db = FirebaseFirestore.getInstance();

        productoT = (EditText)findViewById(R.id.producto);
        precioT = (EditText)findViewById(R.id.precio);
        codigoT = (EditText)findViewById(R.id.codigo);
        medioT = (EditText)findViewById(R.id.medio);

        mDialog = new SpotsDialog.Builder().setContext(NuevaVenta.this).setMessage("Registrando venta").setCancelable(false).build();

        guardar = (Button)findViewById(R.id.guardar);

        db.collection("ID").document("idVentas").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    num = documentSnapshot.getString("num");
                }
                else{

                }
            }
        });

        guardar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mDialog.show();

                producto = productoT.getText().toString();
                precio = precioT.getText().toString();
                codigo = codigoT.getText().toString();
                medio = medioT.getText().toString();


                String fechaActual = getFechaActual();

                Map<String, Object> map = new HashMap<>();
                map.put("producto", producto);
                map.put("id", num);
                map.put("fecha", fechaActual);
                map.put("precio", precio);
                map.put("codigo", codigo);
                map.put("medio", medio);

                db.collection("Ventas").document(num).set(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "DocumentSnapshot successfully written!");
                                mDialog.dismiss();

                                AlertDialog alertDialog = new AlertDialog.Builder(NuevaVenta.this).create();
                                alertDialog.setTitle("Nueva Venta");
                                alertDialog.setMessage("Venta Registrada con el id: " + num);
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent1 = new Intent (getApplicationContext(), MainActivity.class);
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
                int numEntero = Integer.parseInt(num);
                numEntero+=1;
                String numCadena= Integer.toString(numEntero);
                Map<String, Object> map2 = new HashMap<>();
                map2.put("num", numCadena);
                db.collection("ID").document("idVentas").set(map2)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                mDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Error2", Toast.LENGTH_SHORT).show();

                                Log.w("TAG", "Error writing document", e);
                            }
                        });

            }

        });

    }
    public static String getFechaActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        return formateador.format(ahora);
    }




}
