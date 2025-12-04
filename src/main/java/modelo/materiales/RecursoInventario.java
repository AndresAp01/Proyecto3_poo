package modelo.materiales;

import java.io.Serializable;
import modelo.materiales.TipoRecurso;

public class RecursoInventario implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private int cantidadDisponible;
    private TipoRecurso tipo;

    public RecursoInventario(int id, String nombre, int cantidadDisponible, TipoRecurso tipo) {
        this.id = id;
        this.nombre = nombre;
        this.cantidadDisponible = cantidadDisponible;
        this.tipo = tipo;
    }

    public void aumentarCantidad(int cantidad) {
        if (cantidad > 0) {
            this.cantidadDisponible += cantidad;
        }
    }

    public boolean consumirCantidad(int cantidad) {
        if (cantidad > 0 && this.cantidadDisponible >= cantidad) {
            this.cantidadDisponible -= cantidad;
            return true;
        }
        return false;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public TipoRecurso getTipo() {
        return tipo;
    }

    public void setTipo(TipoRecurso tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return nombre + " (" + tipo + "): " + cantidadDisponible;
    }
}
