package com.example.alertacucuta.Objetos;

import com.google.android.gms.maps.model.LatLng;


public class Marcador{
    private Object id;
    private String  tipo, descripcion, fecha, hora, tipoTipo, estado;
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

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }
    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTipoTipo() {
        return tipoTipo;
    }
    public void setTipoTipo(String tipoTipo) {
        this.tipoTipo = tipoTipo;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
