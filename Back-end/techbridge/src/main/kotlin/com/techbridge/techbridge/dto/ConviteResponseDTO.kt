package com.techbridge.techbridge.dto

data class ConviteResponseDTO(
    var dataConvite: String? = null,
    var fkUsuario: Int? = null,
    var nomeAventureiro: String = "",
) {
    fun toEntity() = ConviteResponseDTO(
        dataConvite = dataConvite,
        fkUsuario = fkUsuario,
        nomeAventureiro = nomeAventureiro
    )
}
