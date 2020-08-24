package com.lugopa.juegoelectiva;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.TextView;


public class JugarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugar);

        NumberPicker numberPicker = findViewById(R.id.numberPicker0);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(9);

        NumberPicker numberPicker1 = findViewById(R.id.numberPicker1);
        numberPicker1.setMinValue(0);
        numberPicker1.setMaxValue(9);

        NumberPicker numberPicker2 = findViewById(R.id.numberPicker2);
        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(9);

        NumberPicker numberPicker3 = findViewById(R.id.numberPicker3);
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

    /*
    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        tvShowNumbers.setText("Old Number= "+i+" New Number = "+i1);
    }*/



}
