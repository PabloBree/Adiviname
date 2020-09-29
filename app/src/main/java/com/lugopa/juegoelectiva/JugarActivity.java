package com.lugopa.juegoelectiva;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lugopa.juegoelectiva.Model.Puntaje;
import com.lugopa.juegoelectiva.Model.VariablesGlobales;


public class JugarActivity extends AppCompatActivity {

    private Button btnIntento;
    private Button btnAbandonar;
    private Button btnJugarDeNuevo;
    private TextView tvNumeros;
    private TextView numeroadivinado;
    private TextView nombreIngresado;
    private NumberPicker numberPicker;
    private NumberPicker numberPicker1;
    private NumberPicker numberPicker2;
    private NumberPicker numberPicker3;
    private int[] arrayNumeros = new int[4];

    private int contador_intentos = 0;
    private int puntuacion;
    private String dificultadGlobal;

    // numero random
    String numero_random;

    ListView listView;
    private ArrayList<String> lista_intentos = new ArrayList();
    private ArrayList<String> lista_intentos_descripcion = new ArrayList();
    ArrayAdapter<String> adapter;

    // Vibracion y sonido de botones
    private Vibrator vibe;
    int duracion = 80;
    private MediaPlayer soundMP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugar);

        inicializar();

        vibe = (Vibrator)JugarActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
        soundMP = MediaPlayer.create(this, R.raw.sonido_boton_click);

        btnIntento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oprimir_boton_Intentar();
            }
        });

        btnAbandonar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oprimir_boton_Abandonar();
            }
        });

        btnJugarDeNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jugarDeNuevo();
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
        btnAbandonar = findViewById(R.id.button_Abandonar);
        btnJugarDeNuevo = findViewById(R.id.button_JugarDeNuevo);
        tvNumeros = findViewById(R.id.textViewNumeros);

        //Numero Pickers definition
        numberPicker = findViewById(R.id.numberPicker0);
        numberPicker1 = findViewById(R.id.numberPicker1);
        numberPicker2 = findViewById(R.id.numberPicker2);
        numberPicker3 = findViewById(R.id.numberPicker3);

        numero_random = generar_numeroRandom(); // asignamos el numero random que debe adivinarse
        tvNumeros.setText("Generado = "+ numero_random);

        // Manejo de la lista.....
        listView = findViewById(R.id.list_view_puntajes);
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, lista_intentos_descripcion);

        //variable global de dificultad
        dificultadGlobal = ((VariablesGlobales) this.getApplication()).getDificultad();
    }

    private void oprimir_boton_Intentar(){
        soundMP.start();
        vibe.vibrate(duracion);
        arrayNumeros[0]= numberPicker.getValue();
        arrayNumeros[1]= numberPicker1.getValue();
        arrayNumeros[2]= numberPicker2.getValue();
        arrayNumeros[3]= numberPicker3.getValue();

        int numero = Integer.parseInt(String.valueOf(arrayNumeros[0])+String.valueOf(arrayNumeros[1])+String.valueOf(arrayNumeros[2])+String.valueOf(arrayNumeros[3]));
        String str_num = Integer.toString(numero);

        analizar_intento(str_num, numero_random);
    }

    private void oprimir_boton_Abandonar(){
        soundMP.start();
        vibe.vibrate(duracion);
        mostrarDialogAbandonar();
    }

    private void mostrarDialogAbandonar(){

        AlertDialog.Builder builder = new AlertDialog.Builder(JugarActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_abandonar,null);
        builder.setView(view).setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.show();

        numeroadivinado = view.findViewById(R.id.text_numero_adivinado); //Hizo falta aclarar la view para que no rompa, se mezclaban las vistas y rompía
        numeroadivinado.setText("El número era "+ numero_random);

        Button btn_jugardevuelta = view.findViewById(R.id.btn_jugardevuelta);
        Button btn_salir = view.findViewById(R.id.btn_salir);

        btn_jugardevuelta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundMP.start();
                vibe.vibrate(duracion);
                jugarDeNuevo();
                dialog.cancel();
            }
        });

        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundMP.start();
                vibe.vibrate(duracion);
                finish();
            }
        });

    }

    private void mostrarDialogVictoria(){

        AlertDialog.Builder builder = new AlertDialog.Builder(JugarActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_victoria,null);
        builder.setView(view).setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.show();

        numeroadivinado = view.findViewById(R.id.text_numero_adivinado); //Hizo falta aclarar la view para que no rompa, se mezclaban las vistas y rompía
        numeroadivinado.setText("Bien hecho! Obtuviste "+ puntuacion +" puntos");

        nombreIngresado = view.findViewById(R.id.editText_nombre_ingresado);

        Button btn_jugardevuelta = view.findViewById(R.id.btn_jugardevuelta_v);
        Button btn_salir = view.findViewById(R.id.btn_salir_v);
        final Button btn_guardar = view.findViewById(R.id.btn_guardar_v);

        btn_jugardevuelta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundMP.start();
                vibe.vibrate(duracion);
                jugarDeNuevo();
                dialog.cancel();
            }
        });

        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundMP.start();
                vibe.vibrate(duracion);
                finish();
            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundMP.start();
                vibe.vibrate(duracion);
                String nom = nombreIngresado.getText().toString();
                guardarEnBD(nom, Integer.toString(puntuacion),  dificultadGlobal);
                numeroadivinado.setText("Puntaje guardado exitosamente!!");
                btn_guardar.setClickable(false);
                btn_guardar.setBackgroundColor(Color.GRAY);
                //dialog.cancel();
            }
        });

    }

    private void jugarDeNuevo(){
        inicializar();
        adapter.clear();
        contador_intentos = 0;
        listView.setAdapter(null);
        numberPicker.setValue(1);
        numberPicker1.setValue(0);
        numberPicker2.setValue(0);
        numberPicker3.setValue(0);
        btnJugarDeNuevo.setVisibility(View.INVISIBLE);
        btnJugarDeNuevo.setClickable(false);
        btnAbandonar.setVisibility(View.VISIBLE);
        btnAbandonar.setClickable(true);
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


    private boolean analizar_intento(String num_intento, String num_generado ){ // para determinar si el numero del usuario es igual al generado

        boolean adivinado = false;

        if(validar_numero(num_intento)){ // si el numero cumple los parametros

            tvNumeros.setText(num_intento);

            if(!es_repetido(lista_intentos, num_intento)){ // Si el numero NO fue ingresado previamente

                boolean iguales = son_iguales(Integer.parseInt(num_intento), Integer.parseInt(num_generado));

                if(iguales){ // si son iguales, EL NUMERO FUE ADIVINADO

                    adivinado = true;
                    puntuacion = calcularPuntaje(contador_intentos, dificultadGlobal);
                    mostrarDialogVictoria();  // ------------------analizar ubicacion en el codigo ------------
                    lista_intentos.clear(); // vacio la lista de numeros
                    lista_intentos_descripcion.clear(); // vacio la lista con las descripciones
                    listView.setAdapter(adapter);
                    btnJugarDeNuevo.setClickable(true);
                    btnJugarDeNuevo.setVisibility(View.VISIBLE);
                    btnAbandonar.setVisibility(View.INVISIBLE);
                    btnAbandonar.setClickable(false);
                }else{ // Si NO son iguales, el numero no fue adivinado y debe agregarse a la lista de intentos
                    contador_intentos ++;
                    int cant_regulares = buscar_regulares(num_intento, num_generado);
                    int cant_correctos = buscar_correctos(num_intento, num_generado);
                    tvNumeros.setText("Correctos: "+ cant_correctos +" Regulares: "+ cant_regulares);
                    actualizar_lista(lista_intentos_descripcion,num_intento + " --> Correctos: "+ cant_correctos+" Regulares: "+ cant_regulares); // agrego el intento a la lista
                    actualizar_lista(lista_intentos, num_intento);
                    listView.setAdapter(adapter);
                }
            }else{ // Si el numero ya fue ingresado

                tvNumeros.setText("==Numero ya intentado==");
            }
        }else { // si el numero no cumple los parametros

            tvNumeros.setText("Error - Digitos repetidos");
        }
        // retorno un booleano
        return adivinado;
    }

    private int buscar_regulares(String intento, String numeroGenerado){

        char num_intento;
        int contador_regulares = 0;

        for (int i=0; i < intento.length(); i++){

             num_intento = intento.charAt(i);

             for (int j=0; j < numeroGenerado.length(); j++){

                 if (num_intento == numeroGenerado.charAt(j) && j != i){ // si son iguales y NO estan en la misma posicion
                     contador_regulares++;
                 }
             }
        }
        return contador_regulares;
    }

    private int buscar_correctos(String intento, String numeroGenerado) {

        int contador_correctos = 0;

        for (int i=0; i < intento.length(); i++)

            if(intento.charAt(i) == numeroGenerado.charAt(i)){ // si estan en la misma posicion y son iguales

                contador_correctos++;
            }
        return contador_correctos;
    }

    private void actualizar_lista(ArrayList<String> lista, String valor){
        lista.add(valor);
    }

    private boolean es_repetido(ArrayList<String> lista, String valor){

        boolean repetido = false;

        for(String contenido : lista){

            if(valor.equals(contenido)){

                repetido = true;
                break;
            }
        }
        return repetido;
    }

    /*
    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        tvShowNumbers.setText("Old Number= "+i+" New Number = "+i1);
    }*/

    private boolean son_iguales(int a, int b){
        return a == b; /*retorna True o False*/
    }

    private int getRandomNumber(int min,int max) {
        return (new Random()).nextInt((max - min) + 1) + min;
    }

    private String generar_numeroRandom(){

        int valor1 = getRandomNumber(1,9);
        int valor2 = getRandomNumber(0,9);
        int valor3 = getRandomNumber(0,9);
        int valor4 = getRandomNumber(0,9);

        while(son_iguales(valor1,valor2)){
            valor2 = getRandomNumber(0,9);
        }

        while(son_iguales(valor1,valor3) || son_iguales(valor2,valor3)) {
            valor3 = getRandomNumber(0,9);
        }

        while(son_iguales(valor1,valor4) || son_iguales(valor2,valor4) || son_iguales(valor3,valor4)){
            valor4 = getRandomNumber(0,9);
        }

        //Para poder concatenar los 4 valores, recomiendan pasar cada uno a string y de ahi concatenarlos, una vez concatenados, se pueden transformar a un unico entero.
        String prueba1 = String.valueOf(valor1);
        String prueba2 = String.valueOf(valor2);
        String prueba3 = String.valueOf(valor3);
        String prueba4 = String.valueOf(valor4);

        String resu = prueba1 + prueba2 + prueba3 + prueba4;

        return resu;

    }

    private void guardarEnBD(String nombre, String puntaje, String dificultad){
        DatabaseReference mDataBase = FirebaseDatabase.getInstance().getReference();
        //Map<String, Puntaje> users = new HashMap<>();
        Puntaje puntajeBD = new Puntaje(nombre, puntaje, dificultad);
        mDataBase.child("Usuarios").push().setValue(puntajeBD);
    }

    private int calcularPuntaje(int contador_intentos, String dificultad){
        int puntaje_max_facil = 100;
        int puntaje_max_intermedio = 200;
        int puntaje_max_dificil = 300;
        int puntaje_final = 0;
        switch (dificultad){
            case "intermedio":
                puntaje_final = puntaje_max_intermedio - (contador_intentos * 5);
                break;
            case "dificil":
                puntaje_final = puntaje_max_dificil- (contador_intentos * 5);
                break;
            default: // seria el nivel FACIL
                puntaje_final = puntaje_max_facil - (contador_intentos * 5);
        }
        if (puntaje_final < 0){
            puntaje_final = 0;
        }
        return puntaje_final ;
    }


}
