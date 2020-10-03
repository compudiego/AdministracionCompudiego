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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class NuevoRegistroExistente extends AppCompatActivity {

    EditText equipoT, marcaT, modeloT, fallaT, datosT, contraseñaT, observacionesT, snT;
    String equipo, marca, modelo, falla, datos, contraseña, observaciones, sn, dni, num;
    AlertDialog mDialog;
    Button guardar;
    FirebaseFirestore db;
    String valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_registro_existente);

        db = FirebaseFirestore.getInstance();

        equipoT = (EditText)findViewById(R.id.equipo);
        marcaT = (EditText)findViewById(R.id.marca);
        modeloT = (EditText)findViewById(R.id.modelo);
        fallaT = (EditText)findViewById(R.id.falla);
        datosT = (EditText)findViewById(R.id.datos);
        contraseñaT = (EditText)findViewById(R.id.contraseña);
        observacionesT = (EditText)findViewById(R.id.observaciones);
        snT = (EditText)findViewById(R.id.serie);
        mDialog = new SpotsDialog.Builder().setContext(NuevoRegistroExistente.this).setMessage("Registrando entrada").setCancelable(false).build();
        guardar = (Button)findViewById(R.id.guardar);

        db.collection("ID").document("numeros").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
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
                guardarNuevoRegistro();
            }
        });
    }

    private void guardarNuevoRegistro() {
        dni = getIntent().getExtras().getString("dni");

        equipo = equipoT.getText().toString();
        marca = marcaT.getText().toString();
        modelo = modeloT.getText().toString();
        falla = fallaT.getText().toString();
        datos = datosT.getText().toString();
        contraseña = contraseñaT.getText().toString();
        observaciones = observacionesT.getText().toString();
        sn = snT.getText().toString();

        String fechita = getFechaActual();

        final Map<String, Object> map = new HashMap<>();
        map.put("equipo", equipo);
        map.put("marca", marca);
        map.put("modelo", modelo);
        map.put("falla", falla);
        map.put("id", num);
        map.put("datos", datos);
        map.put("contraseña",contraseña);
        map.put("observaciones",observaciones);
        map.put("sn",sn);
        map.put("DNI",dni);
        map.put("fecha", fechita);
        DocumentReference washingtonRef = db.collection("Clientes").document(dni);

        washingtonRef.update(num, num).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully updated!");
                        db.collection("Equipos").document(num).set(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("TAG", "DocumentSnapshot successfully written!");

                                        mDialog.dismiss();

                                        AlertDialog alertDialog = new AlertDialog.Builder(NuevoRegistroExistente.this).create();
                                        alertDialog.setTitle("Nueva Entrada");
                                        alertDialog.setMessage("Entrada Registrada con el id: " + num);
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

                        sumarId();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "EL CLIENTE NO SE ENCUENTRA REGISTRADO", Toast.LENGTH_SHORT).show();
                        Log.w("TAG", "Error updating document", e);
                    }
                });


    }

    private void sumarId() {
        int numEntero = Integer.parseInt(num);
        numEntero+=1;
        String numCadena= Integer.toString(numEntero);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("num", numCadena);
        db.collection("ID").document("numeros").set(map2)
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

    public static String getFechaActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        return formateador.format(ahora);
    }

}
