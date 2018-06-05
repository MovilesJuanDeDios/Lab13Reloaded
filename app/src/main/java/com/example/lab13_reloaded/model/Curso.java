package com.example.lab13_reloaded.model;

public class Curso {

    private String id;
    private String descripcion;
    private int creditos;

    public Curso() {
    }

    public Curso(String id, String descripcion, int creditos) {
        this.id = id;
        this.descripcion = descripcion;
        this.creditos = creditos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    @Override
    public String toString() {
        return "\nID: " + id + "\n" +
                "Descripción: " + descripcion + "\n" +
                "Créditos: " + creditos + "\n";
    }
}
