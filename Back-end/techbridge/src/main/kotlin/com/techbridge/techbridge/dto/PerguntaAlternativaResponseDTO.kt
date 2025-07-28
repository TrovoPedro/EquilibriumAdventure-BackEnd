package com.techbridge.techbridge.dto

data class PerguntaAlternativaResponseDTO(
    val id: Int,
    val textoPergunta: String,
    val alternativas: List<Pair<String, Int>>
)