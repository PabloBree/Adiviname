package com.lugopa.juegoelectiva.Model;


public class Puntaje {
    private String nombre;
    private String puntaje;
    private String dificultad;

    public Puntaje(String n, String p, String d){
        this.nombre = n;
        this.puntaje = p;
        this.dificultad = d;
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
}
