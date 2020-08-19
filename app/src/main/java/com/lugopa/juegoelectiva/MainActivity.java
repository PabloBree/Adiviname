package com.lugopa.juegoelectiva;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // inicializar elementos UI
    private Button btn_iniciar;
    private Button btn_instrucciones;
    private Button btn_salir;
    private Button btn_configuracion;
    private Button btn_puntajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializar();

        btn_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        /*btnSeleccionarDatos!!.setOnClickListener() {   // PARA ABRIR ACTIVITY - PASAR A JAVA
            intent: Intent = Intent(this, SeleccionDatosCalculos::class.java)
            intent.putExtra("mUsuario", mUsuario) // paso el usuario de Firebase
            startActivity(intent)
            // Permite dirigirnos a la activity de la seleccion de los datos a mostrar
        }*/
    }

    private void inicializar(){
        btn_iniciar = findViewById(R.id.button_iniciar);
        btn_configuracion = findViewById(R.id.button_configuracion);
        btn_instrucciones = findViewById(R.id.button_instrucciones);
        btn_puntajes = findViewById(R.id.button_puntajes);
        btn_salir = findViewById(R.id.button_salir);
    }

    private void iniciarJuego(){
        // abre activity jugar
    }
    

}