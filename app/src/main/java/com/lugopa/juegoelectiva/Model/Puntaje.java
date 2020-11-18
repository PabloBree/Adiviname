package com.lugopa.juegoelectiva.Model;


public class Puntaje {
    private String nombre;
    private String puntaje;
    private String dificultad;
    private String ubicacion;
    private Double latitud;
    private Double longitud;

    public Puntaje(String n, String p, String d, String u, Double lat, Double lon){
        this.nombre = n;
        this.puntaje = p;
        this.dificultad = d;
        this.ubicacion = u;
        this.latitud = lat;
        this.longitud = lon;
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

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
}
