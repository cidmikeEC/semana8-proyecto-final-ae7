# UEES UCOM0310 — Semana 8 — Proyecto Final (Ae7)

Proyecto Integrador de Diseño de Software: API REST en Spring Boot, diseño orientado a objetos, patrones de arquitectura, suite de pruebas automatizadas y cobertura con JaCoCo.

- **Asignatura:** Diseño de Software (UCOM0310) · Segundo Parcial · Semana 8
- **Carrera:** Computación — Facultad de Ingeniería
- **Universidad:** UEES (Universidad Espíritu Santo - Online)
- **Docente:** Ph.D. Jaime Paul Sayago Heredia
- **Estudiante:** Miguel Iván Delgado Anazco

---

## 🚀 Requisitos del Entorno
- Java 17 (Temurin-17.0.19)
- Maven 3.9+
- Git

---

## 🧪 1. Ejecutar Pruebas Automatizadas y Cobertura
```bash
# Limpiar, compilar y ejecutar la suite completa (Unitarias + MockMvc)
mvn clean test
```

**Resultado esperado:**
- `Tests run: 16, Failures: 0, Errors: 0, Skipped: 0`
- `BUILD SUCCESS`

Reporte interactivo de JaCoCo generado en:
`target/site/jacoco/index.html` (100% en `ReservaService`, 100% en Repository y 89% en Controller).

---

## 🌐 2. Ejecutar la Aplicación Spring Boot
```bash
mvn spring-boot:run
```
La aplicación iniciará en `http://localhost:8080`.

---

## 📡 3. Demostración de Endpoints de la API (curl)

### A. Verificar salud de la API
```bash
curl -X GET http://localhost:8080/api/reservas/salud
# Respuesta: "API activa" (HTTP 200)
```

### B. Validar regla de cancelación (límite de 2 horas)
```bash
# Caso válido (2 horas exactas)
curl -X GET "http://localhost:8080/api/reservas/puede-cancelar?horas=2"
# Respuesta: true (HTTP 200)

# Caso inválido (1 hora)
curl -X GET "http://localhost:8080/api/reservas/puede-cancelar?horas=1"
# Respuesta: false (HTTP 200)
```

### C. Crear una nueva reserva (POST)
```bash
curl -X POST http://localhost:8080/api/reservas \
  -H "Content-Type: application/json" \
  -d '{"id":"RES-FINAL-01","tipo":"VIP"}'
# Respuesta: {"id":"RES-FINAL-01","tipo":"VIP","estado":"PENDIENTE"} (HTTP 201)
```

### D. Consultar la reserva por ID (GET)
```bash
curl -X GET http://localhost:8080/api/reservas/RES-FINAL-01
# Respuesta: {"id":"RES-FINAL-01","tipo":"VIP","estado":"PENDIENTE"} (HTTP 200)
```

### E. Confirmar la reserva (PUT)
```bash
curl -X PUT http://localhost:8080/api/reservas/RES-FINAL-01/confirmar
# Respuesta: {"id":"RES-FINAL-01","tipo":"VIP","estado":"CONFIRMADA"} (HTTP 200)
```

---

## 📁 4. Documentación Técnica del Proyecto
- [01_DIAGNOSTICO_PLANTILLA.md](docs/01_DIAGNOSTICO_PLANTILLA.md): Diagnóstico técnico de arquitectura, patrones y evolución del código.
- [02_AUDITORIA_FINAL_PLANTILLA.md](docs/02_AUDITORIA_FINAL_PLANTILLA.md): Auditoría completa (funcional, diseño, pruebas, git y limitaciones).
- [03_DEFENSA_GUIA.md](docs/03_DEFENSA_GUIA.md): Guía de exposición paso a paso en 7 bloques (15 minutos).
- [04_PREGUNTAS_DEFENSA.md](docs/04_PREGUNTAS_DEFENSA.md): Banco de preguntas y respuestas técnicas preparadas para la defensa oral.

