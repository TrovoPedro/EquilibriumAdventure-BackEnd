package com.techbridge.techbridge.dto

import java.time.LocalDate

data class AtivacaoEventoRequestDTO(
    val horaInicio: String?,
    val horaFinal: String?,
    val limiteInscritos: Int?,
    val tempoEstimado: Double?,
    val tipo: String?,
    val dataAtivacao: LocalDate?,
    val preco: Double?,
    val estado: String?,
    val eventoId: Long?
)

