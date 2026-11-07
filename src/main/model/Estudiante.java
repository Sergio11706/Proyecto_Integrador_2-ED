package main.model;

public class Estudiante {
    private String dni;
    private String nombre;
    private String apellido;
    private int edad;

    // Crea un estudiante con sus datos personales.
    public Estudiante(String dni, String nombre, String apellido, int edad) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }

    // Devuelve el DNI del estudiante.
    public String getDni() {
        return dni;
    }

    // Actualiza el DNI del estudiante.
    public void setDni(String dni) {
        this.dni = dni;
    }

    // Devuelve el nombre del estudiante.
    public String getNombre() {
        return nombre;
    }

    // Actualiza el nombre del estudiante.
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Devuelve el apellido del estudiante.
    public String getApellido() {
        return apellido;
    }

    // Actualiza el apellido del estudiante.
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    // Devuelve la edad del estudiante.
    public int getEdad() {
        return edad;
    }

    // Actualiza la edad del estudiante.
    public void setEdad(int edad) {
        this.edad = edad;
    }

    // Devuelve el nombre completo en orden de nombre y apellido.
    public String apellidoYNombre(){
        return this.nombre + " " + this.apellido;
    }

    // Devuelve los datos personales del estudiante en formato legible.
    public String datosAlumno(){
        return apellido + ", " + nombre +
           " | DNI: " + dni +
           " | Edad: " + edad + " años";
    }

    // Devuelve una representación textual de los datos del estudiante.
    @Override
    public String toString() {
        return "Estudiante{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                '}';
    }
}