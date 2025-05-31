package com.techbridge.techbridge.dto

import com.techbridge.techbridge.enums.EstadoEvento
import java.sql.Time
import java.time.LocalDate

data class AtivacaoEventoResponseDTO(
    val horaInicio: String?,
    val horaFinal: String?,
    val limiteInscritos: Int?,
    val tempoEstimado: Double?,
    val tipo: String?,
    val dataAtivacao: LocalDate,
    val preco: Double?,
    val estado: EstadoEvento?,
    val eventoId: Long
)
