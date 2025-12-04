package modelo.brigada;

import modelo.actividad.Actividad;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BrigadaDeSeguridad extends Brigada {
    private static final long serialVersionUID = 1L;

    public BrigadaDeSeguridad(int id, String nombre, String objetivoGeneral) {
        super(id, nombre, objetivoGeneral);
    }

    @Override
    public void ejecutarPlanDeAccion() {
        if (listaActividades.isEmpty()) {
            System.out.println("Sin actividades por realizar");
        }

        Actividad ultima = listaActividades.removeLast();
        ultima.setFechaFin(LocalDateTime.now());
        System.out.println("Desplegando seguridad en zonas: " + ultima.getLugar());
        ultima.setEstado(Actividad.EstadoActividad.REALIZADA);
        actividadesCompletadas.add(ultima);
    }
}
