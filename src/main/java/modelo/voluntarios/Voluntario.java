package modelo.voluntarios;

import modelo.usuarios.Usuario;
import modelo.brigada.Brigada;

public abstract class Voluntario extends Usuario {

    protected EstadoVoluntario estado;
    protected Disponibilidad disponibilidad;

    public Voluntario(String id,
                      String nombreCompleto,
                      String email,
                      String telefono) {
        super(id, nombreCompleto, email, telefono);
        this.estado = EstadoVoluntario.ACTIVO;
        this.disponibilidad = Disponibilidad.DISPONIBLE;
    }

    public void cambiarDisponibilidad(Disponibilidad nueva) {
        this.disponibilidad = nueva;
    }

    public boolean estaDisponible() {
        return disponibilidad == Disponibilidad.DISPONIBLE
                && estado == EstadoVoluntario.ACTIVO;
    }

    //Recibe brigada como parametro, segun tipo de voluntario,
    // determina si puede participar en esta
    public abstract boolean puedeParticipar(Brigada brigada);

    //toma actividad como parametro para notificar sobre actividad
    public void notificarConvocatoriaUrgente() {
        System.out.println("Notificando a " + nombre +
                " sobre actividad urgente: ");
    }
    public EstadoVoluntario getEstado() {
        return estado;
    }
    public void setEstado(EstadoVoluntario estado) {
        this.estado = estado;
    }
    public Disponibilidad getDisponibilidad() {
        return disponibilidad;
    }
    public void setDisponibilidad(Disponibilidad disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
}


