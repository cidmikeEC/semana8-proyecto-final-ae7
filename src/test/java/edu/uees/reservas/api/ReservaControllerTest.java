package edu.uees.reservas.api;

import edu.uees.reservas.domain.EstadoReserva;
import edu.uees.reservas.domain.Reserva;
import edu.uees.reservas.service.ReservaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas de integración HTTP sobre ReservaController utilizando MockMvc.
 */
@WebMvcTest(ReservaController.class)
class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservaService service;

    @Test
    @DisplayName("GET /api/reservas/salud retorna 200 y mensaje API activa")
    void salud_retornaOk() throws Exception {
        mockMvc.perform(get("/api/reservas/salud"))
                .andExpect(status().isOk())
                .andExpect(content().string("API activa"));
    }

    @Test
    @DisplayName("GET /api/reservas/puede-cancelar con horas válidas retorna true")
    void puedeCancelar_conHorasValidas_retornaTrue() throws Exception {
        when(service.puedeCancelar(3)).thenReturn(true);

        mockMvc.perform(get("/api/reservas/puede-cancelar").param("horas", "3"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @DisplayName("GET /api/reservas/puede-cancelar con 1 hora retorna false")
    void puedeCancelar_conUnaHora_retornaFalse() throws Exception {
        when(service.puedeCancelar(1)).thenReturn(false);

        mockMvc.perform(get("/api/reservas/puede-cancelar").param("horas", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    @DisplayName("POST /api/reservas con payload válido crea la reserva y retorna 201")
    void crear_conPayloadValido_retorna201() throws Exception {
        Reserva reservaCreada = new Reserva("RES-001", "VIP");
        when(service.crear("RES-001", "VIP")).thenReturn(reservaCreada);

        String jsonPayload = """
                {
                    "id": "RES-001",
                    "tipo": "VIP"
                }
                """;

        mockMvc.perform(post("/api/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("RES-001"))
                .andExpect(jsonPath("$.tipo").value("VIP"))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));
    }

    @Test
    @DisplayName("GET /api/reservas/{id} retorna 200 con la reserva encontrada")
    void buscar_reservaExistente_retorna200() throws Exception {
        Reserva reserva = new Reserva("RES-002", "NORMAL");
        when(service.buscar("RES-002")).thenReturn(reserva);

        mockMvc.perform(get("/api/reservas/RES-002"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("RES-002"))
                .andExpect(jsonPath("$.tipo").value("NORMAL"));
    }

    @Test
    @DisplayName("PUT /api/reservas/{id}/confirmar cambia estado a CONFIRMADA y retorna 200")
    void confirmar_reservaExistente_retorna200Confirmada() throws Exception {
        Reserva reservaConfirmada = new Reserva("RES-003", "ESTUDIANTE");
        reservaConfirmada.confirmar();
        when(service.confirmar("RES-003")).thenReturn(reservaConfirmada);

        mockMvc.perform(put("/api/reservas/RES-003/confirmar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("RES-003"))
                .andExpect(jsonPath("$.estado").value("CONFIRMADA"));
    }
}
