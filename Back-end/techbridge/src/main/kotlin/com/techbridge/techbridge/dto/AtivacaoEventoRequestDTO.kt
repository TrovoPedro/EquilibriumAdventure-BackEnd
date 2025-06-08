package com.techbridge.techbridge.dto

data class AtivacaoEventoRequestDTO(
    val horaInicio: String?,
    val horaFinal: String?,
    val limiteInscritos: Int?,
    val tempoEstimado: Double?,
    val tipo: String?,
    val dataAtivacao: String?,
    val preco: Double?,
    val estado: String?,
    val eventoId: Long?
)

