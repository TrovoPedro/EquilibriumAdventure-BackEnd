package com.techbridge.techbridge.dto

data class EditarPerfilDTO(
    val nome: String?,
    val email: String?,
    val telefoneContato: String?,
    val informacoes: InformacoesPessoaisRequestDTO,
    val endereco: EnderecoDTO
)