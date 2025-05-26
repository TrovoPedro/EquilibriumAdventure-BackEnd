package com.techbridge.techbridge

import com.techbridge.techbridge.controller.ComentarioControllerJpa
import com.techbridge.techbridge.entity.Comentario
import com.techbridge.techbridge.service.ComentarioService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class ComentarioControllerTests {

    private val comentarioService = mock(ComentarioService::class.java)
    private val comentarioController = ComentarioControllerJpa(comentarioService)

    @Test
    fun adicionarComentarioRetorna201QuandoComentarioValido() {
        val novoComentario = Comentario(1, "Texto do comentário", LocalDateTime.now(), null, 1, 1)
        `when`(comentarioService.adicionarComentario(novoComentario)).thenReturn(novoComentario)

        val response = comentarioController.adicionarComentario(novoComentario)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(novoComentario, response.body)
    }

    @Test
    fun listarComentariosRetorna200ComListaDeComentarios() {
        val comentarios = listOf(
            Comentario(1, "Texto 1", LocalDateTime.now(), null, 1, 1),
            Comentario(2, "Texto 2", LocalDateTime.now(), null, 1, 1)
        )
        `when`(comentarioService.listarComentarios()).thenReturn(comentarios)

        val response = comentarioController.listarComentarios()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(comentarios, response.body)
    }
}