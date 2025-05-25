package com.techbridge.techbridge.dto

data class EventoResponseDTO(
    var nome: String?,
    var descricao: String?,
    var nivel_dificuldade: String?,
    var distancia_km: Double?,
    var resposavel: Long?,
    var endereco: Long?
)
