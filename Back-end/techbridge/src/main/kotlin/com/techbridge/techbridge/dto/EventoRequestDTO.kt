package com.techbridge.techbridge.dto

import com.techbridge.techbridge.entity.Evento
import com.techbridge.techbridge.entity.InformacoesPessoais

data class EventoRequestDTO(
    var nome: String?,
    var descricao: String?,
    var nivel_dificuldade: String?,
    var distancia_km: Double?,
    var responsavel: Long,
    var endereco: Long?
){
    fun toEntity(): Evento {
        val entidade = Evento();
        entidade.nome = nome
        entidade.descricao = descricao
        entidade.nivel_dificuldade = nivel_dificuldade
        entidade.distancia_km = distancia_km
        entidade.responsavel = responsavel
        entidade.endereco = endereco
        return entidade
    }
}
