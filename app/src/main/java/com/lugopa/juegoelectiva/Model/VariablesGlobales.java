package com.lugopa.juegoelectiva.Model;

import android.app.Application;

public class VariablesGlobales extends Application {
    private String dificultad = "facil";

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }
}
