package com.techbridge.techbridge.dto

import com.techbridge.techbridge.enums.Nivel

data class InformacoesPessoaisResponseDTO(
    val contatoEmergencia: String?,
    val endereco: Long,
    val nivel: Nivel,
    val usuario: Long,
    val relatorioAnamnese: String?,
    val idioma: String?,
    val questionarioRespondido: Boolean?
)
