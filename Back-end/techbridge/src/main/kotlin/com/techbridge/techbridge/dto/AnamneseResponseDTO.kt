package com.techbridge.techbridge.dto

import java.time.LocalDateTime

data class AnamneseResponseDTO(
    val id: Long,
    val dataDisponivel: LocalDateTime,
    val nomeAventureiro: String
)