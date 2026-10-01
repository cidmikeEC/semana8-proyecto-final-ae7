package edu.uees.reservas.service;

import edu.uees.reservas.domain.EstadoReserva;
import edu.uees.reservas.domain.Reserva;
import edu.uees.reservas.repository.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository repository;

    private ReservaService service;

    @BeforeEach
    void setUp() {
        service = new ReservaService(repository);
    }

    // =========================================================================
    // CANCELACIÓN
    // =========================================================================

    @Test
    @DisplayName("2 horas permiten cancelar (frontera válida)")
    void dosHorasPermitenCancelar() {
        assertTrue(service.puedeCancelar(2));
    }

    @Test
    @DisplayName("1 hora no permite cancelar (frontera inválida)")
    void unaHoraNoPermiteCancelar() {
        assertFalse(service.puedeCancelar(1));
    }

    @Test
    @DisplayName("5 horas permiten cancelar (caso normal)")
    void cincoHorasPermitenCancelar() {
        assertTrue(service.puedeCancelar(5));
    }

    @Test
    @DisplayName("0 horas o negativo no permiten cancelar")
    void ceroYNegativoNoPermitenCancelar() {
        assertFalse(service.puedeCancelar(0));
        assertFalse(service.puedeCancelar(-2));
    }

    // =========================================================================
    // CREACIÓN Y BÚSQUEDA
    // =========================================================================

    @Test
    @DisplayName("Crear reserva delega al repositorio y retorna la entidad")
    void crearReserva_delegaAlRepositorio() {
        // Arrange
        Reserva reservaGuardada = new Reserva("R-100", "VIP");
        when(repository.guardar(any(Reserva.class))).thenReturn(reservaGuardada);

        // Act
        Reserva resultado = service.crear("R-100", "VIP");

        // Assert
        assertNotNull(resultado);
        assertEquals("R-100", resultado.getId());
        assertEquals("VIP", resultado.getTipo());
        verify(repository, times(1)).guardar(any(Reserva.class));
    }

    @Test
    @DisplayName("Buscar reserva existente retorna la entidad")
    void buscarReserva_existenteRetornaReserva() {
        // Arrange
        Reserva reserva = new Reserva("R-200", "NORMAL");
        when(repository.buscarPorId("R-200")).thenReturn(Optional.of(reserva));

        // Act
        Reserva resultado = service.buscar("R-200");

        // Assert
        assertEquals("R-200", resultado.getId());
        verify(repository, times(1)).buscarPorId("R-200");
    }

    @Test
    @DisplayName("Buscar reserva inexistente lanza IllegalArgumentException")
    void buscarReserva_inexistenteLanzaExcepcion() {
        // Arrange
        when(repository.buscarPorId("R-999")).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.buscar("R-999")
        );
        assertEquals("Reserva no encontrada", ex.getMessage());
    }

    // =========================================================================
    // CONFIRMACIÓN
    // =========================================================================

    @Test
    @DisplayName("Confirmar reserva existente cambia estado a CONFIRMADA y persiste")
    void confirmarReserva_cambiaEstadoAConfirmadaYGuarda() {
        // Arrange
        Reserva reserva = new Reserva("R-300", "ESTUDIANTE");
        when(repository.buscarPorId("R-300")).thenReturn(Optional.of(reserva));
        when(repository.guardar(reserva)).thenReturn(reserva);

        // Act
        Reserva confirmada = service.confirmar("R-300");

        // Assert
        assertEquals(EstadoReserva.CONFIRMADA, confirmada.getEstado());
        verify(repository, times(1)).guardar(reserva);
    }
}
