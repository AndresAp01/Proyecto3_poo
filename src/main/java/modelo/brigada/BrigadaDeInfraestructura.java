package modelo.brigada;

import java.util.ArrayList;
import java.util.List;

public class BrigadaDeInfraestructura extends Brigada {

    private String areaAsignada;
    private List<String> inventarioHerramientas; // String provisional hasta tener clase Herramienta

    public BrigadaDeInfraestructura(int id, String nombre, String objetivoGeneral, String areaAsignada) {
        super(id, nombre, objetivoGeneral);
        this.areaAsignada = areaAsignada;
        this.inventarioHerramientas = new ArrayList<>();
    }

    @Override
    public void ejecutarPlanDeAccion() {
        System.out.println("Iniciando obras de infraestructura en área: " + areaAsignada);
        // Lógica específica: reparación, construcción, limpieza de escombros
        this.estado = EstadoBrigada.ACTIVA;
    }

    public String getAreaAsignada() {
        return areaAsignada;
    }

    public void setAreaAsignada(String areaAsignada) {
        this.areaAsignada = areaAsignada;
    }

    public List<String> getInventarioHerramientas() {
        return inventarioHerramientas;
    }

    public void setInventarioHerramientas(List<String> inventarioHerramientas) {
        this.inventarioHerramientas = inventarioHerramientas;
    }
}
