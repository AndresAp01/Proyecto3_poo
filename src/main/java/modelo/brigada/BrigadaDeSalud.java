package modelo.brigada;

import modelo.materiales.RecursoInventario;
import java.util.ArrayList;
import java.util.List;

public class BrigadaDeSalud extends Brigada {

    private String tipoAtencion;
    private List<RecursoInventario> inventarioMedico; 

    public BrigadaDeSalud(int id, String nombre, String objetivoGeneral, String tipoAtencion) {
        super(id, nombre, objetivoGeneral);
        this.tipoAtencion = tipoAtencion;
        this.inventarioMedico = new ArrayList<>();
    }

    @Override
    public void ejecutarPlanDeAccion() {
        System.out.println("Ejecutando plan de salud: " + tipoAtencion);
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
