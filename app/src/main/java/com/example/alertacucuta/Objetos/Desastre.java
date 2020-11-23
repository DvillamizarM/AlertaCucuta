package com.example.alertacucuta.Objetos;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author DANIELA VILLAMIZAR MENDOZA
 */
public class Desastre {
    private String id, id_usuario;
    private String barrio,  tipo, descripcion, direccion,  estado;
    private String fechaRegistro;
    private String hora;
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");

    public Desastre() {
    }

    public Desastre(String id_usuario, String barrio, String tipo, String descripcion, String direccion, String hora) {
        this.id_usuario = id_usuario;
        this.barrio = barrio;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.estado = "Activo";
        this.fechaRegistro = formatter.format(new Date(System.currentTimeMillis()));
        this.hora = hora;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return id_usuario;
    }

    public void setUsuarioId(String id_usuario) {
        this.id_usuario= id;
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
