package com.techbridge.techbridge.dto

data class UsuarioResponseDTO(
    var idUsuario: Long?,
    var nome:String?,
    var telefone_contato: String?,
    var email: String?,
    var descricao_guia: String?,
){}
