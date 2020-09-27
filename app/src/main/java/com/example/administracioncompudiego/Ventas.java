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

public class Ventas extends AppCompatActivity {
    FirebaseFirestore db;
    TextView insert;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);
        insert = (TextView)findViewById(R.id.insert);

        db = FirebaseFirestore.getInstance();
        traerPresupuestos();

    }


    public void traerPresupuestos(){
        db.collection("Ventas").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    insert.setText("                               Ventas\n__________________________________________________");
                    for (DocumentSnapshot d : list) {
                        String id = d.getString("id");
                        String producto = d.getString("producto");
                        String precio = d.getString("precio");
                        String fecha = d.getString("fecha");
                        String medio = d.getString("medio");

                        insert.setText(insert.getText() + "\nID: " + id + "\nProducto: " + producto + "\nPrecio: " + precio + "\nFecha: " + fecha + "\nMedio de pago: " + medio +"\n______________________________________");
                    }
                }
            }
        });

    }

}



