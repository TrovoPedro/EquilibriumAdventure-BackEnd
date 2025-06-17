package com.techbridge.techbridge.dto

data class ConviteResponseDTO(
    var dataConvite: String? = null,
    var fkUsuario: Long? = null,
    var nomeAventureiro: String = "",
    var conviteAceito: Boolean? = null
) {
    fun toEntity() = ConviteResponseDTO(
        dataConvite = dataConvite,
        fkUsuario = fkUsuario,
        nomeAventureiro = nomeAventureiro,
        conviteAceito = conviteAceito
    )
}
