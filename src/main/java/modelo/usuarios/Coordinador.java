package modelo.usuarios;

import logica.Control;
import modelo.actividad.Actividad;
import modelo.brigada.Brigada;
import modelo.voluntarios.Voluntario;
import util.MiExcepcion;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Coordinador extends Usuario {

    private static final long serialVersionUID = 1L;
    private String codigoEmpleado;

    public Coordinador(String id,
                       String nombreCompleto,
                       String email,
                       String telefono,
                       String codigoEmpleado) {
        super(id, nombreCompleto, email, telefono);
        this.codigoEmpleado = codigoEmpleado;
    }

    public void registrarBrigada(Control control, String tipoBrigada, String nombre, String objetivoGeneral, String parametroEspecifico) throws MiExcepcion {
        control.getGestorBrigadas().crearBrigada(tipoBrigada, nombre, objetivoGeneral, parametroEspecifico);
    }

    public void planificarActividad(Control control, String objetivo, LocalDateTime fecha, String lugar) throws MiExcepcion {
        control.getGestorActividades().crearActividad(objetivo, fecha, lugar);
    }
}
