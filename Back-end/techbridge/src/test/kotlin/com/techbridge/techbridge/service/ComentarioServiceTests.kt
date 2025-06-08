package com.techbridge.techbridge.service

import com.techbridge.techbridge.entity.Comentario
import com.techbridge.techbridge.repository.ComentarioRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.time.LocalDateTime

class ComentarioServiceTests {

    private val comentarioRepository = mock(ComentarioRepository::class.java)
    private val comentarioService = ComentarioService(comentarioRepository)

    @Test
    fun adicionarComentarioSalvaEretornaComentario() {
        val novoComentario = Comentario(1, "Texto do comentário", LocalDateTime.now(), null, 1, 1)
        `when`(comentarioRepository.save(novoComentario)).thenReturn(novoComentario)

        val resultado = comentarioService.adicionarComentario(novoComentario)

        assertEquals(novoComentario, resultado)
        verify(comentarioRepository).save(novoComentario)
    }

    @Test
    fun listarComentariosRetornaListaDeComentarios() {
        val comentarios = listOf(
            Comentario(1, "Texto 1", LocalDateTime.now(), null, 1, 1),
            Comentario(2, "Texto 2", LocalDateTime.now(), null, 1, 1)
        )
        `when`(comentarioRepository.findAll()).thenReturn(comentarios)

        val resultado = comentarioService.listarComentarios()

        assertEquals(comentarios, resultado)
        verify(comentarioRepository).findAll()
    }
}