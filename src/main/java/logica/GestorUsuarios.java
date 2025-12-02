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
        if (buscarUsuarioPorId(usuario.getId()) != null) {
            throw new MiExcepcion("Ya existe un usuario registrado con el ID: " + usuario.getId());
        }
        listaUsuarios.add(usuario);
    }

    public Usuario buscarUsuarioPorId(String id) {
        return listaUsuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void eliminarUsuario(String id) throws MiExcepcion {
        Usuario usuario = buscarUsuarioPorId(id);
        if (usuario == null) {
            throw new MiExcepcion("Usuario no encontrado con ID: " + id);
        }
        listaUsuarios.remove(usuario);
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return new ArrayList<>(listaUsuarios);
    }
}
