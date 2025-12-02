package modelo.brigada;

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
        System.out.println("Desplegando seguridad en zonas: " + zonasDeVigilancia);
        // Lógica específica: rondas, control de perímetros
        this.estado = EstadoBrigada.ACTIVA;
    }

    public List<String> getZonasDeVigilancia() {
        return zonasDeVigilancia;
    }

    public void setZonasDeVigilancia(List<String> zonasDeVigilancia) {
        this.zonasDeVigilancia = zonasDeVigilancia;
    }
}
