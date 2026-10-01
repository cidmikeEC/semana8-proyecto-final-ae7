# Guía de Sustentación Oral — Proyecto Final (Ae7)

Secuencia estructurada para la defensa técnica de 15 minutos ante el docente:

---

### Bloque 1 | Problema y Objetivo (1–2 min)
- **Problema abordado:** Gestión desacoplada y robusta del ciclo de vida de reservas de tutorías universitarias.
- **Objetivo técnico:** Demostrar cómo las decisiones de diseño orientado a objetos, patrones, refactorización, pruebas automáticas en JUnit 5 y disciplina en Git permitieron transicionar de un código heredado hacia una API REST mantenible y testeable en Spring Boot.

### Bloque 2 | Arquitectura del Sistema (2–3 min)
- **Arquitectura en capas limpias:**
  - `Controller` (Capa de entrada HTTP / REST).
  - `Service` (Capa de lógica de negocio y orquestación).
  - `Repository` (Capa de persistencia desacoplada mediante interfaz).
  - `Domain` (Entidades ricas y tipos de estado `EstadoReserva`).
- **Inyección de dependencias:** Inversión de control pura por constructor; la capa de servicio no conoce si el repositorio es en memoria, JPA o un servicio remoto.

### Bloque 3 | Patrones y Refactorización (2 min)
- **Patrones clave aplicados:**
  - *Repository Pattern:* Aísla el almacenamiento de datos del dominio (`ReservaRepositoryMemoria`).
  - *DTO Pattern:* Desacopla la representación JSON entrante del modelo de dominio (`CrearReservaRequest`).
  - *State / Lifecycle:* Transiciones explícitas (`confirmar()`) que protegen los invariantes de la reserva.
- **Refactorización:** Erradicación de controladores sobrecargados; el controlador no calcula ni decide, solo orquesta códigos HTTP.

### Bloque 4 | Demostración Funcional en Vivo (3–4 min)
- **Ejecución de la API:** `mvn spring-boot:run` levantando en `http://localhost:8080`.
- **Demostración de endpoints:**
  1. `GET /api/reservas/salud` → Comprueba estado del servicio (HTTP 200).
  2. `GET /api/reservas/puede-cancelar?horas=2` → Comprueba la regla de frontera (retorna `true`).
  3. `POST /api/reservas` → Crea reserva `RES-FINAL` tipo `VIP` (retorna HTTP 201 en estado `PENDIENTE`).
  4. `PUT /api/reservas/RES-FINAL/confirmar` → Confirma la reserva (retorna HTTP 200 en estado `CONFIRMADA`).
  5. `GET /api/reservas/RES-FINAL` → Verifica persistencia y consulta por ID.

### Bloque 5 | Pruebas y Cobertura con JaCoCo (2 min)
- **Suite automatizada:** 16 pruebas en verde (`mvn clean test`) combinando pruebas unitarias rápidas y pruebas web con `MockMvc`.
- **Reporte JaCoCo:** 100% de instrucciones y ramas en `ReservaService`.
- **Reflexión técnica:** Explicar por qué la cobertura alta no basta si no se prueban las fronteras exactas (2 horas vs 1 hora) y la verificación de ausencia de efectos colaterales.

### Bloque 6 | Git y Trazabilidad (1 min)
- **Evolución en Git:** Rama `ae7/proyecto-final` derivada de `main`.
- **Commits atómicos:** Mensajes semánticos siguiendo la convención (`feat:`, `test:`, `docs:`).
- **Pull Request documentado:** PR oficial abierto en GitHub hacia `main` con diff limpio línea por línea.

### Bloque 7 | Limitaciones y Cierre (1–2 min)
- **Limitación 1:** Persistencia actual en memoria (`ConcurrentHashMap`); requiere migrar a PostgreSQL con Spring Data JPA en la siguiente fase.
- **Limitación 2:** Autenticación ausente; requiere implementar Spring Security con JWT y RBAC.
- **Cierre:** Criterio de éxito: el proyecto es 100% reproducible, cualquier evaluador puede clonar, ejecutar `mvn clean test` y verificar la API en minutos.

