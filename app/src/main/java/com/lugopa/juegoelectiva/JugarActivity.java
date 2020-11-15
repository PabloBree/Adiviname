package com.lugopa.juegoelectiva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lugopa.juegoelectiva.Model.Intento;
import com.lugopa.juegoelectiva.Model.Puntaje;
import com.lugopa.juegoelectiva.Model.ServicioObtencionDeDireccion;
import com.lugopa.juegoelectiva.Model.VariablesGlobales;
import com.lugopa.juegoelectiva.Model.Constantes;


public class JugarActivity extends AppCompatActivity {

    private Button btnIntento;
    private Button btnAbandonar;
    private Button btnJugarDeNuevo;
    private TextView tvNumeros;
    private TextView numeroadivinado;
    private TextView nombreIngresado;
    private NumberPicker numberPicker0;
    private NumberPicker numberPicker1;
    private NumberPicker numberPicker2;
    private NumberPicker numberPicker3;
    private NumberPicker numberPicker4;
    private NumberPicker numberPicker5;
    private NumberPicker numberPicker6;
    private NumberPicker numberPicker7;
    private NumberPicker numberPicker8;
    private int[] arrayNumeros = new int[9]; // deberia ser de 9
    private int largo_dificultad; ////////////////////////////////////////////// TIENE QUE OBTENER SU VALOR SEGUN EL NIVEL DE DIFICULTAD ///////////////////////////

    private int contador_intentos = 0;
    private int puntuacion;
    private String dificultadGlobal;

    // numero random
    String numero_random;

    ListView listView;
    private ArrayList<String> lista_intentos_numeros = new ArrayList();
    private ArrayList<Intento> lista_intentos_objetos = new ArrayList();
    //ArrayAdapter<String> adapter;
    //ADAPTER LISTA INTENTOS PERSONALIZADO =======================================================================================================================
    IntentoListAdapter adapter;

    // Vibracion y sonido de botones
    private Vibrator vibe;
    int duracion = 80;
    private MediaPlayer soundMP;

    // geolocalizacion
    private static final int SOLICITUD_CODIGO_PERMISO_UBICACION = 1;
    private ResultReceiver resultReceiver;
    private String ubicacionActual = "Sin registro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugar);

        inicializar();

        vibe = (Vibrator) JugarActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
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

        numberPicker0.setMinValue(1);
        numberPicker0.setMaxValue(9);

        numberPicker1.setMinValue(0);
        numberPicker1.setMaxValue(9);

        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(9);

        numberPicker3.setMinValue(0);
        numberPicker3.setMaxValue(9);

        numberPicker4.setMinValue(0);
        numberPicker4.setMaxValue(9);

        numberPicker5.setMinValue(0);
        numberPicker5.setMaxValue(9);

        numberPicker6.setMinValue(0);
        numberPicker6.setMaxValue(9);

        numberPicker7.setMinValue(0);
        numberPicker7.setMaxValue(9);

        numberPicker8.setMinValue(0);
        numberPicker8.setMaxValue(9);

