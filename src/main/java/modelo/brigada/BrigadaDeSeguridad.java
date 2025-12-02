package modelo.brigada;

import modelo.actividad.Actividad;

import java.util.ArrayList;
import java.util.List;

public class BrigadaDeSeguridad extends Brigada {
    private static final long serialVersionUID = 1L;

    private List<String> zonasDeVigilancia;

    public BrigadaDeSeguridad(int id, String nombre, String objetivoGeneral) {
        super(id, nombre, objetivoGeneral);
        this.zonasDeVigilancia = new ArrayList<>();
    }

    public void agregarZona(String zona) {
        if (zona != null && !zonasDeVigilancia.contains(zona)) {
            zonasDeVigilancia.add(zona);
        }
    }

    @Override
    public void ejecutarPlanDeAccion() {
        if (listaActividades.isEmpty()) {
            System.out.println("Sin actividades por realizar");
        }
        System.out.println("Desplegando seguridad en zonas: " + zonasDeVigilancia);
        Actividad ultima = listaActividades.getLast();
        ultima.setEstado(Actividad.EstadoActividad.EN_PROGRESO);
        this.estado = EstadoBrigada.ACTIVA;
    }

    public List<String> getZonasDeVigilancia() {
        return zonasDeVigilancia;
    }

    public void setZonasDeVigilancia(List<String> zonasDeVigilancia) {
        this.zonasDeVigilancia = zonasDeVigilancia;
    }
}
