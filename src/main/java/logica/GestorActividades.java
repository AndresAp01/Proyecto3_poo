package logica;

import modelo.actividad.Actividad;
import modelo.actividad.TipoActividad;
import modelo.materiales.RecursoInventario;
import util.GestorArchivos;
import util.MiExcepcion;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorActividades implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String ARCHIVO_ACTIVIDADES = "actividades.dat";
    private List<Actividad> listaActividades;
    private int contadorIds;

    public GestorActividades() {
        this.listaActividades = new ArrayList<>();
        this.contadorIds = 1;
        cargarDatos();
    }

    public Actividad crearActividad(String objetivo, LocalDateTime fecha, String lugar, TipoActividad tipo) throws MiExcepcion {
        if (objetivo == null || objetivo.trim().isEmpty()) {
            throw new MiExcepcion("El objetivo de la actividad es obligatorio.");
        }
        if (fecha == null || fecha.isBefore(LocalDateTime.now())) {
            throw new MiExcepcion("La fecha debe ser futura o válida.");
        }

        Actividad nueva = new Actividad(contadorIds++, objetivo, fecha, lugar, tipo);
        listaActividades.add(nueva);
        guardarDatos();
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
        guardarDatos();
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

    public void guardarDatos() {
        try {
            GestorArchivos.guardarObjeto(ARCHIVO_ACTIVIDADES, new ActividadesData(listaActividades, contadorIds));
        } catch (MiExcepcion e) {
            System.err.println("Error al guardar actividades: " + e.getMessage());
        }
    }

    public void cargarDatos() {
        try {
            ActividadesData datosCargados = GestorArchivos.cargarObjeto(ARCHIVO_ACTIVIDADES, ActividadesData.class);
            if (datosCargados != null) {
                this.listaActividades = datosCargados.getListaActividades();
                this.contadorIds = datosCargados.getContadorIds();
            }
        } catch (MiExcepcion e) {
            System.out.println("No se encontraron datos previos de actividades o error al cargar (se inicia vacio).");
        }
    }

    // Clase interna para persistencia
    private static class ActividadesData implements Serializable {
        private static final long serialVersionUID = 1L;
        private List<Actividad> listaActividades;
        private int contadorIds;

        public ActividadesData(List<Actividad> listaActividades, int contadorIds) {
            this.listaActividades = listaActividades;
            this.contadorIds = contadorIds;
        }

        public List<Actividad> getListaActividades() {
            return listaActividades;
        }

        public int getContadorIds() {
            return contadorIds;
        }
    }
}
