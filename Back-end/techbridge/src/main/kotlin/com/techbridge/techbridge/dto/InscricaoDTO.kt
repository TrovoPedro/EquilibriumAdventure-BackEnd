package com.techbridge.techbridge.dto

import java.time.LocalDateTime

data class InscricaoDTO(
    val idInscricao: Long?,
    val idAtivacaoEvento: Long?,
    val idUsuario: Int?,
    val dataInscricao: LocalDateTime
)