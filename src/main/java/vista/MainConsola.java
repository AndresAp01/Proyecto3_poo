package vista;

import logica.GestorActividades;
import logica.GestorBrigadas;
import logica.GestorInventario;
import logica.GestorVoluntarios;
import modelo.actividad.Actividad;
import modelo.actividad.TipoActividad;
import modelo.brigada.Brigada;
import modelo.materiales.RecursoInventario;
import modelo.materiales.TipoRecurso;
import modelo.voluntarios.VoluntarioMedico;
import util.MiExcepcion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MainConsola {

    public static void main(String[] args) {
        System.out.println("=== INICIO DEL SISTEMA (MODO PRUEBA PERSISTENCIA) ===");

        // 1. Instanciar Gestores (Esto cargará los datos automáticamente)
        GestorInventario gestorInventario = new GestorInventario();
        GestorBrigadas gestorBrigadas = new GestorBrigadas();
        GestorActividades gestorActividades = new GestorActividades();
        GestorVoluntarios gestorVoluntarios = new GestorVoluntarios();

        // 2. Mostrar estado inicial
        System.out.println("\n--- ESTADO ACTUAL ---");
        System.out.println("Inventario: " + gestorInventario.obtenerTodoElInventario().size() + " items.");
        System.out.println("Brigadas:   " + gestorBrigadas.obtenerTodasLasBrigadas().size() + " brigadas.");
        System.out.println("Voluntarios:" + gestorVoluntarios.obtenerTodos().size() + " voluntarios.");
        System.out.println("Actividades:" + gestorActividades.obtenerTodas().size() + " actividades.");

        try {
            // 3. Si está vacío, poblamos con datos de prueba
            if (gestorInventario.obtenerTodoElInventario().isEmpty()) {
                System.out.println("\n[!] Sistema vacío. Generando datos de prueba...");
                poblarDatos(gestorInventario, gestorBrigadas, gestorVoluntarios, gestorActividades);
                System.out.println("[!] Datos generados y guardados.");
            } else {
                System.out.println("\n[i] Datos encontrados. No se generarán duplicados.");
            }

            // 4. Listar detalles para verificar integridad
            listarDetalles(gestorInventario, gestorBrigadas, gestorVoluntarios, gestorActividades);

        } catch (Exception e) {
            System.err.println("ERROR FATAL: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n=== FIN DEL PROGRAMA ===");
    }

    private static void poblarDatos(GestorInventario gi, GestorBrigadas gb, GestorVoluntarios gv, GestorActividades ga) throws MiExcepcion {
        // Inventario
        RecursoInventario r1 = gi.agregarRecurso("Vendas", 50, TipoRecurso.MEDICO);
        RecursoInventario r2 = gi.agregarRecurso("Palas", 10, TipoRecurso.HERRAMIENTA);

        // Voluntarios
        VoluntarioMedico vm = new VoluntarioMedico("101", "Dr. House", "house@med.com", "555-1234", LocalDate.now(), "Diagnostico");
        gv.registrarVoluntario(vm);

        // Brigadas
        Brigada bSalud = gb.crearBrigada("salud", "Brigada Roja", "Emergencias");

        // Asignar voluntario a brigada
        // Nota: Aquí hay un detalle de diseño. El voluntario debería ser obtenido del gestor para asegurar que es la misma instancia
        // o trabajar con IDs. El gestor de brigadas recibe el objeto Voluntario.
        gb.agregarVoluntarioABrigada(bSalud.getId(), vm);

        // Actividades
        Actividad act = ga.crearActividad("Campaña Gripe", LocalDateTime.now().plusDays(2), "Parque Central", TipoActividad.algo);
        ga.asignarRecursoAActividad(act.getId(), r1, 20);
    }

    private static void listarDetalles(GestorInventario gi, GestorBrigadas gb, GestorVoluntarios gv, GestorActividades ga) {
        System.out.println("\n--- DETALLE DE DATOS ---");
        System.out.println("1. Inventario:");
        gi.obtenerTodoElInventario().forEach(System.out::println);

        System.out.println("\n2. Voluntarios:");
        gv.obtenerTodos().forEach(v -> System.out.println("- " + v.getNombre() + " (" + v.getId() + ")"));

        System.out.println("\n3. Brigadas:");
        for (Brigada b : gb.obtenerTodasLasBrigadas()) {
            System.out.println("- " + b);
            System.out.println("  Voluntarios asignados: " + b.consultarVoluntarios().size());
        }

        System.out.println("\n4. Actividades:");
        ga.obtenerTodas().forEach(System.out::println);
    }
}