        // to change formate of number in numberpicker
        numberPicker0.setFormatter(new NumberPicker.Formatter() {
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

        numberPicker4.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%1d", i);
            }
        });

        numberPicker5.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%1d", i);
            }
        });

        numberPicker6.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%1d", i);
            }
        });

        numberPicker7.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%1d", i);
            }
        });

        numberPicker8.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%1d", i);
            }
        });

        //numberPicker.setOnValueChangedListener(this);

        getPermisoUbicacion();

        resultReceiver = new ReceptorResultadoDeDireccion(new Handler());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void inicializar() {
        btnIntento = findViewById(R.id.button_Intento);
        btnAbandonar = findViewById(R.id.button_Abandonar);
        btnJugarDeNuevo = findViewById(R.id.button_JugarDeNuevo);
        tvNumeros = findViewById(R.id.textViewNumeros);

        //Numero Pickers definition
        numberPicker0 = findViewById(R.id.numberPicker0);
        numberPicker1 = findViewById(R.id.numberPicker1);
        numberPicker2 = findViewById(R.id.numberPicker2);
        numberPicker3 = findViewById(R.id.numberPicker3);
        numberPicker4 = findViewById(R.id.numberPicker4);
        numberPicker5 = findViewById(R.id.numberPicker5);
        numberPicker6 = findViewById(R.id.numberPicker6);
        numberPicker7 = findViewById(R.id.numberPicker7);
        numberPicker8 = findViewById(R.id.numberPicker8);

        //variable global de dificultad
        dificultadGlobal = ((VariablesGlobales) this.getApplication()).getDificultad();
        largo_dificultad = obtenerLargoDificultad();

        numero_random = generar_numeroRandom(); // asignamos el numero random que debe adivinarse
        tvNumeros.setText("Generado = " + numero_random);

        // Manejo de la lista.....
        //listView = findViewById(R.id.list_view_puntajes);
        //adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, lista_intentos_descripcion);
        listView = findViewById(R.id.list_view_puntajes);
        adapter = new IntentoListAdapter(getApplicationContext(),R.layout.adapter_intentos_layout,lista_intentos_objetos);

        set_visualizacion_pickers();
    }

    private void oprimir_boton_Intentar() {
        soundMP.start();
        vibe.vibrate(duracion);
        //////////////////////////////////////////////////////////////////////////////////// HABRIA QUE HACER UN CASE PARA DETERMINAR QUE VALORES TOMA SEGUN LA VARIAbLE GLOBAL DE DIFICULTAD
        int numero;
        String str_num = "000";
        switch (dificultadGlobal) {
            case "facil":
                arrayNumeros[0] = numberPicker0.getValue();
                arrayNumeros[1] = numberPicker1.getValue();
                arrayNumeros[2] = numberPicker2.getValue();
                numero = Integer.parseInt(String.valueOf(arrayNumeros[0]) + String.valueOf(arrayNumeros[1]) + String.valueOf(arrayNumeros[2]));
                str_num = Integer.toString(numero);
                break;
            case "intermedio":
                arrayNumeros[0] = numberPicker0.getValue();
                arrayNumeros[1] = numberPicker1.getValue();
                arrayNumeros[2] = numberPicker2.getValue();
                arrayNumeros[3] = numberPicker3.getValue();
                arrayNumeros[4] = numberPicker4.getValue();
                arrayNumeros[5] = numberPicker5.getValue();
                numero = Integer.parseInt(String.valueOf(arrayNumeros[0]) + String.valueOf(arrayNumeros[1]) + String.valueOf(arrayNumeros[2]) + String.valueOf(arrayNumeros[3]) + String.valueOf(arrayNumeros[4]) + String.valueOf(arrayNumeros[5]));
                str_num = Integer.toString(numero);
                break;
            case "dificil":
                arrayNumeros[0] = numberPicker0.getValue();
                arrayNumeros[1] = numberPicker1.getValue();
                arrayNumeros[2] = numberPicker2.getValue();
                arrayNumeros[3] = numberPicker3.getValue();
                arrayNumeros[4] = numberPicker4.getValue();
                arrayNumeros[5] = numberPicker5.getValue();
                arrayNumeros[6] = numberPicker6.getValue();
                arrayNumeros[7] = numberPicker7.getValue();
                arrayNumeros[8] = numberPicker8.getValue();
                numero = Integer.parseInt(String.valueOf(arrayNumeros[0]) + String.valueOf(arrayNumeros[1]) + String.valueOf(arrayNumeros[2]) + String.valueOf(arrayNumeros[3]) + String.valueOf(arrayNumeros[4]) + String.valueOf(arrayNumeros[5]) + String.valueOf(arrayNumeros[6]) + String.valueOf(arrayNumeros[7]) + String.valueOf(arrayNumeros[8]));
                str_num = Integer.toString(numero);
                break;
        }
        analizar_intento(str_num, numero_random);
    }

    private void oprimir_boton_Abandonar() {
        soundMP.start();
        vibe.vibrate(duracion);
        mostrarDialogAbandonar();
    }

    private void mostrarDialogAbandonar() {

        AlertDialog.Builder builder = new AlertDialog.Builder(JugarActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_abandonar, null);
        builder.setView(view).setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.show();

        numeroadivinado = view.findViewById(R.id.text_numero_adivinado); //Hizo falta aclarar la view para que no rompa, se mezclaban las vistas y rompía
        numeroadivinado.setText("El número era " + numero_random);

        Button btn_jugardevuelta = view.findViewById(R.id.btn_jugardevuelta);
        Button btn_salir = view.findViewById(R.id.button_regresar_configuracion);

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

    private void mostrarDialogVictoria() {

        AlertDialog.Builder builder = new AlertDialog.Builder(JugarActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_victoria, null);
        builder.setView(view).setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.show();

        numeroadivinado = view.findViewById(R.id.text_numero_adivinado); //Hizo falta aclarar la view para que no rompa, se mezclaban las vistas y rompía
        numeroadivinado.setText("Bien hecho! Obtuviste " + puntuacion + " puntos");

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
                guardarEnBD(nom, Integer.toString(puntuacion), dificultadGlobal, ubicacionActual);
                numeroadivinado.setText("Puntaje guardado exitosamente!!");
                btn_guardar.setClickable(false);
                btn_guardar.setBackgroundColor(Color.GRAY);
                //dialog.cancel();
            }
        });

    }

    private void jugarDeNuevo() {
        inicializar();
        adapter.clear();
        contador_intentos = 0;
        listView.setAdapter(null);
        numberPicker0.setValue(1);
        numberPicker1.setValue(0);
        numberPicker2.setValue(0);
        numberPicker3.setValue(0);
        numberPicker4.setValue(0);
        numberPicker5.setValue(0);
        numberPicker6.setValue(0);
        numberPicker7.setValue(0);
        numberPicker8.setValue(0);
        btnJugarDeNuevo.setVisibility(View.INVISIBLE);
        btnJugarDeNuevo.setClickable(false);
        btnAbandonar.setVisibility(View.VISIBLE);
        btnAbandonar.setClickable(true);
    }

    private void set_visualizacion_pickers() {
        switch (dificultadGlobal) {
            case "facil":
                numberPicker0.setVisibility(View.VISIBLE);
                numberPicker0.setClickable(false);
                numberPicker1.setVisibility(View.VISIBLE);
                numberPicker1.setClickable(false);
                numberPicker2.setVisibility(View.VISIBLE);
                numberPicker2.setClickable(false);
                // ----- no se deben ver ----
                numberPicker3.setVisibility(View.INVISIBLE);
                numberPicker3.setClickable(false);
                numberPicker4.setVisibility(View.INVISIBLE);
                numberPicker4.setClickable(false);
                numberPicker5.setVisibility(View.INVISIBLE);
                numberPicker5.setClickable(false);
                numberPicker6.setVisibility(View.INVISIBLE);
                numberPicker6.setClickable(false);
                numberPicker7.setVisibility(View.INVISIBLE);
                numberPicker7.setClickable(false);
                numberPicker8.setVisibility(View.INVISIBLE);
                numberPicker8.setClickable(false);
                break;
            case "intermedio":
                numberPicker0.setVisibility(View.VISIBLE);
                numberPicker0.setClickable(true);
                numberPicker1.setVisibility(View.VISIBLE);
                numberPicker1.setClickable(true);
                numberPicker2.setVisibility(View.VISIBLE);
                numberPicker2.setClickable(true);
                numberPicker3.setVisibility(View.VISIBLE);
                numberPicker3.setClickable(true);
                numberPicker4.setVisibility(View.VISIBLE);
                numberPicker4.setClickable(true);
                numberPicker5.setVisibility(View.VISIBLE);
                numberPicker5.setClickable(true);
                // ----- no se deben ver ----
                numberPicker6.setVisibility(View.INVISIBLE);
                numberPicker6.setClickable(false);
                numberPicker7.setVisibility(View.INVISIBLE);
                numberPicker7.setClickable(false);
                numberPicker8.setVisibility(View.INVISIBLE);
                numberPicker8.setClickable(false);
                break;
            case "dificil":
                numberPicker0.setVisibility(View.VISIBLE);
                numberPicker0.setClickable(true);
                numberPicker1.setVisibility(View.VISIBLE);
                numberPicker1.setClickable(true);
                numberPicker2.setVisibility(View.VISIBLE);
                numberPicker2.setClickable(true);
                numberPicker3.setVisibility(View.VISIBLE);
                numberPicker3.setClickable(true);
                numberPicker4.setVisibility(View.VISIBLE);
                numberPicker4.setClickable(true);
                numberPicker5.setVisibility(View.VISIBLE);
                numberPicker5.setClickable(true);
                numberPicker6.setVisibility(View.VISIBLE);
                numberPicker6.setClickable(true);
                numberPicker7.setVisibility(View.VISIBLE);
                numberPicker7.setClickable(true);
                numberPicker8.setVisibility(View.VISIBLE);
                numberPicker8.setClickable(true);
                break;
        }
    }

    private boolean validar_numero(String numero) { // =========================================================== SOLO DEBERIA CHEQUEAR QUE NO COMIENCE EN CERO =========================================
        boolean es_valido;
        es_valido = numero.charAt(0) != '0';
        return es_valido;
    }


    private boolean analizar_intento(String num_intento, String num_generado) { // para determinar si el numero del usuario es igual al generado

        boolean adivinado = false;

        if (validar_numero(num_intento)) { // si el numero cumple los parametros (no comienza con cero)

            tvNumeros.setText(num_intento);

            if (!es_repetido(lista_intentos_numeros, num_intento)) { // Si el numero NO fue ingresado previamente

                boolean iguales = son_iguales(Integer.parseInt(num_intento), Integer.parseInt(num_generado));

                if (iguales) { // si son iguales, EL NUMERO FUE ADIVINADO

                    adivinado = true;
                    puntuacion = calcularPuntaje(contador_intentos, dificultadGlobal);
                    mostrarDialogVictoria();  // ------------------analizar ubicacion en el codigo ------------
                    lista_intentos_numeros.clear(); // vacio la lista de numeros
                    lista_intentos_objetos.clear(); // vacio la lista con las descripciones
                    listView.setAdapter(adapter);
                    btnJugarDeNuevo.setClickable(true);
                    btnJugarDeNuevo.setVisibility(View.VISIBLE);
                    btnAbandonar.setVisibility(View.INVISIBLE);
                    btnAbandonar.setClickable(false);
                } else { // Si NO son iguales, el numero no fue adivinado y debe agregarse a la lista de intentos
                    contador_intentos++;
                    int cant_regulares = buscar_regulares(num_intento, num_generado);
                    int cant_correctos = buscar_correctos(num_intento, num_generado);
                    //tvNumeros.setText("Correctos: " + cant_correctos + " Regulares: " + cant_regulares);
                    //lista_intentos_objetos.add( num_intento + " --> Correctos: " + cant_correctos + " Regulares: " + cant_regulares); // agrego el intento a la lista
                    lista_intentos_numeros.add(num_intento);
                    //listView.setAdapter(adapter);
                    // con adapter personalizado
                    String descripcion = ("Correctos = "+cant_correctos + " | Regulares = "+cant_regulares);
                    Intento intento = new Intento(num_intento, descripcion, Integer.toString(contador_intentos));
                    lista_intentos_objetos.add(intento);
                    listView.setAdapter(adapter);
                }
            } else { // Si el numero ya fue ingresado

                tvNumeros.setText("==Numero ya intentado==");
            }
        } else { // si el numero comienza con cero

            tvNumeros.setText("Error - La primer cuadricula NO puede ser cero");
        }
        // retorno un booleano
        return adivinado;
    }

    private int buscar_regulares(String intento, String numeroGenerado) {
        char num_intento;
        int contador_regulares = 0;

        for (int i = 0; i < intento.length(); i++) {
            num_intento = intento.charAt(i);
            for (int j = 0; j < numeroGenerado.length(); j++) {
                if (num_intento == numeroGenerado.charAt(j) && j != i) { // si son iguales y NO estan en la misma posicion
                    contador_regulares++;
                }
            }
        }
        return contador_regulares;
    }

    private int buscar_correctos(String intento, String numeroGenerado) {
        int contador_correctos = 0;
        for (int i = 0; i < intento.length(); i++)
            if (intento.charAt(i) == numeroGenerado.charAt(i)) { // si estan en la misma posicion y son iguales
                contador_correctos++;
            }
        return contador_correctos;
    }


    private boolean es_repetido(ArrayList<String> lista, String valor) {
        boolean repetido = false;
        for (String contenido : lista) {
            if (valor.equals(contenido)) {
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

    private boolean son_iguales(int a, int b) {
        return a == b; /*retorna True o False*/
    }

    private int getRandomNumber(int min, int max) {
        return (new Random()).nextInt((max - min) + 1) + min;
    }


    private String generar_numeroRandom() {
        String num = "0";
        switch (dificultadGlobal) {
            case "facil":
                do {
                    num = Integer.toString(getRandomNumber(100, 999));
                } while (num.charAt(0) == '0');
                break;
            case "intermedio":
                do {
                    num = Integer.toString(getRandomNumber(100000, 999999));
                } while (num.charAt(0) == '0');
                break;
            case "dificil":
                do {
                    num = Integer.toString(getRandomNumber(100000000, 999999999));
                } while (num.charAt(0) == '0');
                break;
        }
        return num;
    }

    private void guardarEnBD(String nombre, String puntaje, String dificultad, String ubicacion) {
        DatabaseReference mDataBase = FirebaseDatabase.getInstance().getReference();
        //Map<String, Puntaje> users = new HashMap<>();
        Puntaje puntajeBD = new Puntaje(nombre, puntaje, dificultad, ubicacion);
        mDataBase.child("Usuarios").push().setValue(puntajeBD);
    }

    private int calcularPuntaje(int contador_intentos, String dificultad) {
        int puntaje_max_facil = 3000;
        int puntaje_max_inter = 6000;
        int puntaje_max_dificil = 9000;

        int puntaje_final = 0;
        if(contador_intentos == 0){
            contador_intentos =1;
        }
        switch (dificultad) {
            case "intermedio":
                puntaje_final = puntaje_max_inter- (contador_intentos * 100);
                break;
            case "dificil":
                puntaje_final = puntaje_max_dificil - (contador_intentos * 100);
                break;
            default: // seria el nivel FACIL
                puntaje_final = puntaje_max_facil - (contador_intentos * 100);
        }
        if (puntaje_final < 0) {
            puntaje_final = 0;
        }
        return puntaje_final;
    }

    private int obtenerLargoDificultad() {
        int valor;
        switch (dificultadGlobal) {
            case "intermedio":
                valor = 6;
                break;
            case "dificil":
                valor = 9;
                break;
            default:
                valor = 3;
        }
        return valor;
    }

    private void getUbicacionActual() {
        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(JugarActivity.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(JugarActivity.this)
                                .removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latestLocationIndex = locationResult.getLocations().size() - 1;
                            double latitud =
                                    locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            double longitud =
                                    locationResult.getLocations().get(latestLocationIndex).getLongitude();
//                            textLatLong.setText(
//                                    String.format(
//                                            "latitude: %s\nLongitude: %s",
//                                            latitud,
//                                            longitud
//                                    )
//                            );
                            Toast.makeText(JugarActivity.this, "Latitud: " + latitud + " Longitud: " + longitud, Toast.LENGTH_SHORT).show();

                            Location location = new Location("providerNA");
                            location.setLatitude(latitud);
                            location.setLongitude(longitud);
                            obtenerdireccionDesdeLatLong(location);

                        } else {
                            // aca, ocultaba el PROGRESSBAR
                        }
                    }
                }, Looper.getMainLooper() );
    }

    private void obtenerdireccionDesdeLatLong(Location location){
        Intent intent = new Intent(this, ServicioObtencionDeDireccion.class);
        intent.putExtra(Constantes.RECEIVER, resultReceiver);
        intent.putExtra(Constantes.LOCATION_DATA_EXTRA, location);
        startService(intent);
    }

    private class ReceptorResultadoDeDireccion extends ResultReceiver {
        ReceptorResultadoDeDireccion(Handler handler) {
            super(handler);
        }
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == Constantes.SUCCESS_RESULT){
                Toast.makeText(JugarActivity.this, resultData.getString(Constantes.RESULT_DATA_KEY), Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(JugarActivity.this, resultData.getString(Constantes.RESULT_DATA_KEY), Toast.LENGTH_SHORT).show();
            }
            ubicacionActual = resultData.getString(Constantes.RESULT_DATA_KEY);
        }
    }

    private void getPermisoUbicacion(){
        if(ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    JugarActivity.this, // NO ERA ASI LA LINEA
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    SOLICITUD_CODIGO_PERMISO_UBICACION
            );
        } else {
            getUbicacionActual();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SOLICITUD_CODIGO_PERMISO_UBICACION && grantResults.length > 0) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUbicacionActual();
            } else {
                Toast.makeText(this,"Permiso denegado!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
