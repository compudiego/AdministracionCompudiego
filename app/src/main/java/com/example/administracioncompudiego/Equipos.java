package com.example.administracioncompudiego;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Equipos extends AppCompatActivity {
    FirebaseFirestore db;
    EditText insert;
    Button pres;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipos);
        db = FirebaseFirestore.getInstance();
        traerEquipos();
       // insert = (EditText)findViewById(R.id.insert);

    }


    public void traerEquipos(){
        db.collection("Equipos").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                   // insert.setText("                               EQUIPOS\n__________________________________________________");
                    for (DocumentSnapshot d : list) {
                        LinearLayout botonera = (LinearLayout) findViewById(R.id.botonera);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT );
                        Button botonazo = new Button(Equipos.this);
                        botonazo.setLayoutParams(lp);
                        EditText insert = new EditText(Equipos.this);
                        insert.setLayoutParams(lp);
                        insert.setLayoutParams(lp);


                        String nombre = d.getString("nombre");
                        String id = d.getString("id");
                        String equipo = d.getString("equipo");
                        String falla = d.getString("falla");
                        String marca = d.getString("marca");
                        String modelo = d.getString("modelo");
                        String contraseña = d.getString("contraseña");
                        String datos = d.getString("datos");
                        String obs = d.getString("observaciones");
                        insert.setText(insert.getText() + "\nNombre: " + nombre + "\nID: " + id + "\nEquipo: " + equipo + "\nFalla: " + falla + "\nMarca: " + marca + "\nModelo: " + modelo + "\nContraseña: " + contraseña + "\nDatos: " + datos + "\nObservaciones: " + obs);
                        botonazo.setText("Ver presupuesto N°" + d.getString("id"));
                        botonera.addView(botonazo);
                        botonera.addView(insert);

                        botonazo.setOnClickListener(new ButtonsOnClickListener(id));
                    }

                }

            }
        });

    }

}

class ButtonsOnClickListener implements View.OnClickListener
{
    String id;
    String presupuesto2 = " ";
    public ButtonsOnClickListener (String id){
        this.id = id;

}
    @Override
    public void onClick(View v)
    {
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("Presupuestos").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String diagnostico = documentSnapshot.getString("diagnostico");
                    String presupuesto = documentSnapshot.getString("presupuesto");
                    String fecha = documentSnapshot.getString("fecha");
                    String fechaRetiro = documentSnapshot.getString("fechaRetiro");

                    presupuesto2 = "\nID: " + id + "\nDiagnóstico: " + diagnostico + "\nPresupuesto: " + presupuesto + "\nFecha de ingreso: " + fecha + "\nFecha de Retiro: " + fechaRetiro;
                }
                else{
                    presupuesto2 = "No existe presupuesto para el equipo "+id;
                }
            }
        });
        AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
        if(presupuesto2.equals(" ")){

        }
        else{
            alertDialog.setTitle("Presupuestos");
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

};


