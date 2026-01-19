package com.tuuni.testws.model;

public class Persona {

    // Ajusta esto si tu profe te pide otro valor
    private static final double SALARIO_MINIMO = 1300000.0;

    private int id;
    private String nombre;
    private int edad;

    // Calculado: salario = edad * salario_minimo / 3
    private double salario;

    public Persona() {}

    public Persona(int id, String nombre, int edad) {
        this.id = id;
        this.nombre = nombre;
        setEdad(edad); // recalcula salario
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) {
        this.edad = edad;
        this.salario = (edad * SALARIO_MINIMO) / 3.0;
    }

    public double getSalario() { return salario; }

    // (Opcional) si llega salario en el JSON, lo ignoramos y recalculamos
    public void setSalario(double salario) {
        this.salario = (this.edad * SALARIO_MINIMO) / 3.0;
    }
}
