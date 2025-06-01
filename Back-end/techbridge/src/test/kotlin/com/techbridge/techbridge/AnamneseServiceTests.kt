package com.techbridge.techbridge

import com.techbridge.techbridge.dto.AnamneseRequestDTO
import com.techbridge.techbridge.dto.AnamneseResponseDTO
import com.techbridge.techbridge.entity.AgendamentoAnamnese
import com.techbridge.techbridge.entity.AgendaResponsavel
import com.techbridge.techbridge.entity.Usuario
import com.techbridge.techbridge.repository.AgendamentoAnamneseRepository
import com.techbridge.techbridge.repository.AgendaResponsavelRepository
import com.techbridge.techbridge.repository.InformacoesPessoaisRepository
import com.techbridge.techbridge.repository.UsuarioRepository
import com.techbridge.techbridge.service.AgendamentoAnamneseService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.time.LocalDateTime
import java.util.*

class AnamneseServiceTests {

    private val agendamentoRepository = mock(AgendamentoAnamneseRepository::class.java)
    private val agendaResponsavelRepository = mock(AgendaResponsavelRepository::class.java)
    private val usuarioRepository = mock(UsuarioRepository::class.java)
    private val informacoesPessoaisRepository = mock(InformacoesPessoaisRepository::class.java)

    private val service = AgendamentoAnamneseService(
        agendamentoRepository,
        agendaResponsavelRepository,
        usuarioRepository,
        informacoesPessoaisRepository
    )

    @Test
    fun listarHistoricoRetornaListaDeHistorico() {
        val historico = listOf(
            mapOf("idAnamnese" to 1, "dataDisponivel" to "2025-12-31T10:00:00", "nomeUsuario" to "João"),
            mapOf("idAnamnese" to 2, "dataDisponivel" to "2025-12-31T15:00:00", "nomeUsuario" to "Maria")
        )
        `when`(agendamentoRepository.listarHistorico()).thenReturn(historico)

        val resultado = service.listarHistorico()

        assertEquals(2, resultado.size)
        assertEquals("João", resultado[0]["nomeUsuario"])
    }

    @Test
    fun salvarAgendamentoSalvaComSucesso() {
        val dto = AnamneseRequestDTO(fkData = 1, fkAventureiro = 2)
        val agendaResponsavel = AgendaResponsavel(
            dataDisponivel = LocalDateTime.parse("2025-12-31T10:00:00")
        )
        val usuario = Usuario(nome = "João")
        val agendamento = AgendamentoAnamnese(
            agendaResponsavel = agendaResponsavel,
            aventureiro = usuario
        )

        `when`(agendaResponsavelRepository.findById(1)).thenReturn(Optional.of(agendaResponsavel))
        `when`(usuarioRepository.findById(2)).thenReturn(Optional.of(usuario))
        `when`(agendamentoRepository.save(any(AgendamentoAnamnese::class.java))).thenReturn(agendamento)

        val resultado = service.salvarAgendamento(dto)

        assertNotNull(resultado)
        assertEquals(agendaResponsavel.dataDisponivel, resultado.dataDisponivel) // Comparação direta de LocalDateTime
        assertEquals("João", resultado.nomeAventureiro)
    }

    @Test
    fun atualizarRelatorioAtualizaComSucesso() {
        `when`(informacoesPessoaisRepository.atualizarRelatorioPorCpf("123456789", "Relatório atualizado"))
            .thenReturn(1)

        val linhasAfetadas = service.atualizarRelatorio("123456789", "Relatório atualizado")

        assertEquals(1, linhasAfetadas)
    }

    @Test
    fun atualizarRelatorioLancaExcecaoQuandoCpfNaoEncontrado() {
        `when`(informacoesPessoaisRepository.atualizarRelatorioPorCpf("987654321", "Relatório não encontrado"))
            .thenReturn(0)

        val exception = assertThrows(RuntimeException::class.java) {
            service.atualizarRelatorio("987654321", "Relatório não encontrado")
        }
        assertEquals("CPF não encontrado em Informações Pessoais.", exception.message)
    }

    @Test
    fun listarDatasDisponiveisRetornaListaDeDatas() {
        val datas = listOf(
            mapOf("idAgenda" to 1, "dataDisponivel" to "2025-12-31T10:00:00"),
            mapOf("idAgenda" to 2, "dataDisponivel" to "2025-12-31T15:00:00")
        )
        `when`(agendamentoRepository.listarDatasDisponiveis()).thenReturn(datas)

        val resultado = service.listarDatasDisponiveis()

        assertEquals(2, resultado.size)
        assertEquals("2025-12-31T10:00:00", resultado[0]["dataDisponivel"])
    }
}