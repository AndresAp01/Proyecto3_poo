package modelo.voluntarios;

import modelo.brigada.Brigada;

public class VoluntarioMedico extends Voluntario {
    private String especialidadMedica; // p.ej. "Pediatría", "General"

    public VoluntarioMedico(String id,
                            String nombreCompleto,
                            String email,
                            String telefono,
                            String especialidadMedica) {
        super(id, nombreCompleto, email, telefono);
        this.especialidadMedica = especialidadMedica;
    }

    @Override
    public boolean puedeParticipar(Brigada brigada) {
        // Ejemplo de regla: solo participa si la brigada es de tipo SALUD
        return brigada.getClass().getSimpleName().equals("BrigadaDeSalud");
    }
}
