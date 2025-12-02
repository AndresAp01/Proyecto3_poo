package logica;

import modelo.usuarios.Usuario;
import util.MiExcepcion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GestorUsuarios {

    private List<Usuario> listaUsuarios;

    public GestorUsuarios() {
        this.listaUsuarios = new ArrayList<>();
    }

    public void registrarUsuario(Usuario usuario) throws MiExcepcion {
        if (usuario == null) {
            throw new MiExcepcion("El usuario no puede ser nulo.");
        }
        if (buscarUsuarioPorCedula(usuario.getCedula()) != null) {
            throw new MiExcepcion("Ya existe un usuario registrado con la cédula: " + usuario.getCedula());
        }
        listaUsuarios.add(usuario);
    }

    public Usuario buscarUsuarioPorCedula(String cedula) {
        return listaUsuarios.stream()
                .filter(u -> u.getCedula().equals(cedula))
                .findFirst()
                .orElse(null);
    }

    public void eliminarUsuario(String cedula) throws MiExcepcion {
        Usuario usuario = buscarUsuarioPorCedula(cedula);
        if (usuario == null) {
            throw new MiExcepcion("Usuario no encontrado con cédula: " + cedula);
        }
        listaUsuarios.remove(usuario);
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return new ArrayList<>(listaUsuarios);
    }
}
