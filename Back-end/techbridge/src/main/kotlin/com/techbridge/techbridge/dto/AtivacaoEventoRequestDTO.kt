package com.techbridge.techbridge.dto

data class AtivacaoEventoRequestDTO(
    val horaInicio: String?,      // Ex: "10:00:00"
    val horaFinal: String?,
    val limiteInscritos: Int?,
    val tempoEstimado: Double?,
    val tipo: String?,
    val dataAtivacao: String?,    // Ex: "2025-05-31"
    val preco: Double?,
    val estado: String?,          // Ex: "NAO_INICIADO"
    val eventoId: Long?
)

