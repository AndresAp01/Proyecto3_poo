package logica;

import modelo.voluntarios.Voluntario;
import modelo.voluntarios.Disponibilidad; // Importación añadida
import util.GestorArchivos;
import util.MiExcepcion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorVoluntarios implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String ARCHIVO_VOLUNTARIOS = "voluntarios.dat";
    private List<Voluntario> listaVoluntarios;

    public GestorVoluntarios() {
        this.listaVoluntarios = new ArrayList<>();
        cargarDatos();
    }

    public void registrarVoluntario(Voluntario voluntario) throws MiExcepcion {
        if (voluntario == null) {
            throw new MiExcepcion("El voluntario no puede ser nulo.");
        }
        if (buscarVoluntarioPorId(voluntario.getId()) != null) {
            throw new MiExcepcion("Ya existe un voluntario con el ID: " + voluntario.getId());
        }
        listaVoluntarios.add(voluntario);
        guardarDatos();
    }

    public Voluntario buscarVoluntarioPorId(String id) {
        return listaVoluntarios.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Voluntario> listarVoluntariosDisponibles() {
        return listaVoluntarios.stream()
                .filter(v -> v.getDisponibilidad() == Disponibilidad.DISPONIBLE) // Corrección aquí
                .collect(Collectors.toList());
    }

    public List<Voluntario> obtenerTodos() {
        return new ArrayList<>(listaVoluntarios);
    }

    public void eliminarVoluntario(String id) throws MiExcepcion {
        Voluntario v = buscarVoluntarioPorId(id);
        if (v == null) {
            throw new MiExcepcion("Voluntario no encontrado.");
        }
        listaVoluntarios.remove(v);
        guardarDatos();
    }

    public void guardarDatos() {
        try {
            GestorArchivos.guardarObjeto(ARCHIVO_VOLUNTARIOS, (ArrayList<Voluntario>) listaVoluntarios);
        } catch (MiExcepcion e) {
            System.err.println("Error al guardar voluntarios: " + e.getMessage());
        }
    }

    public void cargarDatos() {
        try {
            ArrayList<Voluntario> datosCargados = GestorArchivos.cargarObjeto(ARCHIVO_VOLUNTARIOS, ArrayList.class);
            if (datosCargados != null) {
                this.listaVoluntarios = datosCargados;
            }
        } catch (MiExcepcion e) {
            System.out.println("No se encontraron datos previos de voluntarios o error al cargar (se inicia vacio).");
        }
    }
}
