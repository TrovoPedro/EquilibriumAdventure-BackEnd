package com.techbridge.techbridge.service

import com.techbridge.techbridge.dto.AtivacaoEventoRequestDTO
import com.techbridge.techbridge.entity.AtivacaoEvento
import com.techbridge.techbridge.entity.Evento
import com.techbridge.techbridge.enums.EstadoEvento
import com.techbridge.techbridge.repository.AtivacaoEventoRepository
import com.techbridge.techbridge.repository.EventoRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import java.sql.Time
import java.time.LocalDate
import java.util.*

class AtivacaoEventoServiceUnitTest {

    private lateinit var service: AtivacaoEventoService
    private lateinit var ativacaoRepo: AtivacaoEventoRepository
    private lateinit var eventoRepo: EventoRepository

    @BeforeEach
    fun setup() {
        ativacaoRepo = mock(AtivacaoEventoRepository::class.java)
        eventoRepo = mock(EventoRepository::class.java)
        service = AtivacaoEventoService().apply {
            repository = ativacaoRepo
            eventoRepository = eventoRepo
        }
    }

    private val evento = Evento().apply {
        id_evento = 1L
        nome = "Evento de Teste"
    }

    private val requestDTO = AtivacaoEventoRequestDTO(
        horaInicio = "10:00:00",
        horaFinal = "12:00:00",
        limiteInscritos = 30,
        tempoEstimado = 2.0,
        tipo = "Trilha",
        dataAtivacao = "2025-10-01",
        preco = 49.99,
        estado = "NAO_INICIADO",
        eventoId = 1L
    )

    private fun mockAtivacao(id: Long = 1L): AtivacaoEvento {
        return AtivacaoEvento().apply {
            idAtivacao = id
            horaInicio = Time.valueOf("10:00:00")
            horaFinal = Time.valueOf("12:00:00")
            limiteInscritos = 30
            tempoEstimado = 2.0
            tipo = "Trilha"
            dataAtivacao = LocalDate.parse("2025-10-01")
            preco = 49.99
            estado = EstadoEvento.NAO_INICIADO
            this.evento = this@AtivacaoEventoServiceUnitTest.evento
        }
    }

    @Test
    fun `deve criar ativacao corretamente`() {
        `when`(eventoRepo.findById(1L)).thenReturn(Optional.of(evento))
        `when`(ativacaoRepo.save(any(AtivacaoEvento::class.java))).thenAnswer { it.arguments[0] }

        val result = service.criar(requestDTO)

        assertEquals(Time.valueOf("10:00:00"), result.horaInicio)
        assertEquals(LocalDate.parse("2025-10-01"), result.dataAtivacao)
        assertEquals(EstadoEvento.NAO_INICIADO, result.estado)
        assertEquals(30, result.limiteInscritos)
    }

    @Test
    fun `deve atualizar ativacao corretamente`() {
        val existente = mockAtivacao()
        `when`(ativacaoRepo.findById(1L)).thenReturn(Optional.of(existente))
        `when`(eventoRepo.findById(1L)).thenReturn(Optional.of(evento))
        `when`(ativacaoRepo.save(any(AtivacaoEvento::class.java))).thenAnswer { it.arguments[0] }

        val atualizadoDTO = requestDTO.copy(tipo = "Atualizado")

        val result = service.atualizar(1L, atualizadoDTO)

        assertEquals("Atualizado", result.tipo)
        assertEquals(EstadoEvento.NAO_INICIADO, result.estado)
    }

    @Test
    fun `deve lancar excecao ao atualizar ativacao inexistente`() {
        `when`(ativacaoRepo.findById(99L)).thenReturn(Optional.empty())

        val exception = assertThrows<RuntimeException> {
            service.atualizar(99L, requestDTO)
        }

        assertEquals("Ativação não encontrada", exception.message)
    }

    @Test
    fun `deve alterar estado corretamente`() {
        val existente = mockAtivacao()
        `when`(ativacaoRepo.findById(1L)).thenReturn(Optional.of(existente))
        `when`(ativacaoRepo.save(any(AtivacaoEvento::class.java))).thenAnswer { it.arguments[0] }

        val result = service.alterarEstado(1L, EstadoEvento.FINALIZADO)

        assertEquals(EstadoEvento.FINALIZADO, result.estado)
    }

    @Test
    fun `deve lancar excecao ao alterar estado de ativacao inexistente`() {
        `when`(ativacaoRepo.findById(123L)).thenReturn(Optional.empty())

        val exception = assertThrows<RuntimeException> {
            service.alterarEstado(123L, EstadoEvento.EM_ANDAMENTO)
        }

        assertEquals("Ativação não encontrada", exception.message)
    }
}
