package edu.uees.reservas.repository;

import edu.uees.reservas.domain.Reserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ReservaRepositoryMemoriaTest {

    private ReservaRepositoryMemoria repository;

    @BeforeEach
    void setUp() {
        repository = new ReservaRepositoryMemoria();
    }

    @Test
    @DisplayName("Guardar y recuperar reserva por id en memoria")
    void guardarYRecuperar_reservaCorrectamente() {
        Reserva reserva = new Reserva("R-01", "VIP");
        repository.guardar(reserva);

        Optional<Reserva> encontrada = repository.buscarPorId("R-01");
        assertTrue(encontrada.isPresent());
        assertEquals("R-01", encontrada.get().getId());
        assertEquals("VIP", encontrada.get().getTipo());
    }

    @Test
    @DisplayName("Buscar reserva inexistente retorna Optional vacío")
    void buscarPorId_inexistenteRetornaVacio() {
        Optional<Reserva> encontrada = repository.buscarPorId("NO-EXISTE");
        assertTrue(encontrada.isEmpty());
    }
}
