package com.techbridge.techbridge

import com.techbridge.techbridge.controller.ComentarioControllerJpa
import com.techbridge.techbridge.dto.ComentarioRequestDTO
import com.techbridge.techbridge.dto.ComentarioResponseDTO
import com.techbridge.techbridge.service.ComentarioService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class ComentarioControllerTests {

    private val comentarioService = mock(ComentarioService::class.java)
    private val comentarioController = ComentarioControllerJpa(comentarioService)

    @Test
    fun adicionarComentarioRetorna201QuandoComentarioValido() {
        val requestDTO = ComentarioRequestDTO("Texto do comentário", 1, 1)
        val responseDTO = ComentarioResponseDTO(
            id = 1,
            texto = "Texto do comentário",
            dataComentario = LocalDateTime.now(),
            nomeUsuario = "Usuário Teste",
            idAtivacaoEvento = 1
        )

        `when`(comentarioService.adicionarComentario(requestDTO)).thenReturn(responseDTO)

        val response = comentarioController.adicionarComentario(requestDTO)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(responseDTO, response.body)
    }

    @Test
    fun listarComentariosRetorna200ComListaDeComentarios() {
        val comentarios = listOf(
            ComentarioResponseDTO(
                id = 1,
                texto = "Texto 1",
                dataComentario = LocalDateTime.now(),
                nomeUsuario = "Usuário Teste",
                idAtivacaoEvento = 1
            ),
            ComentarioResponseDTO(
                id = 2,
                texto = "Texto 2",
                dataComentario = LocalDateTime.now(),
                nomeUsuario = "Usuário Teste",
                idAtivacaoEvento = 1
            )
        )
        `when`(comentarioService.listarComentarios()).thenReturn(comentarios)

        val response = comentarioController.listarComentarios()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(comentarios, response.body)
    }

    @Test
    fun responderComentarioRetorna201QuandoRespostaValida() {
        val requestDTO = ComentarioRequestDTO("Resposta ao comentário", 1, 1)
        val responseDTO = ComentarioResponseDTO(
            id = 2,
            texto = "Resposta ao comentário",
            dataComentario = LocalDateTime.now(),
            nomeUsuario = "Usuário Teste",
            idAtivacaoEvento = 1
        )

        `when`(comentarioService.responderComentario(1, requestDTO)).thenReturn(responseDTO)

        val response = comentarioController.responderComentario(1, requestDTO)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(responseDTO, response.body)
    }

    @Test
    fun responderComentarioRetorna404QuandoComentarioNaoExiste() {
        val requestDTO = ComentarioRequestDTO("Resposta ao comentário", 1, 1)

        `when`(comentarioService.responderComentario(1, requestDTO)).thenReturn(null)

        val response = comentarioController.responderComentario(1, requestDTO)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertNull(response.body)
    }
}