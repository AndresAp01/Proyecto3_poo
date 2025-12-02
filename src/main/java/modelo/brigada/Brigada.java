package modelo.brigada;

import modelo.voluntarios.Voluntario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Brigada implements Serializable {

    protected int id;
    protected String nombre;
    protected String objetivoGeneral;
    protected EstadoBrigada estado;
    protected List<Voluntario> listaVoluntarios;
    public Brigada(int id, String nombre, String objetivoGeneral) {
        this.id = id;
        this.nombre = nombre;
        this.objetivoGeneral = objetivoGeneral;
        this.estado = EstadoBrigada.PENDIENTE;
        this.listaVoluntarios = new ArrayList<>();
    }

    public void agregarVoluntario(Voluntario voluntario) {
        if (voluntario != null && !listaVoluntarios.contains(voluntario)) {
            listaVoluntarios.add(voluntario);
        }
    }

    public void removerVoluntario(Voluntario voluntario) {
        listaVoluntarios.remove(voluntario);
    }

    public List<Voluntario> consultarVoluntarios() {
        return new ArrayList<>(listaVoluntarios);
    }

    // Método polimórfico principal
    public abstract void ejecutarPlanDeAccion();

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObjetivoGeneral() {
        return objetivoGeneral;
    }

    public void setObjetivoGeneral(String objetivoGeneral) {
        this.objetivoGeneral = objetivoGeneral;
    }

    public EstadoBrigada getEstado() {
        return estado;
    }

    public void setEstado(EstadoBrigada estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Brigada{" + "id=" + id + ", nombre='" + nombre + '\'' + ", estado=" + estado + '}';
    }

    public enum EstadoBrigada {
        PENDIENTE,
        ACTIVA,
        FINALIZADA
    }
}
