package com.techbridge.techbridge.dto

data class PerguntaAlternativaRequestDTO(
    val textoPergunta: String,
    val alternativa1: String,
    val valor1: Int,
    val alternativa2: String,
    val valor2: Int,
    val alternativa3: String,
    val valor3: Int,
    val alternativa4: String,
    val valor4: Int
)