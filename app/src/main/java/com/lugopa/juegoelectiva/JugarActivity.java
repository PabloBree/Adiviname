package com.lugopa.juegoelectiva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.List;
import java.util.Random;


public class JugarActivity extends AppCompatActivity {

    private Button btnIntento;
    private TextView tvNumeros;
    private NumberPicker numberPicker;
    private NumberPicker numberPicker1;
    private NumberPicker numberPicker2;
    private NumberPicker numberPicker3;
    private int[] arrayNumeros = new int[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugar);

        inicializar();

        btnIntento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oprimir_boton();
            }
        });

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(9);

        numberPicker1.setMinValue(0);
        numberPicker1.setMaxValue(9);

        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(9);

        numberPicker3.setMinValue(0);
        numberPicker3.setMaxValue(9);

        // to change formate of number in numberpicker
        numberPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%1d", i);
            }
        });

        numberPicker1.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%1d", i);
            }
        });

        numberPicker2.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%1d", i);
            }
        });

        numberPicker3.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%1d", i);
            }
        });

        //numberPicker.setOnValueChangedListener(this);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void inicializar(){
      btnIntento = findViewById(R.id.button_Intento);
      tvNumeros = findViewById(R.id.textViewNumeros);

      //Numero Pickers definition
      numberPicker = findViewById(R.id.numberPicker0);
      numberPicker1 = findViewById(R.id.numberPicker1);
      numberPicker2 = findViewById(R.id.numberPicker2);
      numberPicker3 = findViewById(R.id.numberPicker3);
    }

    private void oprimir_boton(){
        btnIntento.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                arrayNumeros[0]= numberPicker.getValue();
                arrayNumeros[1]= numberPicker1.getValue();
                arrayNumeros[2]= numberPicker2.getValue();
                arrayNumeros[3]= numberPicker3.getValue();

                int numero = Integer.parseInt(String.valueOf(arrayNumeros[0])+String.valueOf(arrayNumeros[1])+String.valueOf(arrayNumeros[2])+String.valueOf(arrayNumeros[3]));
                String str_num = Integer.toString(numero);
                if(validar_numero(str_num)){
                    tvNumeros.setText(str_num);
                }else {
                    tvNumeros.setText("Error - Digitos repetidos");
                }

            }
        });
    }

    private boolean validar_numero(String numero){
        // Iterate over characters of a String
        // using simple for loop
        char digito;
        boolean es_valido = true;
        for (int i = 0; i < numero.length(); i++) {
             digito = numero.charAt(i);
            for(int j = i+1; j < numero.length(); j++){
                if(digito == numero.charAt((j))){
                    es_valido = false;
                    break;
                }
            }
            if(!es_valido) {
                break;
            }
        }
        return es_valido;
    }

    private boolean son_iguales(int a, int b){
        return a == b; // retorna True o False
    }

//    private int generar_numero(){  # Tiene que estar entre 1023 y 9876 con extremos incluidos
//
//    }

//    private boolean es_correcto(){
//
//    }


//    private List<Integer> actualizar_lista_intentos(int numero, List<Integer> lista){
//
//    }


    /*
    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        tvShowNumbers.setText("Old Number= "+i+" New Number = "+i1);
    }*/

    private int getRandomNumber(int min,int max) {
        return (new Random()).nextInt((max - min) + 1) + min;
    }


}
