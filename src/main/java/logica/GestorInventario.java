package logica;

import modelo.materiales.RecursoInventario;
import util.GestorArchivos;
import util.MiExcepcion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GestorInventario implements Serializable {

    private List<RecursoInventario> inventario;
    private int contadorIds;

    public GestorInventario() {
        this.inventario = new ArrayList<>();
        this.contadorIds = 1;
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
    }

    public List<RecursoInventario> obtenerTodoElInventario() {
        return new ArrayList<>(inventario);
    }
}
