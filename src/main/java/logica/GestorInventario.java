package logica;

import modelo.materiales.RecursoInventario;
import util.GestorArchivos;
import util.MiExcepcion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GestorInventario implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String ARCHIVO_INVENTARIO = "inventario.dat";
    private List<RecursoInventario> inventario;
    private int contadorIds;

    public GestorInventario() {
        this.inventario = new ArrayList<>();
        this.contadorIds = 1;
        cargarDatos();
    }

    public RecursoInventario agregarRecurso(String nombre, int cantidad, RecursoInventario.TipoRecurso tipo) throws MiExcepcion {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new MiExcepcion("El nombre del recurso no puede estar vacío.");
        }
        if (cantidad < 0) {
            throw new MiExcepcion("La cantidad no puede ser negativa.");
        }

        RecursoInventario nuevo = new RecursoInventario(contadorIds++, nombre, cantidad, tipo);
        inventario.add(nuevo);
        guardarDatos();
        return nuevo;
    }

    public RecursoInventario buscarRecursoPorId(int id) {
        return inventario.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void eliminarRecurso(int id) throws MiExcepcion {
        RecursoInventario recurso = buscarRecursoPorId(id);
        if (recurso == null) {
            throw new MiExcepcion("Recurso no encontrado con ID: " + id);
        }
        inventario.remove(recurso);
        guardarDatos();
    }

    public List<RecursoInventario> obtenerTodoElInventario() {
        return new ArrayList<>(inventario);
    }

    public void guardarDatos() {
        try {
            // Guardar tanto la lista como el contador de IDs
            GestorArchivos.guardarObjeto(ARCHIVO_INVENTARIO, new InventarioData(inventario, contadorIds));
        } catch (MiExcepcion e) {
            System.err.println("Error al guardar inventario: " + e.getMessage());
        }
    }

    public void cargarDatos() {
        try {
            InventarioData datosCargados = GestorArchivos.cargarObjeto(ARCHIVO_INVENTARIO, InventarioData.class);
            if (datosCargados != null) {
                this.inventario = datosCargados.getInventario();
                this.contadorIds = datosCargados.getContadorIds();
            }
        } catch (MiExcepcion e) {
            System.out.println("No se encontraron datos previos de inventario o error al cargar (se inicia vacio).");
        }
    }

    // Clase interna para envolver la lista y el contador de IDs para la serialización
    private static class InventarioData implements Serializable {
        private static final long serialVersionUID = 1L;
        private List<RecursoInventario> inventario;
        private int contadorIds;

        public InventarioData(List<RecursoInventario> inventario, int contadorIds) {
            this.inventario = inventario;
            this.contadorIds = contadorIds;
        }

        public List<RecursoInventario> getInventario() {
            return inventario;
        }

        public int getContadorIds() {
            return contadorIds;
        }
    }
}
