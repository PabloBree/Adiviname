package com.lugopa.juegoelectiva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    // inicializar elementos UI
    private Button btn_jugar;
    private Button btn_instrucciones;
    private Button btn_salir;
    private Button btn_configuracion;
    private Button btn_puntajes;

    // Vibracion y sonido de botones
    private Vibrator vibe;
    int duracion = 80;
    private MediaPlayer soundMP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializar(); // obtengo las vistas de UI

        vibe = (Vibrator)MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
        soundMP = MediaPlayer.create(this, R.raw.sonido_boton_click);

        btn_jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundMP.start();
                vibe.vibrate(duracion);
                abrir_jugar();
            }
        });

        btn_puntajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundMP.start();
                vibe.vibrate(duracion);
                abrir_puntajes();
            }
        });

        btn_instrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundMP.start();
                vibe.vibrate(duracion);
                abrir_instrucciones();
            }
        });

        btn_configuracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundMP.start();
                vibe.vibrate(duracion);
                abrir_configuracion();
            }
        });

        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundMP.start();
                vibe.vibrate(duracion);
                finish();
                System.exit(0);
            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void inicializar(){
        btn_jugar = findViewById(R.id.button_iniciar);
        btn_configuracion = findViewById(R.id.button_configuracion);
        btn_instrucciones = findViewById(R.id.button_instrucciones);
        btn_puntajes = findViewById(R.id.button_puntajes);
        btn_salir = findViewById(R.id.button_salir);
    }

    private void abrir_jugar(){
        // abre activity jugar
        Intent intent = new Intent(this, JugarActivity.class);
        //intent.putExtra( lo que queramos pasar) // para pasar cosas a la activity
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void abrir_instrucciones(){
        // abre activity instrucciones
        Intent intent = new Intent(this, InstruccionesActivity.class);
        //intent.putExtra( lo que queramos pasar) // para pasar cosas a la activity
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void abrir_configuracion(){
        // abre activity configuracion
        Intent intent = new Intent(this, ConfiguracionActivity.class);
        //intent.putExtra( lo que queramos pasar) // para pasar cosas a la activity
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void abrir_puntajes(){
        // abre activity puntajes
        Intent intent = new Intent(this, PuntajesActivity.class);
        //intent.putExtra( lo que queramos pasar) // para pasar cosas a la activity
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


}