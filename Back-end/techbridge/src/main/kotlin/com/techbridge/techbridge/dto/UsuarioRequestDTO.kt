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
<<<<<<< HEAD
    fun toEntity() = Usuario(nome = nome, telefone_contato = telefone_contato, email = email, senha = senha, descricao_guia = descricao_guia,
=======
    fun toEntity() = Usuario(nome = nome, telefoneContato = telefone_contato, email = email, senha = senha, descricao_guia = descricao_guia,
>>>>>>> c978f1be428cb776d4b0bfed9085c4bb6af2834c
        tipo_usuario = tipo_usuario
    )
}
