package com.techbridge.techbridge.dto

data class ConviteReqDTO(
    var conviteAceito: Boolean? = null,
) {
    fun toEntity() = ConviteReqDTO(
        conviteAceito = conviteAceito,
    )
}
