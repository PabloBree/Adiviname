package com.lugopa.juegoelectiva.Model;

public class Intento {
    private String numero;
    private String descripcion;
    private String posicion; // ???????????

    public Intento(String numero, String descripcion, String posicion) {
        this.numero = numero;
        this.descripcion = descripcion;
        this.posicion = posicion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }
}
