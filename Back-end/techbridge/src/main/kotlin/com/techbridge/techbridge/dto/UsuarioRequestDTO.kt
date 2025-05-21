package com.techbridge.techbridge.dto

import com.techbridge.techbridge.entity.Usuario
import com.techbridge.techbridge.enums.TipoUsuario

data class UsuarioRequestDTO(
    var nome: String?,
    var telefone_contato: String?,
    var email: String?,
    var senha: String?,
    var descricao_guia: String?,
    var fk_tipo_usuario: TipoUsuario?
){
    fun toEntity() = Usuario(nome = nome, telefone_contato = telefone_contato, email = email, senha = senha, descricao_guia = descricao_guia,
        fk_tipo_usuario = fk_tipo_usuario
    )
}
