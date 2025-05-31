package com.techbridge.techbridge.dto

import com.techbridge.techbridge.enums.Nivel
import java.util.Date

data class InformacoesPessoaisGetPerfilDTO(
    val telefoneContato: String?,
    val nome: String?,
    val dataNascimento: Date?,
    val nivel: Nivel,
    val rua: String?
)
