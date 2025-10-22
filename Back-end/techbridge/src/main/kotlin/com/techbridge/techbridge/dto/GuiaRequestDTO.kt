package com.techbridge.techbridge.dto

import com.techbridge.techbridge.entity.Usuario
import com.techbridge.techbridge.enums.TipoUsuario
import com.techbridge.techbridge.enums.TipoUsuario.*
import java.util.Date

data class GuiaRequestDTO(
    var nome : String? = null,
    var email : String? = null,
    var senha : String? = null,
    var descricao_guia : String? = null,
    var tipo_usuario : TipoUsuario? = null,
    var img_usuario : ByteArray? = null
) {
    fun toEntity(): Usuario {
        return Usuario(
        nome = nome,
        email = email,
        senha = senha,
        descricao_guia = descricao_guia,
        tipo_usuario = GUIA,
        img_usuario = img_usuario
        )
    }
}