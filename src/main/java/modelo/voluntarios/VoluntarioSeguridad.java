package modelo.voluntarios;

import modelo.brigada.Brigada;

public class VoluntarioSeguridad extends Voluntario {
    private String tipoSeguridad; // p.ej. "Nacional", "Vial"

    public VoluntarioSeguridad(String id,
                            String nombreCompleto,
                            String email,
                            String telefono,
                            String tipoSeguridad) {
        super(id, nombreCompleto, email, telefono);
        this.tipoSeguridad = tipoSeguridad;
    }

    @Override
    public boolean puedeParticipar(Brigada brigada) {
        // Ejemplo de regla: solo participa si la brigada es de tipo SEGURIDAD
        return brigada.getClass().getSimpleName().equals("BrigadaDeSeguridad");
    }
}
