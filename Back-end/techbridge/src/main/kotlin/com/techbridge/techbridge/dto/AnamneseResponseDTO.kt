package com.techbridge.techbridge.dto

import java.time.LocalDateTime

data class AnamneseResponseDTO(
    val id: Int,
    val dataDisponivel: LocalDateTime,
    val nomeAventureiro: String
)