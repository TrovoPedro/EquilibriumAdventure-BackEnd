package com.techbridge.techbridge.dto

import java.time.LocalDateTime

data class ComentarioResponseDTO(
    val id: Int,
    val texto: String,
    val dataComentario: LocalDateTime,
    val nomeUsuario: String,
    val idAtivacaoEvento: Long
)