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

Se definirá una clase base abstracta que contendrá los atributos y comportamientos comunes a todas las brigadas.

- **Atributos comunes:**
    - `nombre`: String
    - `objetivoGeneral`: String
    - `estado`: Enum (Ej: PENDIENTE, ACTIVA, FINALIZADA)
    - `listaVoluntarios`: `ArrayList<Voluntario>`
- **Métodos comunes:**
    - `agregarVoluntario(Voluntario v)`
    - `removerVoluntario(Voluntario v)`
    - `consultarVoluntarios()`
- **Método Abstracto:**
    - `ejecutarPlanDeAccion()`: Este método deberá ser implementado por cada tipo de brigada, permitiendo un
      comportamiento polimórfico.

#### 2. Clases Hijas (Especializadas)

Se crearán clases concretas que hereden de `Brigada` para representar los diferentes tipos de equipos.

- **`BrigadaDeSalud`**
    - **Atributos propios:** `tipoAtencion` (String), `inventarioMedico` (`ArrayList<Medicamento>`)
    - **Implementación de `ejecutarPlanDeAccion()`:** Lógica específica para atención médica.
- **`BrigadaDeInfraestructura`**
    - **Atributos propios:** `areaAsignada` (String), `inventarioHerramientas` (`ArrayList<Herramienta>`)
    - **Implementación de `ejecutarPlanDeAccion()`:** Lógica para la reparación o construcción.
- **`BrigadaDeSeguridad`**
    - **Atributos propios:** `zonasDeVigilancia` (`ArrayList<String>`)
    - **Implementación de `ejecutarPlanDeAccion()`:** Lógica para realizar rondas o monitorizar áreas.

### Beneficios de este Enfoque

- **Herencia y Polimorfismo:** Se integran de forma natural y justificada.
- **Problema Definido:** El sistema se enfocará en "gestionar y coordinar un conjunto de brigadas especializadas para
  responder a diversas necesidades comunitarias".
- **Justificación para Interfaces:** Se puede crear una interfaz `IMaterial` para ser implementada por `Herramienta` y
  `Medicamento`, permitiendo una gestión de inventario genérica.
- **Justificación para Archivos y Excepciones:** Se necesitarán archivos para persistir el estado del sistema y
  excepciones para manejar situaciones como voluntarios no disponibles o recursos inexistentes.
