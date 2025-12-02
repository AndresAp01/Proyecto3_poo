package logica;

import modelo.actividad.Actividad;
import modelo.materiales.RecursoInventario;
import util.GestorArchivos;
import util.MiExcepcion;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorActividades implements Serializable {

    private List<Actividad> listaActividades;
    private int contadorIds;

    public GestorActividades() {
        this.listaActividades = new ArrayList<>();
        this.contadorIds = 1;
    }

    public Actividad crearActividad(String objetivo, LocalDateTime fecha, String lugar) throws MiExcepcion {
        if (objetivo == null || objetivo.trim().isEmpty()) {
            throw new MiExcepcion("El objetivo de la actividad es obligatorio.");
        }
        if (fecha == null || fecha.isBefore(LocalDateTime.now())) {
            throw new MiExcepcion("La fecha debe ser futura o válida.");
        }

        Actividad nueva = new Actividad(contadorIds++, objetivo, fecha, lugar);
        listaActividades.add(nueva);
        return nueva;
    }

    public void asignarRecursoAActividad(int idActividad, RecursoInventario recurso, int cantidad) throws MiExcepcion {
        Actividad actividad = buscarActividad(idActividad);
        if (actividad == null) {
            throw new MiExcepcion("Actividad no encontrada.");
        }
        if (recurso == null) {
            throw new MiExcepcion("Recurso inválido.");
        }
        // Aquí se podría validar si hay suficiente inventario antes de asignar, 
        // pero eso depende de si consumimos el recurso ahora o al ejecutar la actividad.
        // Por ahora, solo registramos la asignación.
        actividad.asignarRecurso(recurso, cantidad);
    }

    public Actividad buscarActividad(int id) {
        return listaActividades.stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Actividad> listarActividadesPorEstado(Actividad.EstadoActividad estado) {
        return listaActividades.stream()
                .filter(a -> a.getEstado() == estado)
                .collect(Collectors.toList());
    }

    public List<Actividad> obtenerTodas() {
        return new ArrayList<>(listaActividades);
    }
}
