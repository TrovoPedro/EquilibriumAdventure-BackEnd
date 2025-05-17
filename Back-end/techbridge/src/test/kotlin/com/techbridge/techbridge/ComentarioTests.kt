package com.techbridge.techbridge

import com.techbridge.techbridge.controller.ComentarioControllerJpa
import com.techbridge.techbridge.entity.Comentario
import com.techbridge.techbridge.repository.ComentarioRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class ComentarioTests {

        private val comentarioRepository = mock(ComentarioRepository::class.java)
        private val comentarioController = ComentarioControllerJpa(comentarioRepository)

        @Test
        fun adicionarComentarioRetorna201QuandoComentarioValido() {
            val novoComentario = Comentario(1, "Texto do comentário", LocalDateTime.now(), null, 1, 1)
            `when`(comentarioRepository.save(novoComentario)).thenReturn(novoComentario)

            val response = comentarioController.adicionarComentario(novoComentario)

            assertEquals(HttpStatus.CREATED, response.statusCode)
            assertEquals(novoComentario, response.body)
        }

        @Test
        fun excluirComentarioRetorna204QuandoIdExiste() {
            val id = 1
            `when`(comentarioRepository.existsById(id)).thenReturn(true)

            val response = comentarioController.excluirComentario(id)

            assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
            verify(comentarioRepository).deleteById(id)
        }

        @Test
        fun responderComentarioRetorna201QuandoComentarioOriginalExiste() {
            val id = 1
            val comentarioOriginal = Comentario(id, "Texto original", LocalDateTime.now(), null, 1, 1)
            val resposta = Comentario(2, "Texto da resposta", LocalDateTime.now(), null, 1, 1)
            `when`(comentarioRepository.findById(id)).thenReturn(java.util.Optional.of(comentarioOriginal))
            `when`(comentarioRepository.save(any(Comentario::class.java))).thenReturn(resposta)

            val response = comentarioController.responderComentario(id, resposta)

            assertEquals(HttpStatus.CREATED, response.statusCode)
            assertEquals(resposta, response.body)
        }

        @Test
        fun listarComentariosRetorna200ComListaDeComentarios() {
            val comentarios = listOf(
                Comentario(1, "Texto 1", LocalDateTime.now(), null, 1, 1),
                Comentario(2, "Texto 2", LocalDateTime.now(), null, 1, 1)
            )
            `when`(comentarioRepository.findAll()).thenReturn(comentarios)

            val response = comentarioController.listarComentarios()

            assertEquals(HttpStatus.OK, response.statusCode)
            assertEquals(comentarios, response.body)
        }
    }
