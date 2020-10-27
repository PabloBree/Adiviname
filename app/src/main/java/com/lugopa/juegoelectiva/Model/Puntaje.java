package com.lugopa.juegoelectiva.Model;


public class Puntaje {
    private String nombre;
    private String puntaje;
    private String dificultad;
    private String ubicacion;

    public Puntaje(String n, String p, String d, String u){
        this.nombre = n;
        this.puntaje = p;
        this.dificultad = d;
        this.ubicacion = u;
    }
    public Puntaje(){
    }

    private void guardarEnBD(){
       // puntuacionesBD.actualizarBD(parametros)
    }

    public String getNombre() {
        return nombre;
    }

    public String getDificultad() {
        return dificultad;
    }

    public String getPuntaje() {
        return puntaje;
    }

    public String getUbicacion(){
        return ubicacion;
    }
}
