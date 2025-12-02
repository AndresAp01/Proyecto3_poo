package logica;

import modelo.voluntarios.Voluntario;
import modelo.voluntarios.EstadoVoluntario;
import util.MiExcepcion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorVoluntarios {

    private List<Voluntario> listaVoluntarios;

    public GestorVoluntarios() {
        this.listaVoluntarios = new ArrayList<>();
    }

    public void registrarVoluntario(Voluntario voluntario) throws MiExcepcion {
        if (voluntario == null) {
            throw new MiExcepcion("El voluntario no puede ser nulo.");
        }
        // Verificamos si ya existe (usando cédula, que hereda de Usuario)
        if (buscarVoluntarioPorCedula(voluntario.getCedula()) != null) {
            throw new MiExcepcion("Ya existe un voluntario con la cédula: " + voluntario.getCedula());
        }
        listaVoluntarios.add(voluntario);
    }

    public Voluntario buscarVoluntarioPorCedula(String cedula) {
        return listaVoluntarios.stream()
                .filter(v -> v.getCedula().equals(cedula))
                .findFirst()
                .orElse(null);
    }

    public List<Voluntario> listarVoluntariosDisponibles() {
        return listaVoluntarios.stream()
                .filter(v -> v.getEstado() == EstadoVoluntario.DISPONIBLE) // Asumiendo que EstadoVoluntario.DISPONIBLE existe
                .collect(Collectors.toList());
    }

    public List<Voluntario> obtenerTodos() {
        return new ArrayList<>(listaVoluntarios);
    }

    public void eliminarVoluntario(String cedula) throws MiExcepcion {
        Voluntario v = buscarVoluntarioPorCedula(cedula);
        if (v == null) {
            throw new MiExcepcion("Voluntario no encontrado.");
        }
        listaVoluntarios.remove(v);
    }
}
