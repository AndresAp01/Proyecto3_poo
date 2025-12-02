package modelo.voluntarios;

import modelo.brigada.Brigada;

public class VoluntarioManoObra extends Voluntario {
    private String tipoDeObra; // p.ej. "Plomeria", "Construccion", "Electricidad"

    public VoluntarioManoObra(String id,
                               String nombreCompleto,
                               String email,
                               String telefono,
                               String tipoDeObra) {
        super(id, nombreCompleto, email, telefono);
        this.tipoDeObra = tipoDeObra;
    }

    @Override
    public boolean puedeParticipar(Brigada brigada) {
        // Ejemplo de regla: solo participa si la brigada es de tipo Infraestructura
        return brigada.getClass().getSimpleName().equals("BrigadaDeInfraestructura");
    }
}