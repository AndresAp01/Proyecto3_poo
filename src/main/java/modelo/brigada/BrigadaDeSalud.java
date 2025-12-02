package modelo.brigada;

import java.util.ArrayList;
import java.util.List;

public class BrigadaDeSalud extends Brigada {

    // Atributos específicos (según bitácora)
    // Nota: inventarioMedico se podría refinar con clases reales de Inventario luego, 
    // por ahora usaré una lista de Strings o Object genérico si no existe la clase Medicamento aún.
    // Usaré String provisionalmente para no romper la compilación hasta tener la clase Material/Medicamento.
    private String tipoAtencion;
    private List<String> inventarioMedico;

    public BrigadaDeSalud(int id, String nombre, String objetivoGeneral, String tipoAtencion) {
        super(id, nombre, objetivoGeneral);
        this.tipoAtencion = tipoAtencion;
        this.inventarioMedico = new ArrayList<>();
    }

    @Override
    public void ejecutarPlanDeAccion() {
        System.out.println("Ejecutando plan de salud: " + tipoAtencion);
        // Lógica específica: triaje, atención primaria, vacunación, etc.
        this.estado = EstadoBrigada.ACTIVA;
    }

    public String getTipoAtencion() {
        return tipoAtencion;
    }

    public void setTipoAtencion(String tipoAtencion) {
        this.tipoAtencion = tipoAtencion;
    }

    public List<String> getInventarioMedico() {
        return inventarioMedico;
    }

    public void setInventarioMedico(List<String> inventarioMedico) {
        this.inventarioMedico = inventarioMedico;
    }
}
