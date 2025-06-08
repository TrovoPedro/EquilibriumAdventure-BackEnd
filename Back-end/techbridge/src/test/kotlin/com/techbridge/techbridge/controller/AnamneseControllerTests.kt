package com.techbridge.techbridge.controller

import com.techbridge.techbridge.service.AgendamentoAnamneseService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus

class AnamneseControllerTests {

    private val agendamentoService: AgendamentoAnamneseService = mock(AgendamentoAnamneseService::class.java)
    private val agendamentoController = AgendamentoAnamneseControllerJpa(agendamentoService)

    @Test
    fun visualizarHistoricoRetorna200ComUsuariosEDataFutura() {
        val resultados = listOf(
            mapOf("idAnamnese" to 1, "dataDisponivel" to "2025-12-31T10:00:00", "nomeUsuario" to "João"),
            mapOf("idAnamnese" to 2, "dataDisponivel" to "2025-12-31T15:00:00", "nomeUsuario" to "Maria")
        )
        `when`(agendamentoService.listarHistorico()).thenReturn(resultados)

        val response = agendamentoController.visualizarHistorico()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(2, response.body?.size)
        assertEquals("João", response.body?.get(0)?.get("nomeUsuario"))
    }

    @Test
    fun visualizarHistoricoRetorna204QuandoNaoHaUsuariosComDataFutura() {
        `when`(agendamentoService.listarHistorico()).thenReturn(emptyList())

        val response = agendamentoController.visualizarHistorico()

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        assertNull(response.body)
    }

    @Test
    fun gerarRelatorioRetorna200QuandoAtualizacaoBemSucedida() {
        `when`(agendamentoService.atualizarRelatorio("123456789", "Relatório atualizado com sucesso.")).thenReturn(1)

        val response = agendamentoController.gerarRelatorio("123456789", "Relatório atualizado com sucesso.")

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("Relatório atualizado com sucesso.", response.body)
    }

    @Test
    fun gerarRelatorioRetorna404QuandoCpfNaoExiste() {
        `when`(agendamentoService.atualizarRelatorio("987654321", "Relatório não encontrado.")).thenReturn(0)

        val response = agendamentoController.gerarRelatorio("987654321", "Relatório não encontrado.")

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertEquals("CPF não encontrado.", response.body)
    }

    @Test
    fun listarDatasDisponiveisRetorna200ComDatasValidas() {
        val resultados = listOf(
            mapOf("idAgenda" to 1, "dataDisponivel" to "2025-12-31T10:00:00"),
            mapOf("idAgenda" to 2, "dataDisponivel" to "2025-12-31T15:00:00")
        )
        `when`(agendamentoService.listarDatasDisponiveis()).thenReturn(resultados)

        val response = agendamentoController.listarDatasDisponiveis()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(2, response.body?.size)
    }

    @Test
    fun listarDatasDisponiveisRetorna204QuandoNaoHaDatas() {
        `when`(agendamentoService.listarDatasDisponiveis()).thenReturn(emptyList())

        val response = agendamentoController.listarDatasDisponiveis()

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        assertNull(response.body)
    }
}