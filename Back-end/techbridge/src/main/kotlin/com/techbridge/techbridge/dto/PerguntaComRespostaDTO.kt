package com.techbridge.techbridge.dto

data class PerguntaComRespostaDTO(
    val id: Int,
    val textoPergunta: String,
    val alternativas: List<Pair<String, Int>>,
    val alternativaEscolhida: Int?
)