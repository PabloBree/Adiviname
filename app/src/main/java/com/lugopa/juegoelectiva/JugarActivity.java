package com.lugopa.juegoelectiva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;


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

                int numero = Integer.valueOf(String.valueOf(arrayNumeros[0])+String.valueOf(arrayNumeros[1])+String.valueOf(arrayNumeros[2])+String.valueOf(arrayNumeros[3]));

                tvNumeros.setText(Integer.toString(numero));
            }
        });
    }



    /*
    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        tvShowNumbers.setText("Old Number= "+i+" New Number = "+i1);
    }*/



}
