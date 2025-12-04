package modelo.brigada;

import modelo.actividad.Actividad;
import modelo.materiales.RecursoInventario;
import java.util.ArrayList;
import java.util.List;

public class BrigadaDeSalud extends Brigada {
    private static final long serialVersionUID = 1L;

    private String tipoAtencion;
    private List<RecursoInventario> inventarioMedico; 

    public BrigadaDeSalud(int id, String nombre, String objetivoGeneral, String tipoAtencion) {
        super(id, nombre, objetivoGeneral);
        this.tipoAtencion = tipoAtencion;
        this.inventarioMedico = new ArrayList<>();
    }

    @Override
    public void ejecutarPlanDeAccion() {
        if (listaActividades.isEmpty()) {
            System.out.println("Sin actividades por realizar");
        }
        System.out.println("Ejecutando plan de salud: " + tipoAtencion);
        Actividad ultima = listaActividades.removeLast();
        ultima.setEstado(Actividad.EstadoActividad.REALIZADA);
        actividadesCompletadas.add(ultima);
        this.estado = EstadoBrigada.ACTIVA;
    }

    public String getTipoAtencion() {
        return tipoAtencion;
    }

    public void setTipoAtencion(String tipoAtencion) {
        this.tipoAtencion = tipoAtencion;
    }

    public List<RecursoInventario> getInventarioMedico() {
        return inventarioMedico;
    }

    public void setInventarioMedico(List<RecursoInventario> inventarioMedico) {
        this.inventarioMedico = inventarioMedico;
    }
}
