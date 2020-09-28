package com.lugopa.juegoelectiva;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

import com.lugopa.juegoelectiva.Model.VariablesGlobales;

public class ConfiguracionActivity extends AppCompatActivity {

    private Button btnFacil;
    private Button btnIntermedio;
    private Button btnDificil;

    private String dificultad;

    // Vibracion y sonido de botones
    private Vibrator vibe;
    int duracion = 80;
    private MediaPlayer soundMP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        inicializar();

        vibe = (Vibrator)ConfiguracionActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
        soundMP = MediaPlayer.create(this, R.raw.sonido_boton_click);

        btnFacil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundMP.start();
                vibe.vibrate(duracion);
                setEstadoBotones("facil");
                setDificultadGlobal("facil");
            }
        });

        btnIntermedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundMP.start();
                vibe.vibrate(duracion);
                setEstadoBotones("intermedio");
                setDificultadGlobal("intermedio");
            }
        });

        btnDificil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundMP.start();
                vibe.vibrate(duracion);
                setEstadoBotones("dificil");
                setDificultadGlobal("dificil");
            }
        });

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void inicializar(){
        btnFacil = findViewById(R.id.buttonFacil);
        btnIntermedio = findViewById(R.id.buttonIntermedio);
        btnDificil = findViewById(R.id.buttonDificil);
        dificultad = ((VariablesGlobales) this.getApplication()).getDificultad();
        setEstadoBotones(dificultad);
    }

    private void setEstadoBotones(String dificultad){
        switch (dificultad){
            case "intermedio":
                btnFacil.setClickable(true);
                btnFacil.setBackgroundResource(R.drawable.button_gray_rounded);

                btnIntermedio.setClickable(false);
                btnIntermedio.setBackgroundResource(R.drawable.button_green_rounded);

                btnDificil.setClickable(true);
                btnDificil.setBackgroundResource(R.drawable.button_gray_rounded);
                break;

            case "dificil":
                btnFacil.setClickable(true);
                btnFacil.setBackgroundResource(R.drawable.button_gray_rounded);

                btnIntermedio.setClickable(true);
                btnIntermedio.setBackgroundResource(R.drawable.button_gray_rounded);

                btnDificil.setClickable(false);
                btnDificil.setBackgroundResource(R.drawable.button_green_rounded);
                break;

            default: // seria el nivel FACIL
                btnFacil.setClickable(false);
                btnFacil.setBackgroundResource(R.drawable.button_green_rounded);

                btnIntermedio.setClickable(true);
                btnIntermedio.setBackgroundResource(R.drawable.button_gray_rounded);

                btnDificil.setClickable(true);
                btnDificil.setBackgroundResource(R.drawable.button_gray_rounded);
                break;
        }
    }

    private void setDificultadGlobal(String dif){
        ((VariablesGlobales) this.getApplication()).setDificultad(dif);
    }



}

