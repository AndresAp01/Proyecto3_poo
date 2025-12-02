package modelo.actividad;

import modelo.materiales.RecursoInventario;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Actividad {

    private int id;
    private String objetivo;
    private LocalDateTime fecha;
    private String lugar;
    private EstadoActividad estado;
    private String resultados;
    // Optimización: Map<Recurso, Cantidad> reemplaza a la clase InventarioAsignado
    private Map<RecursoInventario, Integer> recursosAsignados;

    public Actividad(int id, String objetivo, LocalDateTime fecha, String lugar) {
        this.id = id;
        this.objetivo = objetivo;
        this.fecha = fecha;
        this.lugar = lugar;
        this.estado = EstadoActividad.PENDIENTE;
        this.recursosAsignados = new HashMap<>();
    }

    public void asignarRecurso(RecursoInventario recurso, int cantidad) {
        if (recurso != null && cantidad > 0) {
            recursosAsignados.put(recurso, recursosAsignados.getOrDefault(recurso, 0) + cantidad);
        }
    }

    public Map<RecursoInventario, Integer> getRecursosAsignados() {
        return new HashMap<>(recursosAsignados);
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public EstadoActividad getEstado() {
        return estado;
    }

    public void setEstado(EstadoActividad estado) {
        this.estado = estado;
    }

    public String getResultados() {
        return resultados;
    }

    public void setResultados(String resultados) {
        this.resultados = resultados;
    }

    @Override
    public String toString() {
        return "Actividad{" + "id=" + id + ", objetivo='" + objetivo + "'" + ", estado=" + estado + '}';
    }

    public enum EstadoActividad {
        PENDIENTE,
        EN_PROGRESO,
        REALIZADA,
        CANCELADA
    }
}
