package com.techbridge.techbridge.dto

data class EmailRequest(
    val to: String?,
    val tipo: String,
    val nomeUsuario: String?,
    val nomeTrilha: String?,
    val dataEvento: String,
    val motivo: String
)
