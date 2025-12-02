package logica;

import java.io.Serializable;
import util.MiExcepcion;

public class Control implements Serializable {

    private static final long serialVersionUID = 1L;

    private GestorUsuarios gestorUsuarios;
    private GestorVoluntarios gestorVoluntarios;
    private GestorBrigadas gestorBrigadas;
    private GestorActividades gestorActividades;
    private GestorInventario gestorInventario;

    public Control() {
        // Aquí decides si los gestores se cargan de archivo o se crean vacíos
        this.gestorUsuarios.cargarDatos();       // si implementas cargar()
        this.gestorVoluntarios.cargarDatos();
        this.gestorBrigadas.cargarDatos();
        this.gestorActividades.cargarDatos();
        this.gestorInventario.cargarDatos();
    }

    // Getters por si la UI necesita acceso directo (opcional)
    public GestorVoluntarios getGestorVoluntarios() {
        return gestorVoluntarios;
    }

    public GestorBrigadas getGestorBrigadas() {
        return gestorBrigadas;
    }

    public GestorActividades getGestorActividades() {
        return gestorActividades;
    }

    public GestorInventario getGestorInventario() {
        return gestorInventario;
    }

    public GestorUsuarios getGestorUsuarios() {
        return gestorUsuarios;
    }

    public void guardarTodo() throws MiExcepcion {
        gestorUsuarios.guardarDatos();
        gestorVoluntarios.guardarDatos();
        gestorBrigadas.guardarDatos();
        gestorActividades.guardarDatos();
        gestorInventario.guardarDatos();
    }
}