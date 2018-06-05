package com.example.lab13_reloaded.model;

public class Estudiante {

    private String id;
    private String nombre;
    private String apellidos;
    private int edad;

    public Estudiante() {
    }

    public Estudiante(String id, String nombre, String apellidos, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "\nID: " + id + "\n" +
                "Nombre: " + nombre + " " + apellidos + "\n" +
                "Edad: " + edad + "\n";
    }
}
