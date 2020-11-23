package com.example.alertacucuta.Objetos;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author DANIELA VILLAMIZAR MENDOZA
 */
public class Crimen {
    private String id, id_usuario;
    private Usuario usuario;
    private String barrio,  tipo, descripcion, direccion, descripcionVictima, descripcionSospechoso, estado;
    private String fechaRegistro;
    private String hora;
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");

    public Crimen() {
    }

    public Crimen(String id_usuario, String barrio, String tipo, String direccion, String descripcion, String descripcionVictima, String descripcionSospechoso, String hora) {
        this.id_usuario = id_usuario;
        this.barrio = barrio;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.descripcionVictima = descripcionVictima;
        this.descripcionSospechoso = descripcionSospechoso;
        this.hora = hora;
        this.estado = "Activo";
        this.fechaRegistro = formatter.format(new Date(System.currentTimeMillis()));
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return id_usuario;
    }

    public void setUsuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcionVictima() {
        return descripcionVictima;
    }

    public void setDescripcionVictima(String descripcionVictima) {
        this.descripcionVictima = descripcionVictima;
    }

    public String getDescripcionSospechoso() {
        return descripcionSospechoso;
    }

    public void setDescripcionSospechoso(String descripcionSospechoso) {
        this.descripcionSospechoso = descripcionSospechoso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}

