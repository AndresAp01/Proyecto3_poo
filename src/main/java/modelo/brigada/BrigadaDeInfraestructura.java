package modelo.brigada;

import modelo.actividad.Actividad;
import modelo.materiales.RecursoInventario;
import java.util.ArrayList;
import java.util.List;

public class BrigadaDeInfraestructura extends Brigada {
    private static final long serialVersionUID = 1L;

    private String areaAsignada;
    private List<RecursoInventario> inventarioHerramientas;

    public BrigadaDeInfraestructura(int id, String nombre, String objetivoGeneral, String areaAsignada) {
        super(id, nombre, objetivoGeneral);
        this.areaAsignada = areaAsignada;
        this.inventarioHerramientas = new ArrayList<>();
    }

    @Override
    public void ejecutarPlanDeAccion() {
        if (listaActividades.isEmpty()) {
            System.out.println("Sin actividades por realizar");
        }
        System.out.println("Iniciando obras de infraestructura en área: " + areaAsignada);
        Actividad ultima = listaActividades.removeLast();
        ultima.setEstado(Actividad.EstadoActividad.REALIZADA);
        actividadesCompletadas.add(ultima);

        this.estado = EstadoBrigada.ACTIVA;
    }

    public String getAreaAsignada() {
        return areaAsignada;
    }

    public void setAreaAsignada(String areaAsignada) {
        this.areaAsignada = areaAsignada;
    }

    public List<RecursoInventario> getInventarioHerramientas() {
        return inventarioHerramientas;
    }

    public void setInventarioHerramientas(List<RecursoInventario> inventarioHerramientas) {
        this.inventarioHerramientas = inventarioHerramientas;
    }
}
