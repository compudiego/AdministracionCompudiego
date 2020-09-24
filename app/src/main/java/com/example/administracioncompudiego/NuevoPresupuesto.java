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

public class NuevoPresupuesto extends AppCompatActivity {

    EditText idT, diagnosticoT, repuestoT, gastoT, presupuestoT, señaT, fechaRetiroT, garantiaT, codGarantiaT;

    String id, diagnostico, repuesto, gasto, presupuesto, seña, estado, fechaRetiro, garantia, codGarantia;
    AlertDialog mDialog;

    Button guardar;
    FirebaseFirestore db;
    //String num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_presupuesto);

        db = FirebaseFirestore.getInstance();

        idT = (EditText)findViewById(R.id.id);
        diagnosticoT = (EditText)findViewById(R.id.diagnostico);
        repuestoT = (EditText)findViewById(R.id.repuesto);
        gastoT = (EditText)findViewById(R.id.gasto);
        presupuestoT = (EditText)findViewById(R.id.presupuesto);
        señaT = (EditText)findViewById(R.id.seña);
        fechaRetiroT = (EditText)findViewById(R.id.fecha);
        garantiaT = (EditText)findViewById(R.id.garantia);
        codGarantiaT = (EditText)findViewById(R.id.codigoFactura);

        mDialog = new SpotsDialog.Builder().setContext(NuevoPresupuesto.this).setMessage("Registrando presupuesto").setCancelable(false).build();

        guardar = (Button)findViewById(R.id.guardar);

        /*db.collection("ID").document("numeros").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    num = documentSnapshot.getString("num");
                }
                else{

                }
            }
        });*/

        guardar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mDialog.show();

                id = idT.getText().toString();
                diagnostico = diagnosticoT.getText().toString();
                repuesto = repuestoT.getText().toString();
                gasto = gastoT.getText().toString();
                presupuesto = presupuestoT.getText().toString();
                seña = señaT.getText().toString();
                fechaRetiro = fechaRetiroT.getText().toString();
                garantia = garantiaT.getText().toString();
                codGarantia = codGarantiaT.getText().toString();

                String fechaActual = getFechaActual();

                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("fecha", fechaActual);
                map.put("diagnostico", diagnostico);
                map.put("repuesto", repuesto);
                map.put("gasto", gasto);
                map.put("presupuesto", presupuesto);
                map.put("seña", seña);
                map.put("fechaRetiro", fechaRetiro);
                map.put("garantia",garantia);
                map.put("Codigo de garantia", codGarantia);

                db.collection("Presupuestos").document(id).set(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "DocumentSnapshot successfully written!");
                                mDialog.dismiss();

                                AlertDialog alertDialog = new AlertDialog.Builder(NuevoPresupuesto.this).create();
                                alertDialog.setTitle("Nueva Entrada");
                                alertDialog.setMessage("Presupuesto Registrado con el id: " + id);
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

            }

        });

    }
    public static String getFechaActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        return formateador.format(ahora);
    }




}
