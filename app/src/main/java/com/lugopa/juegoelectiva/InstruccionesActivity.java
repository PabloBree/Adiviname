package com.lugopa.juegoelectiva;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InstruccionesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrucciones);

        Button btn_regresar = findViewById(R.id.button_regresasr_instrucciones);
        TextView tv_instrucciones = findViewById(R.id.textView_instrucciones);
        tv_instrucciones.setText("Adiviname es un juego donde tienes que utilizar tu logica para adivinar un numero secreto de 4 digitos que nuestro sistema escoge al comienzo de la partida. " +
                "El numero esta formado por digitos del 0 al 9 pero debe cumplir con las siguientes restricciones: \n " +
                "1) No puede comenzar con el numero '0' (cero)\n " +
                "2) No se puede repetir ningun digito\n " +
                "El objetivo entonces sera el de adivinar el numero en la menor cantidad de intentos y prestando atencion a los mensajes que te indicaran cuantos numeros CORRECTOS hay " +
                "(su valor y su posicion estan bien) y cuantos numeros REGULARES hay (su valor esta bien pero su posicion no) y ademas te mostrara un registro de tus intentos pevios\n " +
                "Mucha suerte y a poner a trabajar esas neuronas!!!");

        btn_regresar.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view){
                onBackPressed();
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


    }
}