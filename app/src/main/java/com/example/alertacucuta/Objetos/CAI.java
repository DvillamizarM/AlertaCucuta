package com.example.alertacucuta.Objetos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author DANIELA VILLAMIZAR MENDOZA
 */
public class CAI {
    String id;
    String telefono, direccion , barrio, estado;
    String fechaRegistro;
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");

    public CAI() {
    }

    public CAI(String telefono, String direccion, String barrio) {
        this.telefono = telefono;
        this.direccion = direccion;
        this.barrio = barrio;
        this.estado = "Activo";
        this.fechaRegistro = formatter.format(new Date(System.currentTimeMillis()));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
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
}
