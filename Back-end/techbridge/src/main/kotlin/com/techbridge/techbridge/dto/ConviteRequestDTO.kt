package com.techbridge.techbridge.dto

import com.techbridge.techbridge.entity.Convite
import com.techbridge.techbridge.entity.Usuario
import java.time.LocalDateTime

data class ConviteRequestDTO(
    var dataConvite: String? = null,
    var emailConvidado: String,
    var conviteAceito: Boolean? = null,
    var aventureiro: Long,
    var convidado: Long
) {
    fun toEntity(aventureiroUsuario: Usuario, convidadoUsuario: Usuario): Convite {
        return Convite(
            dataConvite = LocalDateTime.parse(dataConvite ?: LocalDateTime.now().toString()),
            emailConvidado = emailConvidado,
            conviteAceito = conviteAceito,
            aventureiro = aventureiroUsuario,
            convidado = convidadoUsuario
        )
    }
}
