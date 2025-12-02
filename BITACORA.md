# Bitácora del Proyecto 3

Este documento servirá como un diario para registrar las decisiones, cambios y propuestas de diseño a lo largo del
desarrollo del proyecto.

## 2025-11-29: Propuesta de Rediseño Inicial

### Problema a Resolver

Tras la retroalimentación de la profesora, se identificó que la propuesta inicial carecía de un problema claro y no
justificaba el uso de conceptos avanzados de Programación Orientada a Objetos (POO). El proyecto se percibía como una
simple aplicación transaccional.

### Propuesta de Solución

Para solucionar esto, se propone rediseñar el núcleo del sistema en torno a una jerarquía de **Brigadas**, utilizando
herencia y polimorfismo como conceptos centrales.

#### 1. Clase Abstracta: `Brigada`

Se definió una clase base abstracta que contiene los atributos y comportamientos comunes a todas las brigadas,
incluyendo la gestión de voluntarios y un método abstracto para su plan de acción.

#### 2. Clases Hijas (Especializadas)

Se crearon clases concretas que heredan de `Brigada` para representar los diferentes tipos de equipos, cada una con
atributos y lógica específica para la implementación de su plan de acción:

- **`BrigadaDeSalud`**: Atributos relacionados con la atención médica y gestión de inventario médico.
- **`BrigadaDeInfraestructura`**: Atributos para el manejo de áreas asignadas y herramientas.
- **`BrigadaDeSeguridad`**: Atributos para definir y gestionar zonas de vigilancia.

### Beneficios de este Enfoque

- **Herencia y Polimorfismo:** Se integran de forma natural y justificada.
- **Problema Definido:** El sistema se enfocará en "gestionar y coordinar un conjunto de brigadas especializadas para
  responder a diversas necesidades comunitarias".
- **Justificación para Interfaces:** Se puede crear una interfaz `IMaterial` para ser implementada por `Herramienta` y
  `Medicamento`, permitiendo una gestión de inventario genérica.
- **Justificación para Archivos y Excepciones:** Se necesitarán archivos para persistir el estado del sistema y
  excepciones para manejar situaciones como voluntarios no disponibles o recursos inexistentes.

## 2025-12-01: Confirmación de Diseño y Plan de Implementación

### Decisión de Diseño (Modelo de Clases)

Se ha consolidado la arquitectura del sistema fusionando la propuesta de la bitácora con la estructura de voluntarios
del UML original.

1. **Jerarquía de Brigadas:** Se implementará `Brigada` como clase abstracta con `BrigadaDeSalud`,
   `BrigadaDeInfraestructura` y `BrigadaDeSeguridad` como hijas concretas. Esto permite polimorfismo en el método
   `ejecutarPlanDeAccion()`.
2. **Jerarquía de Voluntarios:** Se mantendrá `Voluntario` como clase abstracta (heredando de `Usuario`) con subclases
   especializadas (`VoluntarioMedico`, `VoluntarioManoObra`, etc.).
3. **Interacción:** `Brigada` tendrá una lista polimórfica `ArrayList<Voluntario>`, permitiendo asignar cualquier tipo
   de voluntario a cualquier brigada, aunque se podrán implementar validaciones lógicas (ej. solo médicos en brigadas de
   salud) si es necesario.

### Próximos Pasos

- Crear estructura de paquetes (Modelo, Vista, Controlador/Negocio).
- Implementar clases base abstractas (`Brigada`, `Voluntario`, `Usuario`).
- Implementar clases hijas y lógica de negocio.

## 2025-12-01: Implementación Inicial del Modelo

### Avances Realizados

Se ha iniciado la codificación del proyecto estableciendo la estructura base y las clases principales del dominio.

#### 1. Estructura de Paquetes

Se definió la estructura bajo `src/main/java`:

- `vista`: Contiene `Main.java` para el arranque de JavaFX.
- `modelo`: Clases base.
- `modelo.brigada`: Jerarquía de brigadas.
- `modelo.voluntarios`: Jerarquía de voluntarios.

#### 2. Clases Implementadas

- **`modelo.Usuario`**: Clase abstracta padre que representa la información base de cualquier usuario.
- **`modelo.voluntarios.Voluntario`**: Clase abstracta que hereda de `Usuario`, añadiendo detalles de disponibilidad y
  fecha de ingreso.
- **`modelo.brigada.Brigada`**: Clase abstracta base para todas las brigadas.
- **Subclases de Brigada**: `BrigadaDeSalud`, `BrigadaDeInfraestructura`, `BrigadaDeSeguridad`, cada una con sus
  especializaciones y atributos correspondientes.

#### 3. Actualización de Documentación

- Se actualizó `UML.md` para reflejar fielmente la jerarquía implementada de brigadas y sus atributos específicos.

### Pendientes Inmediatos

- Definir el modelo de **Inventario** (`RecursoInventario`) para reemplazar las listas de `String` temporales en las
  brigadas.
- Implementar las subclases concretas de `Voluntario`.
- Crear el modelo de `Actividad`.

## 2025-12-01: Desarrollo de Componentes Clave (Inventario, Actividades, Errores)

### Avances Realizados

Se ha progresado en la implementación de componentes esenciales del modelo:

#### 1. Modelo de Inventario

- Se creó la clase `modelo.materiales.RecursoInventario`, incluyendo un `Enum TipoRecurso` para manejar diferentes tipos
  de materiales de forma unificada y minimalista.
- Las clases `modelo.brigada.BrigadaDeSalud` y `modelo.brigada.BrigadaDeInfraestructura` fueron actualizadas para
  utilizar `List<RecursoInventario>` en sus atributos de inventario, reemplazando las listas de `String` provisionales.

#### 2. Modelo de Actividades

- Se implementó la clase `modelo.actividad.Actividad`, que incluye `id`, `objetivo`, `fecha`, `lugar`, `estado` (Enum
  interno) y `resultados`.
- Se adoptó una estrategia de optimización: en lugar de crear una clase `InventarioAsignado` separada, `Actividad`
  utiliza un `Map<RecursoInventario, Integer>` para gestionar los recursos asignados, reduciendo el número total de
  archivos.

#### 3. Manejo de Errores

- Se creó la clase `util.MiExcepcion`, una excepción personalizada que hereda de
  `Exception`, permitiendo un manejo de errores más específico y centralizado.

### Próximos Pasos

- Retomar la creación de las clases base `modelo.Usuario` y `modelo.voluntarios.Voluntario` (y `vista.Main`) que se
  perdieron o no se persistieron correctamente al inicio de la sesión.
- Implementar las subclases concretas de `Voluntario`.
- Desarrollar los Gestores (por ejemplo, `GestorBrigadas`) para empezar a integrar la lógica de negocio y la interacción
  entre los modelos.