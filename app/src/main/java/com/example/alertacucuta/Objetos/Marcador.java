package com.example.alertacucuta.Objetos;

import com.google.android.gms.maps.model.LatLng;

public class Marcador {
    private Object id;
    private String  tipo;
    private LatLng coordinates;
    public Marcador() {}

    public Object getId() {
        return id;
    }
    public void setId(Object id) {
        this.id = id;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }
    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
