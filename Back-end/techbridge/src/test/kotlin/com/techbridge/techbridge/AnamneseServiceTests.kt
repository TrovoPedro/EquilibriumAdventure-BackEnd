package com.techbridge.techbridge

import com.techbridge.techbridge.entity.AgendamentoAnamnese
import com.techbridge.techbridge.repository.AgendamentoAnamneseRepository
import com.techbridge.techbridge.service.AgendamentoAnamneseService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class AnamneseServiceTests {

    private val agendamentoRepository: AgendamentoAnamneseRepository = mock(AgendamentoAnamneseRepository::class.java)
    private val agendamentoService = AgendamentoAnamneseService(agendamentoRepository)

    @Test
    fun listarHistoricoRetornaResultadosCorretos() {
        val resultados = listOf(
            mapOf("idAnamnese" to 1, "dataDisponivel" to "2025-12-31T10:00:00", "nomeUsuario" to "João"),
            mapOf("idAnamnese" to 2, "dataDisponivel" to "2025-12-31T15:00:00", "nomeUsuario" to "Maria")
        )
        `when`(agendamentoRepository.listarHistorico()).thenReturn(resultados)

        val historico = agendamentoService.listarHistorico()

        assertEquals(2, historico.size)
        assertEquals("João", historico[0]["nomeUsuario"])
    }

    @Test
    fun listarDatasDisponiveisRetornaResultadosCorretos() {
        val resultados = listOf(
            mapOf("idAgenda" to 1, "dataDisponivel" to "2025-12-31T10:00:00"),
            mapOf("idAgenda" to 2, "dataDisponivel" to "2025-12-31T15:00:00")
        )
        `when`(agendamentoRepository.listarDatasDisponiveis()).thenReturn(resultados)

        val datas = agendamentoService.listarDatasDisponiveis()

        assertEquals(2, datas.size)
        assertEquals("2025-12-31T10:00:00", datas[0]["dataDisponivel"])
    }

    @Test
    fun salvarAgendamentoRetornaAgendamentoSalvo() {
        val agendamento = AgendamentoAnamnese(0, 1, 1)
        `when`(agendamentoRepository.save(agendamento)).thenReturn(agendamento)

        val salvo = agendamentoService.salvarAgendamento(agendamento)

        assertEquals(agendamento, salvo)
    }

    @Test
    fun listarPorAventureiroRetornaAgendamentosCorretos() {
        val agendamentos = listOf(
            AgendamentoAnamnese(1, 1, 1),
            AgendamentoAnamnese(2, 1, 1)
        )
        `when`(agendamentoRepository.findByFkAventureiro(1)).thenReturn(agendamentos)

        val resultado = agendamentoService.listarPorAventureiro(1)

        assertEquals(2, resultado.size)
        assertEquals(1, resultado[0].fkAventureiro)
    }
}