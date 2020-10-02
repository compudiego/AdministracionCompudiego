package com.example.administracioncompudiego;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {

    Button nuevo, nuevoPresupuesto, consulta, nuevaVenta, resetear, cliente;
    String num;
    AlertDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("ID").document("acumulado").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    num = documentSnapshot.getString("num");
                }
                else{

                }
            }
        });
        cliente = (Button)findViewById(R.id.button0);
        cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent (v.getContext(), Cliente.class);
                startActivityForResult(intent1, 1);
            }
        });

        nuevoPresupuesto = (Button)findViewById(R.id.button3);
        nuevoPresupuesto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent (v.getContext(), NuevoPresupuesto.class);
                startActivityForResult(intent1, 1);
            }
        });
        consulta = (Button)findViewById(R.id.button2);
        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent (v.getContext(), Consulta.class);
                startActivityForResult(intent1, 1);
            }
        });

        nuevo = (Button)findViewById(R.id.button);
        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent (v.getContext(), NuevoRegistro.class);
                startActivityForResult(intent1, 1);
            }
        });
        nuevaVenta = (Button)findViewById(R.id.button4);
        nuevaVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent (v.getContext(), NuevaVenta.class);
                startActivityForResult(intent1, 1);
            }
        });
        resetear = (Button)findViewById(R.id.button5);
        resetear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reseteo(num);
            }
        });
    }

    private void reseteo(String num) {
        final FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        mDialog = new SpotsDialog.Builder().setContext(MainActivity.this).setMessage("Reseteando Datos").setCancelable(false).build();
        mDialog.show();


        final Map<String, Object> map = new HashMap<>();
        map.put("Ganancia", num);

        final Map<String, Object> map2 = new HashMap<>();
        map2.put("num", "0");

        final String fechita = getFechaActual();

        db.collection("EntradasReparaciones").document(fechita).set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully written!");

                        mDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Mes reseteado", Toast.LENGTH_SHORT).show();

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

        db.collection("ID").document("acumulado").set(map2)
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
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

                        Log.w("TAG", "Error writing document", e);
                    }
                });


    }
    public static String getFechaActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        return formateador.format(ahora);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}