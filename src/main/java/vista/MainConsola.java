package vista;

import logica.GestorActividades;
import logica.GestorBrigadas;
import logica.GestorInventario;
import logica.GestorVoluntarios;
import modelo.actividad.Actividad;
import modelo.brigada.Brigada;
import modelo.materiales.RecursoInventario;
import modelo.voluntarios.VoluntarioMedico;
import util.MiExcepcion;
import util.GestorArchivos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MainConsola {

    public static void main(String[] args) {
        System.out.println("--- PRUEBAS DE CONSOLA DEL SISTEMA DE BRIGADAS (EXTENDIDO) ---");

        GestorInventario gestorInventario = new GestorInventario();
        GestorBrigadas gestorBrigadas;
        GestorActividades gestorActividades = new GestorActividades();
        GestorVoluntarios gestorVoluntarios = new GestorVoluntarios();

        try {
            gestorBrigadas = GestorArchivos.cargarObjeto("brigadas.dat", GestorBrigadas.class);
            System.out.println("carga exitosa");
        } catch (MiExcepcion e) {
            System.err.println("No se pudo cargar GestorBrigadas, se creará uno nuevo: " + e.getMessage());
            gestorBrigadas = new GestorBrigadas();
        }

        try {
            // --- PRUEBAS DE INVENTARIO ---
            System.out.println("\n--- 1. Gestión de Inventario ---");
            RecursoInventario suero = gestorInventario.agregarRecurso("Suero Fisiológico", 100, RecursoInventario.TipoRecurso.MEDICO);
            RecursoInventario martillo = gestorInventario.agregarRecurso("Martillo", 5, RecursoInventario.TipoRecurso.HERRAMIENTA);

            System.out.println("-> Inventario inicial: " + gestorInventario.obtenerTodoElInventario());

            // Prueba de Eliminación
            RecursoInventario temp = gestorInventario.agregarRecurso("Temporal", 1, RecursoInventario.TipoRecurso.OTRO);
            System.out.println("-> Recurso temporal creado: " + temp.getNombre());
            gestorInventario.eliminarRecurso(temp.getId());
            System.out.println("-> Recurso temporal eliminado. ¿Existe aún? " + (gestorInventario.buscarRecursoPorId(temp.getId()) != null));


            // --- PRUEBAS DE BRIGADAS ---
            System.out.println("\n--- 2. Gestión de Brigadas ---");
            Brigada brigadaSalud = gestorBrigadas.crearBrigada("salud", "Brigada Alpha", "Atención médica primaria", "General");
            Brigada brigadaInfra = gestorBrigadas.crearBrigada("infraestructura", "Brigada Beta", "Reparación de caminos", "Sector Este");
            System.out.println("-> Brigadas creadas: " + gestorBrigadas.obtenerTodasLasBrigadas().size());


            // --- PRUEBAS DE VOLUNTARIOS ---
            System.out.println("\n--- 3. Gestión de Voluntarios ---");
            VoluntarioMedico medico1 = new VoluntarioMedico("111111111", "Dr. Smith", "smith@hospital.com", "8888-8888", LocalDate.now(), "Cardiología");

            // Registro en el Gestor
            gestorVoluntarios.registrarVoluntario(medico1);
            System.out.println("-> Voluntario registrado: " + gestorVoluntarios.buscarVoluntarioPorId("111111111").getNombre());

            // Listado de Disponibles
            System.out.println("-> Voluntarios disponibles: " + gestorVoluntarios.listarVoluntariosDisponibles().size());

            // Asignación a Brigada
            gestorBrigadas.agregarVoluntarioABrigada(brigadaSalud.getId(), medico1);
            System.out.println("-> Voluntarios en Brigada Alpha tras asignación: " + brigadaSalud.consultarVoluntarios());


            // --- PRUEBAS DE ACTIVIDADES ---
            System.out.println("\n--- 4. Gestión de Actividades ---");
            Actividad campana = gestorActividades.crearActividad("Vacunación Masiva", LocalDateTime.now().plusDays(7), "Plaza Central");
            gestorActividades.asignarRecursoAActividad(campana.getId(), suero, 50);
            System.out.println("-> Actividad creada: " + campana.getObjetivo() + " | Recursos: " + campana.getRecursosAsignados());


            // --- EJECUCIÓN ---
            System.out.println("\n--- 5. Ejecución de Planes ---");
            brigadaSalud.ejecutarPlanDeAccion();
            brigadaInfra.ejecutarPlanDeAccion();

            System.out.println("\n--- PRUEBA FINALIZADA CON ÉXITO ---");

            try {
                GestorArchivos.guardarObjeto("brigadas.dat", gestorBrigadas);
            } catch (MiExcepcion e) {
                System.err.println(e.getMessage());
            }



        } catch (MiExcepcion e) {
            System.err.println("ERROR CONTROLADO: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERROR INESPERADO: " + e.getMessage());
            e.printStackTrace();
        }
    }
}