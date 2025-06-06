package com.techbridge.techbridge.controller

import com.techbridge.techbridge.dto.InscricaoDTO
import com.techbridge.techbridge.service.InscricaoService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime

@WebMvcTest(InscricaoController::class)
class InscricaoControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var inscricaoService: InscricaoService

    @Test
    fun `deve criar uma inscricao`() {
        val inscricaoDTO = InscricaoDTO(1, 1, 1, LocalDateTime.parse("2023-10-01T10:00:00"))
        Mockito.`when`(inscricaoService.criarInscricao(1, 1)).thenReturn(inscricaoDTO)

        mockMvc.perform(
            post("/inscricoes/ativacaoEvento/1/usuario/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.idInscricao").value(1))
            .andExpect(jsonPath("$.idAtivacaoEvento").value(1))
            .andExpect(jsonPath("$.idUsuario").value(1))
            .andExpect(jsonPath("$.dataInscricao").value("2023-10-01T10:00:00"))
    }

    @Test
    fun `deve listar inscritos`() {
        val inscritos = listOf(
            InscricaoDTO(1, 1, 1, LocalDateTime.parse("2023-10-01T10:00:00")),
            InscricaoDTO(2, 1, 2, LocalDateTime.parse("2023-10-02T10:00:00"))
        )
        Mockito.`when`(inscricaoService.listarInscritos(1)).thenReturn(inscritos)

        mockMvc.perform(
            get("/inscricoes/ativacaoEvento/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(2))
            .andExpect(jsonPath("$[0].idInscricao").value(1))
            .andExpect(jsonPath("$[1].idInscricao").value(2))
    }

    @Test
    fun `deve remover um inscrito`() {
        Mockito.doNothing().`when`(inscricaoService).removerInscrito(1)

        mockMvc.perform(
            delete("/inscricoes/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNoContent)
    }

    @Test
    fun `deve cancelar uma inscricao`() {
        Mockito.doNothing().`when`(inscricaoService).cancelarInscricao(1)

        mockMvc.perform(
            delete("/inscricoes/remover/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNoContent)
    }
}