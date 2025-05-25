package com.techbridge.techbridge.dto

import java.time.LocalDateTime
import com.techbridge.techbridge.entity.Convite
import com.techbridge.techbridge.entity.Usuario

data class ConviteRequestDTO(
    var dataConvite: String? = null,
    var emailConvidado: String? = null,
    var conviteAceito: Boolean? = null,
    var fkUsuario: Long? = null,
    var fkConvidado: Long? = null
) {
    fun toEntity(aventureiro: Unit, convidado: Usuario?): Convite {
        return Convite(
            dataConvite = LocalDateTime.parse(dataConvite ?: LocalDateTime.now().toString()),
            emailConvidado = emailConvidado ?: "",
            conviteAceito = conviteAceito,
            aventureiro = Usuario(), // Assuming aventureiro is set elsewhere
            fkConvidado = convidado
        )
    }
}