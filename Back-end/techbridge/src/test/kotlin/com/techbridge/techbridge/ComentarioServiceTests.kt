package com.techbridge.techbridge

import com.techbridge.techbridge.dto.ComentarioRequestDTO
import com.techbridge.techbridge.entity.AtivacaoEvento
import com.techbridge.techbridge.entity.Comentario
import com.techbridge.techbridge.entity.Usuario
import com.techbridge.techbridge.repository.AtivacaoEventoRepository
import com.techbridge.techbridge.repository.ComentarioRepository
import com.techbridge.techbridge.repository.UsuarioRepository
import com.techbridge.techbridge.service.ComentarioService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.time.LocalDateTime
import java.util.*

class ComentarioServiceTests {

    private val comentarioRepository = mock(ComentarioRepository::class.java)
    private val usuarioRepository = mock(UsuarioRepository::class.java)
    private val ativacaoEventoRepository = mock(AtivacaoEventoRepository::class.java)

    private val comentarioService = ComentarioService(
        comentarioRepository,
        usuarioRepository,
        ativacaoEventoRepository
    )

    @Test
    fun adicionarComentarioSalvaEretornaComentario() {
        val requestDTO = ComentarioRequestDTO("Texto do comentário", 1, 1)
        val usuario = Usuario(idUsuario = 1, nome = "Usuário Teste")
        val ativacaoEvento = AtivacaoEvento().apply { idAtivacao = 1 } // Instância válida de AtivacaoEvento
        val comentarioSalvo = Comentario(
            id = 1,
            texto = "Texto do comentário",
            dataComentario = LocalDateTime.now(),
            usuario = usuario,
            ativacaoEvento = ativacaoEvento
        )

        `when`(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario))
        `when`(ativacaoEventoRepository.findById(1)).thenReturn(Optional.of(ativacaoEvento)) // Mock configurado
        `when`(comentarioRepository.save(any(Comentario::class.java))).thenReturn(comentarioSalvo)

        val resultado = comentarioService.adicionarComentario(requestDTO)

        assertEquals(comentarioSalvo.id, resultado.id)
        assertEquals(comentarioSalvo.texto, resultado.texto)
        assertEquals(comentarioSalvo.usuario?.nome, resultado.nomeUsuario)
    }

    @Test
    fun responderComentarioSalvaERetornaResposta() {
        val requestDTO = ComentarioRequestDTO("Resposta ao comentário", 1, 1)
        val comentarioOriginal = Comentario(id = 1, texto = "Comentário original")
        val usuario = Usuario(idUsuario = 1, nome = "Usuário Teste")
        val ativacaoEvento = AtivacaoEvento().apply { idAtivacao = 1 } // Instância válida de AtivacaoEvento
        val respostaSalva = Comentario(
            id = 2,
            texto = "Resposta ao comentário",
            dataComentario = LocalDateTime.now(),
            usuario = usuario,
            ativacaoEvento = ativacaoEvento
        )

        `when`(comentarioRepository.findById(1)).thenReturn(Optional.of(comentarioOriginal))
        `when`(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario))
        `when`(ativacaoEventoRepository.findById(1)).thenReturn(Optional.of(ativacaoEvento)) // Mock configurado
        `when`(comentarioRepository.save(any(Comentario::class.java))).thenReturn(respostaSalva)

        val resultado = comentarioService.responderComentario(1, requestDTO)

        assertEquals(respostaSalva.id, resultado.id)
        assertEquals(respostaSalva.texto, resultado.texto)
        assertEquals(respostaSalva.usuario?.nome, resultado.nomeUsuario)
    }

    @Test
    fun excluirComentarioRemoveComentario() {
        val comentario = Comentario(id = 1, texto = "Comentário a ser excluído")

        `when`(comentarioRepository.findById(1)).thenReturn(Optional.of(comentario))
        doNothing().`when`(comentarioRepository).delete(comentario)

        val resultado = comentarioService.excluirComentario(1)

        assertTrue(resultado)
        verify(comentarioRepository).delete(comentario)
    }

    @Test
    fun listarComentariosRetornaListaDeComentarios() {
        val comentarios = listOf(
            Comentario(
                id = 1,
                texto = "Texto 1",
                dataComentario = LocalDateTime.now(),
                usuario = Usuario(nome = "Usuário 1"),
                ativacaoEvento = null
            ),
            Comentario(
                id = 2,
                texto = "Texto 2",
                dataComentario = LocalDateTime.now(),
                usuario = Usuario(nome = "Usuário 2"),
                ativacaoEvento = null
            )
        )

        `when`(comentarioRepository.findAll()).thenReturn(comentarios)

        val resultado = comentarioService.listarComentarios()

        assertEquals(2, resultado.size)
        assertEquals("Texto 1", resultado[0].texto)
        assertEquals("Usuário 1", resultado[0].nomeUsuario)
    }
}