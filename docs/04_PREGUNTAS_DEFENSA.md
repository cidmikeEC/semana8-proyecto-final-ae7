# Banco de Preguntas y Argumentación Técnica para la Defensa (Ae7)

Respuestas fundamentadas para la sustentación oral ante el docente Ph.D. Jaime Sayago:

---

### 1. ¿Qué decisión de diseño fue la más importante en todo el proyecto?
La decisión de **aislar la lógica de negocio del framework y de la persistencia mediante inversión de dependencias**. Al hacer que `ReservaService` dependa exclusivamente de la interfaz `ReservaRepository` y no de una clase concreta, el sistema se volvió inmune a cambios tecnológicos: podemos correr pruebas unitarias instantáneas con dobles en memoria, probar el API con `MockMvc` sin base de datos, y mañana cambiar la persistencia a PostgreSQL o DynamoDB sin tocar una sola línea de lógica de reservas.

---

### 2. ¿Qué patrón de diseño resolvió un problema concreto y cómo?
El patrón **Repository combinado con DTO**:
- El **Repository** (`ReservaRepository`) resolvió el acoplamiento a base de datos.
- El **DTO** (`CrearReservaRequest`) resolvió la sobre-exposición del modelo interno: evita que clientes externos modifiquen directamente el estado de la reserva (`CONFIRMADA` o `PENDIENTE`) al enviar un JSON; la entidad nace siempre en estado `PENDIENTE` y solo transiciona mediante el método de negocio `confirmar()`.

---

### 3. ¿Qué patrón de diseño decidieron evitar conscientemente y por qué?
Evitamos utilizar el patrón **Singleton clásico** implementado a mano con constructores privados y variables estáticas. En su lugar, dejamos que **Spring maneje el ciclo de vida de los componentes (`@Service`, `@Repository`) como beans administrados**. El Singleton estático introduce estado global oculto, acopla las clases al cargador de clases y hace que las pruebas unitarias sean difíciles de paralelizar o resetear.

---

### 4. ¿Qué refactorización redujo significativamente la deuda técnica?
La refactorización del **Controller hacia un controlador ultra-delgado**. En las versiones iniciales de código heredado, el controlador tendía a verificar si la reserva existía, calcular aranceles y manipular estados directamente. Mover todas las reglas a `ReservaService` redujo la deuda técnica porque ahora el controlador se limita a su única responsabilidad: traducir peticiones HTTP, mapear códigos de estado (200, 201, 400) y delegar al servicio.

---

### 5. ¿Qué caso de prueba automatizado protege el mayor riesgo del negocio?
El caso de prueba **`unaHoraNoPermiteCancelar` y su contraparte `dosHorasPermitenCancelar`** (frontera exacta de 2 horas). El mayor riesgo económico y de descontento docente en tutorías universitarias es que un estudiante cancele 10 minutos antes de la sesión y el docente pierda su honorario. Esa prueba de frontera protege la regla estricta contra errores típicos de *off-by-one* (`>` en vez de `>=`).

---

### 6. ¿Qué significa realmente la métrica de cobertura obtenida en JaCoCo?
Obtuvimos **100% de cobertura en instrucciones y ramas en `ReservaService` y 89% en el controlador**. Sin embargo, en la defensa explicamos con criterio crítico que esto **no significa ausencia absoluta de fallos**: significa únicamente que cada línea de bytecode se ejecutó durante las pruebas. La verdadera confiabilidad proviene de que las pruebas comparan aserciones rigurosas de estado y verifican que ante errores no existan efectos colaterales indeseados.

---

### 7. ¿Qué evidencia en el historial Git demuestra la evolución disciplinada del equipo?
El historial en la rama `ae7/proyecto-final` muestra **commits atómicos e incrementales**: primero la línea base, luego la incorporación de endpoints y DTOs, posteriormente la suite exhaustiva de pruebas y finalmente la documentación. No hay un solo "mega-commit" al final; cada cambio fue probado en verde antes de registrarse.

---

### 8. ¿Qué mejorarían en una siguiente iteración del sistema?
En la siguiente iteración incorporaríamos:
1. **Spring Data JPA con PostgreSQL:** Reemplazar el repositorio en memoria por una base de datos relacional con migraciones versionadas (Flyway).
2. **Seguridad y Control de Acceso (Spring Security):** Implementar autenticación JWT con roles diferenciados (`ESTUDIANTE`, `DOCENTE`, `ADMINISTRADOR`) para proteger los endpoints.
3. **Manejo de concurrencia optimista:** Agregar un campo `@Version` en `Reserva` para evitar colisiones cuando dos peticiones intentan confirmar la misma reserva al mismo milisegundo.
