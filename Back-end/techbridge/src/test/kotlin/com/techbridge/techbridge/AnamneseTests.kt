package com.techbridge.techbridge

import com.techbridge.techbridge.controller.AgendamentoAnamneseControllerJpa
import com.techbridge.techbridge.repository.AgendamentoAnamneseRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.Query
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus

class AnamneseTests {

    private val entityManager: EntityManager = mock(EntityManager::class.java)
    private val query: Query = mock(Query::class.java)
    private val agendamentoRepository: AgendamentoAnamneseRepository = mock(AgendamentoAnamneseRepository::class.java)
    private val agendamentoController = AgendamentoAnamneseControllerJpa(agendamentoRepository).apply {
        this.setEntityManager(entityManager)
    }

    @Test
    fun visualizarHistoricoRetorna200ComUsuariosEDataFutura() {
        val resultados = listOf(
            arrayOf(1, "2025-12-31T10:00:00", "João"),
            arrayOf(2, "2025-12-31T15:00:00", "Maria")
        )
        `when`(entityManager.createNativeQuery(anyString())).thenReturn(query)
        `when`(query.resultList).thenReturn(resultados)

        val response = agendamentoController.visualizarHistorico()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(2, response.body?.size)
        assertEquals("João", response.body?.get(0)?.get("nomeUsuario"))
        assertEquals("2025-12-31T10:00:00", response.body?.get(0)?.get("dataDisponivel"))
    }

    @Test
    fun visualizarHistoricoRetorna204QuandoNaoHaUsuariosComDataFutura() {
        `when`(entityManager.createNativeQuery(anyString())).thenReturn(query)
        `when`(query.resultList).thenReturn(emptyList<Any>())

        val response = agendamentoController.visualizarHistorico()

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        assertNull(response.body)
    }

    @Test
    fun gerarRelatorioRetorna200QuandoAtualizacaoBemSucedida() {
        `when`(entityManager.createNativeQuery(anyString())).thenReturn(query)
        `when`(query.executeUpdate()).thenReturn(1)

        val response = agendamentoController.gerarRelatorio("123456789", "Relatório atualizado com sucesso.")

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("Relatório atualizado com sucesso.", response.body)
    }

    @Test
    fun gerarRelatorioRetorna404QuandoRgNaoExiste() {
        `when`(entityManager.createNativeQuery(anyString())).thenReturn(query)
        `when`(query.executeUpdate()).thenReturn(0)

        val response = agendamentoController.gerarRelatorio("987654321", "Relatório não encontrado.")

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertEquals("RG não encontrado.", response.body)
    }

    @Test
    fun listarDatasDisponiveisRetorna200ComDatasValidas() {
        val resultados = listOf(
            arrayOf(1, "2025-12-31T10:00:00"),
            arrayOf(2, "2025-12-31T15:00:00")
        )
        `when`(entityManager.createNativeQuery(anyString())).thenReturn(query)
        `when`(query.resultList).thenReturn(resultados)

        val response = agendamentoController.listarDatasDisponiveis()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(2, response.body?.size)
        assertEquals("2025-12-31T10:00:00", response.body?.get(0)?.get("dataDisponivel"))
    }

    @Test
    fun listarDatasDisponiveisRetorna204QuandoNaoHaDatas() {
        `when`(entityManager.createNativeQuery(anyString())).thenReturn(query)
        `when`(query.resultList).thenReturn(emptyList<Any>())

        val response = agendamentoController.listarDatasDisponiveis()

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        assertNull(response.body)
    }
}