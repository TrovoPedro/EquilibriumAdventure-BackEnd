package com.techbridge.techbridge.dto

data class PerguntaComRespostaDTO(
    val id: Long,
    val textoPergunta: String,
    val alternativas: List<Pair<String, Int>>,
    val alternativaEscolhida: Int?
)