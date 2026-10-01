# Diagnóstico Inicial e Integrador — Proyecto Final (Ae7)

| Elemento | Estado | Evidencia | Riesgo | Acción |
|---|---|---|---|---|
| **Diseño OO** | Sólido y Modular | Arquitectura por capas: Controller (REST) → Service (Negocio) → Repository (Persistencia) → Domain (Entidades). | Acoplamiento a implementaciones concretas de persistencia. | Inyección de dependencias mediante constructor sobre interfaces (`ReservaRepository`). |
| **Patrones** | Aplicados y Justificados | Repository Pattern (`ReservaRepositoryMemoria`), DTO Pattern (`CrearReservaRequest`), State Pattern (`EstadoReserva`). | Lógica de negocio dispersa en la capa de transporte HTTP. | Mantener controladores ultra-delgados que solo orquestan HTTP y delegan al servicio. |
| **Refactorización** | Cohesión Alta | Separación de responsabilidades: validación de anticipación, ciclo de vida de confirmación y persistencia aisladas. | Primitive Obsession y duplicación de reglas de estado. | Encapsular transiciones en métodos de dominio (`reserva.confirmar()`) y validar invariantes. |
| **Pruebas** | Integral (Unitarias + Integración) | 16 pruebas automatizadas en JUnit 5: pruebas unitarias con Mockito y pruebas web HTTP con `MockMvc`. | Regresiones inadvertidas en endpoints o serialización JSON. | Pruebas de integración sobre `ReservaController` validando códigos HTTP 200, 201 y 400. |
| **Cobertura** | Alta y Verificada | Reporte JaCoCo generado en `target/site/jacoco/index.html`: 100% en `ReservaService`, 89% en API, 100% en Repository. | Falsa sensación de seguridad por porcentaje alto sin pruebas de frontera. | Validar explícitamente límites de 2h, 1h, 0h y excepciones de no-encontrado. |
| **Git / PR** | Trazable y Colaborativo | Rama `ae7/proyecto-final` derivada de `main`, commits semánticos incrementales y Pull Request documentado. | Commits monolíticos o falta de reproducibilidad para revisión. | Mantener `Files changed` limpio sin carpetas `target/` ni binarios generados. |
| **Documentación** | Completa y Ejecutable | README con comandos `mvn spring-boot:run`, `mvn clean test`, ejemplos `curl` y checklist de verificación. | Dificultad para levantar o probar la API por parte del evaluador. | Proveer guía paso a paso con comandos reproducibles en Windows PowerShell. |

