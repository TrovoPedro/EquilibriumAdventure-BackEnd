package com.techbridge.techbridge.dto

import com.techbridge.techbridge.entity.Evento

data class EventoRequestDTO(
    var nome: String? = null,
    var descricao: String? = null,
    var nivel_dificuldade: String? = null,
    var distancia_km: Double? = null,
    var responsavel: Long = 0,
    var endereco: Long? = null
) {
    fun toEntity(): Evento {
        val entidade = Evento()
        entidade.nome = nome
        entidade.descricao = descricao
        entidade.nivel_dificuldade = nivel_dificuldade
        entidade.distancia_km = distancia_km
        entidade.responsavel = responsavel
        entidade.endereco = endereco
        return entidade
    }
}


