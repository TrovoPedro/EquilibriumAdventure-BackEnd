package com.techbridge.techbridge.dto

data class EventoGuiaEnderecoDTO(
    val id_evento: Long,
    val nome_evento: String,
    val descricao: String,
    val nivel_dificuldade: String,
    val distancia_km: Double,
    val responsavel_id: Long,
    val nome_responsavel: String,
    val endereco_id: Long,
    val rua: String
)
