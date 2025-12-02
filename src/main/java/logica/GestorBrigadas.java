package logica;

import modelo.brigada.Brigada;
import modelo.brigada.BrigadaDeInfraestructura;
import modelo.brigada.BrigadaDeSalud;
import modelo.brigada.BrigadaDeSeguridad;
import modelo.voluntarios.Voluntario;
import util.GestorArchivos;
import util.MiExcepcion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GestorBrigadas implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String ARCHIVO_BRIGADAS = "brigadas.dat";
    private List<Brigada> listaBrigadas;
    private int contadorIds;

    public GestorBrigadas() {
        this.listaBrigadas = new ArrayList<>();
        this.contadorIds = 1;
        cargarDatos();
    }

    /**
     * Crea una nueva brigada del tipo especificado.
     *
     * @param tipoBrigada         Tipo de brigada a crear (Salud, Infraestructura, Seguridad).
     * @param nombre              Nombre de la brigada.
     * @param objetivoGeneral     Objetivo general de la brigada.
     * @param parametroEspecifico Parámetro específico según el tipo de brigada (e.g., tipoAtencion para Salud).
     * @return La brigada recién creada.
     * @throws MiExcepcion Si el tipo de brigada es inválido o faltan parámetros.
     */
    public Brigada crearBrigada(String tipoBrigada, String nombre, String objetivoGeneral, String parametroEspecifico) throws MiExcepcion {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new MiExcepcion("El nombre de la brigada no puede ser vacío.");
        }
        if (objetivoGeneral == null || objetivoGeneral.trim().isEmpty()) {
            throw new MiExcepcion("El objetivo general no puede ser vacío.");
        }

        Brigada nuevaBrigada;
        switch (tipoBrigada.toLowerCase()) {
            case "salud":
                nuevaBrigada = new BrigadaDeSalud(contadorIds++, nombre, objetivoGeneral, parametroEspecifico);
                break;
            case "infraestructura":
                nuevaBrigada = new BrigadaDeInfraestructura(contadorIds++, nombre, objetivoGeneral, parametroEspecifico);
                break;
            case "seguridad":
                nuevaBrigada = new BrigadaDeSeguridad(contadorIds++, nombre, objetivoGeneral);
                // Las zonas de vigilancia se añaden por separado
                break;
            default:
                throw new MiExcepcion("Tipo de brigada inválido: " + tipoBrigada);
        }
        listaBrigadas.add(nuevaBrigada);
        guardarDatos();
        return nuevaBrigada;
    }

    public Brigada buscarBrigadaPorId(int id) {
        return listaBrigadas.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void eliminarBrigada(int id) throws MiExcepcion {
        Brigada brigada = buscarBrigadaPorId(id);
        if (brigada == null) {
            throw new MiExcepcion("Brigada no encontrada con ID: " + id);
        }
        // Lógica adicional: reasignar voluntarios antes de eliminar la brigada
        if (!brigada.consultarVoluntarios().isEmpty()) {
            // Aquí se debería llamar a GestorVoluntarios para liberar a los voluntarios
            System.out.println("Advertencia: Brigada " + brigada.getNombre() + " eliminada, sus voluntarios deberán ser reasignados manualmente.");
            // Esto es un placeholder. En un sistema real, se gestionaría con el GestorVoluntarios
        }
        listaBrigadas.remove(brigada);
        guardarDatos();
    }

    public void agregarVoluntarioABrigada(int idBrigada, Voluntario voluntario) throws MiExcepcion {
        Brigada brigada = buscarBrigadaPorId(idBrigada);
        if (brigada == null) {
            throw new MiExcepcion("Brigada no encontrada.");
        }
        if (voluntario == null) {
            throw new MiExcepcion("Voluntario inválido.");
        }
        if (!voluntario.puedeParticipar(brigada)) {
            throw new MiExcepcion("Voluntario inválido.");
        }
        brigada.agregarVoluntario(voluntario);
        guardarDatos();
        // Aquí se debería actualizar el estado del voluntario si es necesario (e.g., OCUPADO)
    }

    public void removerVoluntarioDeBrigada(int idBrigada, Voluntario voluntario) throws MiExcepcion {
        Brigada brigada = buscarBrigadaPorId(idBrigada);
        if (brigada == null) {
            throw new MiExcepcion("Brigada no encontrada.");
        }
        if (voluntario == null) {
            throw new MiExcepcion("Voluntario inválido.");
        }
        brigada.removerVoluntario(voluntario);
        guardarDatos();
        // Aquí se debería actualizar el estado del voluntario si es necesario (e.g., DISPONIBLE)
    }

    public List<Brigada> obtenerTodasLasBrigadas() {
        return new ArrayList<>(listaBrigadas);
    }

    public List<Brigada> listarBrigadasPorEstado(Brigada.EstadoBrigada estado) {
        return listaBrigadas.stream()
                .filter(b -> b.getEstado() == estado)
                .collect(Collectors.toList());
    }

    public void guardarDatos() {
        try {
            GestorArchivos.guardarObjeto(ARCHIVO_BRIGADAS, new BrigadasData(listaBrigadas, contadorIds));
        } catch (MiExcepcion e) {
            System.err.println("Error al guardar brigadas: " + e.getMessage());
        }
    }

    public void cargarDatos() {
        try {
            BrigadasData datosCargados = GestorArchivos.cargarObjeto(ARCHIVO_BRIGADAS, BrigadasData.class);
            if (datosCargados != null) {
                this.listaBrigadas = datosCargados.getListaBrigadas();
                this.contadorIds = datosCargados.getContadorIds();
            }
        } catch (MiExcepcion e) {
            System.out.println("No se encontraron datos previos de brigadas o error al cargar (se inicia vacio).");
        }
    }

    // Clase interna para persistencia
    private static class BrigadasData implements Serializable {
        private static final long serialVersionUID = 1L;
        private List<Brigada> listaBrigadas;
        private int contadorIds;

        public BrigadasData(List<Brigada> listaBrigadas, int contadorIds) {
            this.listaBrigadas = listaBrigadas;
            this.contadorIds = contadorIds;
        }

        public List<Brigada> getListaBrigadas() {
            return listaBrigadas;
        }

        public int getContadorIds() {
            return contadorIds;
        }
    }
}
