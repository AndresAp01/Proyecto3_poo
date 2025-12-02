package logica;

import modelo.usuarios.Usuario;
import util.GestorArchivos;
import util.MiExcepcion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GestorUsuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String ARCHIVO_USUARIOS = "usuarios.dat";
    private List<Usuario> listaUsuarios;

    public GestorUsuarios() {
        this.listaUsuarios = new ArrayList<>();
        cargarDatos();
    }

    public void registrarUsuario(Usuario usuario) throws MiExcepcion {
        if (usuario == null) {
            throw new MiExcepcion("El usuario no puede ser nulo.");
        }
        if (buscarUsuarioPorId(usuario.getId()) != null) {
            throw new MiExcepcion("Ya existe un usuario registrado con el ID: " + usuario.getId());
        }
        listaUsuarios.add(usuario);
        guardarDatos();
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
        guardarDatos();
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return new ArrayList<>(listaUsuarios);
    }

    private void guardarDatos() {
        try {
            GestorArchivos.guardarObjeto(ARCHIVO_USUARIOS, (ArrayList<Usuario>) listaUsuarios);
        } catch (MiExcepcion e) {
            System.err.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    private void cargarDatos() {
        try {
            ArrayList<Usuario> datosCargados = GestorArchivos.cargarObjeto(ARCHIVO_USUARIOS, ArrayList.class);
            if (datosCargados != null) {
                this.listaUsuarios = datosCargados;
            }
        } catch (MiExcepcion e) {
            System.out.println("No se encontraron datos previos de usuarios o error al cargar (se inicia vacio).");
        }
    }
}
