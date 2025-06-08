package com.techbridge.techbridge.service

import com.techbridge.techbridge.dto.InscricaoDTO
import com.techbridge.techbridge.entity.AtivacaoEvento
import com.techbridge.techbridge.entity.Inscricao
import com.techbridge.techbridge.entity.Usuario
import com.techbridge.techbridge.repository.AtivacaoEventoRepository
import com.techbridge.techbridge.repository.InscricaoRepository
import com.techbridge.techbridge.repository.UsuarioRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import java.time.LocalDateTime
import java.util.*

class InscricaoServiceTest {

    private val inscricaoRepository: InscricaoRepository = mock(InscricaoRepository::class.java)
    private val ativacaoEventoRepository: AtivacaoEventoRepository = mock(AtivacaoEventoRepository::class.java)
    private val usuarioRepository: UsuarioRepository = mock(UsuarioRepository::class.java)

    private val inscricaoService = InscricaoService().apply {
        inscricaoRepository = this@InscricaoServiceTest.inscricaoRepository
        ativacaoEventoRepository = this@InscricaoServiceTest.ativacaoEventoRepository
        usuarioRepository = this@InscricaoServiceTest.usuarioRepository
    }

    @Test
    fun `deve criar uma inscricao com sucesso`() {
        val evento = AtivacaoEvento(idAtivacao = 1, limiteInscritos = 10)
        val usuario = Usuario(idUsuario = 1, nome = "Usuario")
        val inscricao = Inscricao(id_inscricao = 1, ativacaoEvento = evento, aventureiro = usuario, dataInscricao = LocalDateTime.now())

        Mockito.`when`(ativacaoEventoRepository.findById(1)).thenReturn(Optional.of(evento))
        Mockito.`when`(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario))
        Mockito.`when`(inscricaoRepository.findByAtivacaoEventoAndAventureiro(evento, usuario)).thenReturn(null)
        Mockito.`when`(inscricaoRepository.countByAtivacaoEvento(evento)).thenReturn(0)
        Mockito.`when`(inscricaoRepository.save(any(Inscricao::class.java))).thenReturn(inscricao)

        val result = inscricaoService.criarInscricao(1, 1)

        assertNotNull(result)
        assertEquals(1, result.idInscricao)
        assertEquals(1, result.idAtivacaoEvento)
        assertEquals(1, result.idUsuario)
    }

    @Test
    fun `deve lançar exceção ao tentar criar inscrição para evento inexistente`() {
        Mockito.`when`(ativacaoEventoRepository.findById(1)).thenReturn(Optional.empty())

        val exception = assertThrows<RuntimeException> {
            inscricaoService.criarInscricao(1, 1)
        }

        assertEquals("Evento não encontrado", exception.message)
    }

    @Test
    fun `deve lançar exceção ao tentar criar inscrição para usuário inexistente`() {
        val evento = AtivacaoEvento(idAtivacao = 1, limiteInscritos = 10)

        Mockito.`when`(ativacaoEventoRepository.findById(1)).thenReturn(Optional.of(evento))
        Mockito.`when`(usuarioRepository.findById(1)).thenReturn(Optional.empty())

        val exception = assertThrows<RuntimeException> {
            inscricaoService.criarInscricao(1, 1)
        }

        assertEquals("Usuário não encontrado", exception.message)
    }

    @Test
    fun `deve cancelar uma inscricao com sucesso`() {
        Mockito.`when`(inscricaoRepository.existsById(1)).thenReturn(true)
        Mockito.doNothing().`when`(inscricaoRepository).deleteById(1)

        assertDoesNotThrow { inscricaoService.cancelarInscricao(1) }
    }

    @Test
    fun `deve lançar exceção ao tentar cancelar inscrição inexistente`() {
        Mockito.`when`(inscricaoRepository.existsById(1)).thenReturn(false)

        val exception = assertThrows<RuntimeException> {
            inscricaoService.cancelarInscricao(1)
        }

        assertEquals("Inscrição não encontrada", exception.message)
    }

    @Test
    fun `deve listar inscritos com sucesso`() {
        val evento = AtivacaoEvento(idAtivacao = 1, limiteInscritos = 10)
        val usuario = Usuario(idUsuario = 1, nome = "Usuario")
        val inscricao = Inscricao(id_inscricao = 1, ativacaoEvento = evento, aventureiro = usuario, dataInscricao = LocalDateTime.now())

        Mockito.`when`(ativacaoEventoRepository.findById(1)).thenReturn(Optional.of(evento))
        Mockito.`when`(inscricaoRepository.findAll()).thenReturn(listOf(inscricao))

        val result = inscricaoService.listarInscritos(1)

        assertEquals(1, result.size)
        assertEquals(1, result[0].idInscricao)
    }

    @Test
    fun `deve lançar exceção ao tentar listar inscritos de evento inexistente`() {
        Mockito.`when`(ativacaoEventoRepository.findById(1)).thenReturn(Optional.empty())

        val exception = assertThrows<RuntimeException> {
            inscricaoService.listarInscritos(1)
        }

        assertEquals("Evento não encontrado", exception.message)
    }
}