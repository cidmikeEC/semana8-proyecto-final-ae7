# Auditoría Técnica Final — Proyecto Integrador (Ae7)

## 1. Funcional
- [x] **Aplicación inicia:** Spring Boot 3 levanta en el puerto 8080 con Java 17 Temurin (`ReservasApplication.java`).
- [x] **Endpoints principales responden:**
  - `GET /api/reservas/salud` → HTTP 200 `"API activa"`
  - `GET /api/reservas/puede-cancelar?horas=3` → HTTP 200 `true` / `false`
  - `POST /api/reservas` → HTTP 201 Created (payload JSON con DTO validado)
  - `GET /api/reservas/{id}` → HTTP 200 OK con entidad JSON
  - `PUT /api/reservas/{id}/confirmar` → HTTP 200 OK con estado `CONFIRMADA`

## 2. Diseño y Arquitectura OO
- [x] **Controller delgado:** `ReservaController` no contiene reglas de negocio; delega directamente en `ReservaService` y orquesta códigos HTTP (200, 201, 400).
- [x] **Service claro:** `ReservaService` encapsula las reglas de anticipación, búsqueda, confirmación y orquestación con el repositorio.
- [x] **Dominio coherente:** Entidad `Reserva` protege su estado con el enum `EstadoReserva` y valida la obligatoriedad de su identificador.
- [x] **Inyección de dependencias:** Acoplamiento mediado por constructores e interfaces (`ReservaRepository`).

## 3. Pruebas y Cobertura
- [x] **mvn clean test exitoso:** 16 pruebas automatizadas ejecutadas con 0 fallos y 0 errores.
- [x] **Pruebas de integración HTTP:** 6 pruebas con `MockMvc` validando el contrato de la API.
- [x] **Pruebas unitarias de servicio y repositorio:** 10 pruebas unitarias con Mockito y aserciones de estado.
- [x] **JaCoCo revisado:** Cobertura de instrucciones del 100% en `ReservaService`, 100% en Repository y 89% en Controller.

## 4. Control de Versiones y Git
- [x] **Rama final de entrega:** `ae7/proyecto-final` derivada de `main`.
- [x] **Commits descriptivos:** Historial incremental con prefijos convencionales (`feat:`, `test:`, `docs:`).
- [x] **Pull Request documentado:** PR oficial abierto en GitHub hacia `main` con objetivos, verificación y autorrevisión.
- [x] **Higiene del repositorio:** Cero carpetas `target/`, binarios `.class` ni archivos temporales.

## 5. Limitaciones Conocidas y Trabajo Futuro
1. **Persistencia en memoria:** La implementación actual (`ReservaRepositoryMemoria`) utiliza un `ConcurrentHashMap`; para un entorno productivo distribuido se debe migrar a JPA/Hibernate con PostgreSQL o MySQL.
2. **Seguridad y autenticación:** Los endpoints son públicos; en una siguiente iteración se debe incorporar Spring Security con tokens JWT para autenticar docentes y estudiantes por rol.

