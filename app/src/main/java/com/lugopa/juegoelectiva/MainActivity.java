package com.lugopa.juegoelectiva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializar(); // obtengo las vistas de UI

        btn_jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrir_jugar();
            }
        });

        btn_puntajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrir_puntajes();
            }
        });

        btn_instrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrir_instrucciones();
            }
        });

        btn_configuracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrir_configuracion();
            }
        });

        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //salir();
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
    }

    private void abrir_instrucciones(){
        // abre activity instrucciones
        Intent intent = new Intent(this, InstruccionesActivity.class);
        //intent.putExtra( lo que queramos pasar) // para pasar cosas a la activity
        startActivity(intent);
    }

    private void abrir_configuracion(){
        // abre activity configuracion
        Intent intent = new Intent(this, ConfiguracionActivity.class);
        //intent.putExtra( lo que queramos pasar) // para pasar cosas a la activity
        startActivity(intent);
    }

    private void abrir_puntajes(){
        // abre activity puntajes
        Intent intent = new Intent(this, PuntajesActivity.class);
        //intent.putExtra( lo que queramos pasar) // para pasar cosas a la activity
        startActivity(intent);
    }

    private void salir(){
        // DEBERIA CERRAR LA APP o la sesion
    }
    

}