package com.techbridge.techbridge.dto

import jakarta.validation.constraints.Email

data class EditarInformacoesDTO(
    val nome: String,
    val email: String,
    val telefone_contato: String
)
