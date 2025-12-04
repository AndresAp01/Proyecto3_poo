package util;

import modelo.actividad.Actividad;
import modelo.brigada.Brigada;
import modelo.materiales.RecursoInventario;
import modelo.voluntarios.Voluntario;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GeneradorReportes {

    public static void generarReporteGeneral(List<RecursoInventario> inventario,
                                             List<Brigada> brigadas,
                                             List<Voluntario> voluntarios,
                                             List<Actividad> actividades) throws MiExcepcion {

        String nombreArchivo = "Reporte_General_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            writer.write("=== REPORTE GENERAL DEL SISTEMA ===");
            writer.newLine();
            writer.write("Generado el: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            writer.newLine();
            writer.write("===================================");
            writer.newLine();
            writer.newLine();

            // Sección Inventario
            writer.write("--- INVENTARIO ACTUAL ---");
            writer.newLine();
            if (inventario.isEmpty()) writer.write("No hay recursos registrados.");
            for (RecursoInventario r : inventario) {
                writer.write(String.format("- [%d] %s (%s): %d unidades", r.getId(), r.getNombre(), r.getTipo(), r.getCantidadDisponible()));
                writer.newLine();
            }
            writer.newLine();

            // Sección Brigadas
            writer.write("--- BRIGADAS ACTIVAS ---");
            writer.newLine();
            if (brigadas.isEmpty()) writer.write("No hay brigadas registradas.");
            for (Brigada b : brigadas) {
                writer.write(String.format("- [%d] %s (%s) - Estado: %s", b.getId(), b.getNombre(), b.getObjetivoGeneral(), b.getEstado()));
                writer.newLine();
                writer.write("  Voluntarios asignados: " + b.consultarVoluntarios().size());
                writer.newLine();
            }
            writer.newLine();

            // Sección Actividades
            writer.write("--- ACTIVIDADES PLANIFICADAS ---");
            writer.newLine();
            if (actividades.isEmpty()) writer.write("No hay actividades registradas.");
            for (Actividad a : actividades) {
                writer.write(String.format("- [%d] %s en %s (%s)", a.getId(), a.getObjetivo(), a.getLugar(), a.getFechaInicio().toLocalDate()));
                writer.newLine();
                writer.write("  Estado: " + a.getEstado());
                writer.newLine();
            }

        } catch (IOException e) {
            throw new MiExcepcion("Error al escribir el reporte: " + e.getMessage());
        }
    }
}
