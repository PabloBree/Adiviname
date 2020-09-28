package com.lugopa.juegoelectiva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lugopa.juegoelectiva.Model.Puntaje;

import java.util.ArrayList;

public class PuntajesActivity extends AppCompatActivity {

    private Button btnSalir;
    private DatabaseReference bdReference;

    ListView listView;
    private ArrayList<String> lista_puntajes = new ArrayList();
    ArrayAdapter<String> adapter;

    String dificultad;
    String nombre;
    String puntaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntajes);

        btnSalir = findViewById(R.id.button_salir_puntajes);

        // Manejo de la lista.....
        listView = findViewById(R.id.list_view_puntajes);
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, lista_puntajes);

        lista_puntajes.add(" NOMBRE - DIFICULTAD - PUNTAJE");
        listView.setAdapter(adapter);

        obtenerListaBD();

    }



    private void obtenerListaBD(){

        DatabaseReference nodoUsuarios = FirebaseDatabase.getInstance().getReference().child("Usuarios");

        nodoUsuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String nombre;
                    String dificultad;
                    String puntaje;

                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        Puntaje p = ds.getValue(Puntaje.class);
                        dificultad = p.getDificultad();
                        nombre = p.getNombre();
                        puntaje = p.getPuntaje();

                        lista_puntajes.add(nombre + " - " + dificultad +" - "+ puntaje);
                        listView.setAdapter(adapter);
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ERROR REC. FIREBASE ", "ERROR - No se pudieron recuperar los datos de Firebase");
                //progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}