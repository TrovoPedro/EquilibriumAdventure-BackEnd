package com.techbridge.techbridge.dto

data class RespostaAventureiroDTO(
    val idResposta: Int,
    val usuarioId: Long,
    val perguntaId: Int,
    val alternativaEscolhida: Int
)