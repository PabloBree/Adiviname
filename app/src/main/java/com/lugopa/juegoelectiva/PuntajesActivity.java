package com.lugopa.juegoelectiva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
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
    private ArrayList<Puntaje> lista_puntajes = new ArrayList<>();
    //ArrayAdapter<Puntaje> adapter;
    //ADAPTER PERSONALIZADO
    PersonListAdapter adapter;

    String dificultad;
    String nombre;
    String puntaje;

    private Vibrator vibe;
    int duracion = 80;
    private MediaPlayer soundMP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntajes);

        vibe = (Vibrator)PuntajesActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
        soundMP = MediaPlayer.create(this, R.raw.sonido_boton_click);

        btnSalir = findViewById(R.id.button_salir_puntajes);


        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundMP.start();
                vibe.vibrate(duracion);
                onBackPressed();
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        // Manejo de la lista.....
        listView = findViewById(R.id.list_view_puntajes);
       // adapter = new ArrayAdapter<Puntaje>(getApplicationContext(), android.R.layout.simple_list_item_1, lista_puntajes);
        adapter = new PersonListAdapter(getApplicationContext(),R.layout.adapter_puntajes_layout,lista_puntajes);
        obtenerListaBD();

    }



    private void obtenerListaBD(){
        DatabaseReference nodoUsuarios = FirebaseDatabase.getInstance().getReference().child("Usuarios");
        nodoUsuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
//                    String nombre;
//                    String dificultad;
//                    String puntaje;
                    // OBTENEMOS LOS DATOS DEL PUNTAJE DE LA BASE DE DATOS
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        Puntaje p = ds.getValue(Puntaje.class);
//                        dificultad = p.getDificultad();
//                        nombre = p.getNombre();
//                        puntaje = p.getPuntaje();

                        lista_puntajes.add(p);
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