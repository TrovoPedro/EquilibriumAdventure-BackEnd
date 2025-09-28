package com.techbridge.techbridge.dto

import com.techbridge.techbridge.enums.Nivel
import java.util.Date

data class InformacoesPessoaisGetPerfilDTO(
    val nome: String?,
    val email: String?,
    val telefoneContato: String?,
    val dataNascimento: Date?,
    val cpf: String?,
    val rg: String?,
    val idioma: String?,
    val contatoEmergencia: String?,
    val endereco: EnderecoDTO?,
    val nivel: Nivel?,
    val relatorioAnamnese: String? // <-- Adicionado aqui
)