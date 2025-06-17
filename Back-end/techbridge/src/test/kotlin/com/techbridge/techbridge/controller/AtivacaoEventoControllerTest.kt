package com.techbridge.techbridge.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.techbridge.techbridge.dto.AtivacaoEventoRequestDTO
import com.techbridge.techbridge.entity.AtivacaoEvento
import com.techbridge.techbridge.enums.EstadoEvento
import com.techbridge.techbridge.service.AtivacaoEventoService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.sql.Time
import java.time.LocalDate

@WebMvcTest(AtivacaoEventoController::class)
class AtivacaoEventoControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var service: AtivacaoEventoService

    private val requestDTO = AtivacaoEventoRequestDTO(
        horaInicio = "08:00:00",
        horaFinal = "10:00:00",
        limiteInscritos = 30,
        tempoEstimado = 2.0,
        tipo = "Trilha",
        dataAtivacao = "2025-12-01",
        preco = 49.99,
        estado = "NAO_INICIADO",
        eventoId = 1L
    )

    private fun buildAtivacaoEventoMock(estado: EstadoEvento): AtivacaoEvento {
        return AtivacaoEvento().apply {
            idAtivacao = 1L
            horaInicio = Time.valueOf("08:00:00")
            horaFinal = Time.valueOf("10:00:00")
            limiteInscritos = 30
            tempoEstimado = 2.0
            tipo = "Trilha"
            dataAtivacao = LocalDate.of(2025, 12, 1)
            preco = 49.99
            this.estado = estado
            evento = null
        }
    }

    @Test
    fun `deve criar ativacao com sucesso`() {
        val ativacao = buildAtivacaoEventoMock(EstadoEvento.NAO_INICIADO)
        given(service.criar(requestDTO)).willReturn(ativacao)

        mockMvc.perform(
            post("/ativacoes/criar-ativacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.estado").value("NAO_INICIADO"))
            .andExpect(jsonPath("$.idAtivacao").value(1))
    }

    @Test
    fun `deve atualizar ativacao com sucesso`() {
        val ativacao = buildAtivacaoEventoMock(EstadoEvento.NAO_INICIADO)
        given(service.atualizar(1L, requestDTO)).willReturn(ativacao)

        mockMvc.perform(
            put("/ativacoes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.tipo").value("Trilha"))
            .andExpect(jsonPath("$.estado").value("NAO_INICIADO"))
    }

    @Test
    fun `deve alterar estado com sucesso`() {
        val alterado = buildAtivacaoEventoMock(EstadoEvento.FINALIZADO)
        given(service.alterarEstado(1L, EstadoEvento.FINALIZADO)).willReturn(alterado)

        mockMvc.perform(put("/ativacoes/1/estado?estado=finalizado"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.estado").value("FINALIZADO"))
    }

    @Test
    fun `deve retornar erro ao enviar estado invalido`() {
        mockMvc.perform(put("/ativacoes/1/estado?estado=invalido"))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.erro").value("Estado inválido: invalido"))
    }
}
