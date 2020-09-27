package com.example.administracioncompudiego;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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

public class Presupuestos extends AppCompatActivity {
    FirebaseFirestore db;
    TextView insert;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presupuestos);
        insert = (TextView)findViewById(R.id.insert);

        db = FirebaseFirestore.getInstance();
        traerPresupuestos();

    }


    public void traerPresupuestos(){
        db.collection("Presupuestos").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    insert.setText("                               PRESUPUESTOS\n__________________________________________________");
                    for (DocumentSnapshot d : list) {
                        String id = d.getString("id");
                        String diagnostico = d.getString("diagnostico");
                        String presupuesto = d.getString("presupuesto");
                        String fecha = d.getString("fecha");
                        String fechaRetiro = d.getString("fechaRetiro");

                        insert.setText(insert.getText() + "\nID: " + id + "\nDiagnóstico: " + diagnostico + "\nPresupuesto: " + presupuesto + "\nFecha de ingreso: " + fecha + "\nFecha de Retiro: " + fechaRetiro +"\n______________________________________");
                    }
                }
            }
        });

    }

}



