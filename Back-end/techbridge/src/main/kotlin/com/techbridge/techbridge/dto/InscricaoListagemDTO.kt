package com.techbridge.techbridge.dto

import java.time.LocalDateTime


data class InscricaoListagemDTO(
    val idInscricao: Long?,
    val idAtivacaoEvento: Long,
    val dataInscricao: LocalDateTime,
    val idUsuario: Long?,
    val nomeUsuario: String?,
    val emailUsuario: String?
)