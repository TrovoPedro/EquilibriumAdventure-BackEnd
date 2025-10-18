package com.techbridge.techbridge.dto

import com.techbridge.techbridge.entity.Endereco

data class EventoAtivacaoDTO(
    val idEvento: Long?,
    val nome: String?,
    val descricao: String?,
    val nivelDificuldade: String?,
    val distanciaKm: Double?,
    val responsavel: Long,
    val endereco: EnderecoDTO?,
)
