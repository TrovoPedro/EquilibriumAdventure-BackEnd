package com.techbridge.techbridge.dto

import com.techbridge.techbridge.entity.Endereco

data class EnderecoEventoBaseDTO(
    var id: Long? = null,
    var rua: String? = null,
    var numero: String? = null,
    var complemento: String? = null,
    var bairro: String? = null,
    var cidade: String? = null,
    var estado: String? = null,
    var cep: String? = null
) {
    fun toEntity(): Endereco {
        return Endereco(
            rua = rua,
            numero = numero,
            complemento = complemento,
            bairro = bairro,
            cidade = cidade,
            estado = estado,
            cep = cep
        )
    }
}
