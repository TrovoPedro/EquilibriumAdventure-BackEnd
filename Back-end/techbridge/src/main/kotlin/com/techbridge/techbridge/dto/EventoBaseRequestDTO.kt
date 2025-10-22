package com.techbridge.techbridge.dto

import com.techbridge.techbridge.entity.Evento

data class EventoBaseRequestDTO(
    var id_evento: Long,
    var nome: String? = null,
    var descricao: String? = null,
    var nivel_dificuldade: String? = null,
    var distancia_km: Double? = null,
    var responsavel: Long = 0,
    var endereco: EnderecoEventoBaseDTO? = null,
    var caminho_arquivo_evento: String? = null
){

fun toEntity(enderecoId: Long?): Evento {
    return Evento(
        id_evento = id_evento,
        nome = nome,
        descricao = descricao,
        nivel_dificuldade = nivel_dificuldade,
        distancia_km = distancia_km,
        responsavel = responsavel,
        endereco = enderecoId,
        caminho_arquivo_evento = caminho_arquivo_evento
    )
}
}
