package com.techbridge.techbridge.dto

import com.techbridge.techbridge.entity.Usuario
import com.techbridge.techbridge.enums.TipoUsuario

data class UsuarioRequestDTO(
    var nome: String?,
    var telefone_contato: String?,
    var email: String?,
    var senha: String?,
    var descricao_guia: String?,
    var tipo_usuario: TipoUsuario?
){
    fun toEntity() = Usuario(nome = nome, telefoneContato = telefone_contato, email = email, senha = senha, descricao_guia = descricao_guia,
        tipo_usuario = tipo_usuario
    )
}
