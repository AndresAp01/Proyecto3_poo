package vista;

import logica.GestorActividades;
import logica.GestorBrigadas;
import logica.GestorInventario;
import modelo.actividad.Actividad;
import modelo.brigada.Brigada;
import modelo.materiales.RecursoInventario;
import modelo.usuarios.Usuario; // Asumiendo que Usuario.java está en modelo.usuarios
import modelo.voluntarios.EstadoVoluntario;
import modelo.voluntarios.Voluntario; // Asumiendo que Voluntario.java está en modelo.voluntarios
import modelo.voluntarios.VoluntarioMedico; // Ejemplo de voluntario concreto
import util.MiExcepcion;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MainConsola {

    public static void main(String[] args) {
        System.out.println("--- PRUEBAS DE CONSOLA DEL SISTEMA DE BRIGADAS ---");

        GestorInventario gestorInventario = new GestorInventario();
        GestorBrigadas gestorBrigadas = new GestorBrigadas();
        GestorActividades gestorActividades = new GestorActividades();
        // GestorUsuarios gestorUsuarios = new GestorUsuarios(); // Se deja comentado por simplicidad
        // GestorVoluntarios gestorVoluntarios = new GestorVoluntarios(); // Se deja comentado por simplicidad


        try {
            // --- PRUEBAS DE INVENTARIO ---
            System.out.println("\n--- Gestión de Inventario ---");
            RecursoInventario suero = gestorInventario.agregarRecurso("Suero Fisiológico", 100, RecursoInventario.TipoRecurso.MEDICO);
            RecursoInventario martillo = gestorInventario.agregarRecurso("Martillo", 5, RecursoInventario.TipoRecurso.HERRAMIENTA);
            RecursoInventario cemento = gestorInventario.agregarRecurso("Cemento 50kg", 20, RecursoInventario.TipoRecurso.MATERIAL_CONSTRUCCION);

            System.out.println("Inventario actual: " + gestorInventario.obtenerTodoElInventario());
            System.out.println("Buscando suero (ID " + suero.getId() + "): " + gestorInventario.buscarRecursoPorId(suero.getId()).getNombre());

            // Consumir un recurso (el martillo)
            RecursoInventario martilloEncontrado = gestorInventario.buscarRecursoPorId(martillo.getId());
            if (martilloEncontrado != null && martilloEncontrado.consumirCantidad(2)) {
                System.out.println("Se consumieron 2 martillos. Quedan: " + martilloEncontrado.getCantidadDisponible());
            }

            // --- PRUEBAS DE BRIGADAS ---
            System.out.println("\n--- Gestión de Brigadas ---");
            Brigada brigadaSalud = gestorBrigadas.crearBrigada("salud", "Brigada Alpha", "Atención médica primaria", "General");
            Brigada brigadaInfra = gestorBrigadas.crearBrigada("infraestructura", "Brigada Beta", "Reparación de caminos", "Sector Este");
            // Brigada brigadaSeg = gestorBrigadas.crearBrigada("seguridad", "Brigada Gamma", "Vigilancia", null); // Los parámetros se añadirían después para seguridad

            System.out.println("Brigadas creadas: " + gestorBrigadas.obtenerTodasLasBrigadas());

            // --- PRUEBAS DE VOLUNTARIOS ---
            System.out.println("\n--- Gestión de Voluntarios ---");
            // Constructor correcto: ID, Nombre, Email, Telefono, Fecha, Especialidad
            VoluntarioMedico medico1 = new VoluntarioMedico("111111111", "Dr. Smith", "dr.smith@test.com", "8888-8888", LocalDate.now(), "Cardiología");

            // Aquí deberíamos registrar al voluntario en un GestorVoluntarios si lo hubiéramos instanciado
            // gestorVoluntarios.registrarVoluntario(medico1);

            gestorBrigadas.agregarVoluntarioABrigada(brigadaSalud.getId(), medico1);
            System.out.println("Voluntarios en Brigada Alpha: " + brigadaSalud.consultarVoluntarios());

            // --- PRUEBAS DE ACTIVIDADES ---
            System.out.println("\n--- Gestión de Actividades ---");
            Actividad campanaSalud = gestorActividades.crearActividad("Campaña de vacunación", LocalDateTime.now().plusDays(7), "Centro Comunal");
            gestorActividades.asignarRecursoAActividad(campanaSalud.getId(), suero, 50);
            System.out.println("Actividad creada: " + campanaSalud.getObjetivo());
            System.out.println("Recursos asignados a campaña de salud: " + campanaSalud.getRecursosAsignados());

            // --- EJECUCIÓN DE PLAN DE ACCIÓN DE BRIGADA ---
            System.out.println("\n--- Ejecución de Planes de Acción ---");
            brigadaSalud.ejecutarPlanDeAccion();
            brigadaInfra.ejecutarPlanDeAccion();

        } catch (MiExcepcion e) {
            System.err.println("Error en la aplicación: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
